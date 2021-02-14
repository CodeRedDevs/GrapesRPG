package me.trqhxrd.grapesrpg.api.objects.entity.npc;

import com.mojang.authlib.GameProfile;
import me.trqhxrd.grapesrpg.Grapes;
import me.trqhxrd.grapesrpg.api.GrapesPlayer;
import me.trqhxrd.grapesrpg.api.event.GrapesPlayerClickNPCEvent;
import net.minecraft.server.v1_16_R3.*;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_16_R3.CraftServer;
import org.bukkit.craftbukkit.v1_16_R3.CraftWorld;
import org.bukkit.craftbukkit.v1_16_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

/**
 * This class represents a NPC.
 * A NPC is a simulated EntityPlayer.
 *
 * @author Trqhxrd
 */
public class NPC extends EntityPlayer {

    /**
     * This list contains all created NPCs.
     */
    private static final List<NPC> npcs = new ArrayList<>();

    /**
     * This Field contains the NPCs ID.
     * This ID will later be used for naming the file, which contains the serialized NPC.
     */
    private final UUID npcId;

    /**
     * This boolean will be queried in {@link NPC#display(Player)}.
     * If it is false, the Player will be removed from the tablist.
     */
    private final boolean tablist;

    /**
     * This list contains all tasks, which will be executed as soon as the NPC gets clicked.
     */
    private final List<NPCTask> tasks = new ArrayList<>();

    /**
     * This constructor creates a new NPC.
     *
     * @param name     The name of the new NPC.
     * @param skin     The Skin of the NPC. Leave null, if the NPC should have a random Steve or Alex Skin.
     * @param location The Location of the NPC.
     * @param tablist  If set to true, the NPC will be displayed on the tablist.
     */
    public NPC(String name, Skin skin, Location location, boolean tablist) {
        this(UUID.randomUUID(), name, skin, location, tablist);
    }

    /**
     * This constructor creates a new NPC.
     *
     * @param name     The name of the new NPC.
     * @param location The Location of the NPC.
     * @param tablist  If set to true, the NPC will be displayed on the tablist.
     */
    public NPC(String name, Location location, boolean tablist) {
        this(UUID.randomUUID(), name, Skin.getSkin(name), location, tablist);
    }

    /**
     * This constructor creates a new NPC.
     *
     * @param name     The name of the new NPC.
     * @param location The Location of the NPC.
     */
    public NPC(String name, Location location) {
        this(UUID.randomUUID(), name, Skin.getSkin(name), location, true);
    }

    /**
     * This constructor creates a new NPC.
     *
     * @param id       The NPCs id. This ID will be the name of the json-file, which contains the serialized NPC.
     * @param name     The name of the new NPC.
     * @param skin     The Skin of the NPC. Leave null, if the NPC should have a random Steve or Alex Skin.
     * @param location The Location of the NPC.
     * @param tablist  If set to true, the NPC will be displayed on the tablist.
     */
    public NPC(UUID id, String name, Skin skin, Location location, boolean tablist) {
        super(
                ((CraftServer) Bukkit.getServer()).getServer(),
                ((CraftWorld) location.getWorld()).getHandle(),
                new GameProfile(UUID.randomUUID(), name),
                new PlayerInteractManager(((CraftWorld) location.getWorld()).getHandle())
        );

        this.npcId = id;
        this.tablist = tablist;
        this.setLocation(location);

        this.addTask((operator, type) -> Bukkit.getPluginManager().callEvent(new GrapesPlayerClickNPCEvent(operator, this, type)));

        if (skin != null) skin.apply(this);

        //DISPLAY OVERLAY
        this.getDataWatcher().set(EntityHuman.bi, ((byte) 0xFF));

        npcs.add(this);
    }

    /**
     * Getter for the list of NPCs.
     *
     * @return A list, which contains all NPCs.
     */
    public static List<NPC> getNpcs() {
        return npcs;
    }

    /**
     * This method sets the NPCs new location.
     *
     * @param location The new Location of the NPC.
     */
    public void setLocation(Location location) {
        super.setLocation(location.getX(), location.getY(), location.getZ(), location.getYaw(), location.getPitch());
    }

    /**
     * This method displays the NPC to the Player, which you give in the first parameter.
     *
     * @param p The payer, who is supposed to see the NPC.
     */
    public void display(Player p) {
        PlayerConnection connection = ((CraftPlayer) p).getHandle().playerConnection;
        connection.sendPacket(new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.ADD_PLAYER, this));
        connection.sendPacket(new PacketPlayOutNamedEntitySpawn(this));
        connection.sendPacket(new PacketPlayOutEntityHeadRotation(this, (byte) (this.yaw * 256 / 360)));
        connection.sendPacket(new PacketPlayOutEntityMetadata(this.getId(), this.getDataWatcher(), true));
        PacketPlayOutPlayerInfo packet = new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.REMOVE_PLAYER, this);
        if (!this.tablist) new BukkitRunnable() {
            @Override
            public void run() {
                connection.sendPacket(packet);
            }
        }.runTaskLater(Grapes.getGrapes(), 5);
    }

    /**
     * This method removes the NPC for all players.
     */
    public void destroy() {
        Bukkit.getOnlinePlayers().forEach(p -> ((CraftPlayer) p).getHandle().playerConnection.sendPacket(new PacketPlayOutEntityDestroy(this.getId())));
    }

    /**
     * This method displays the NPC for all Players.
     */
    public void display() {
        Bukkit.getOnlinePlayers().forEach(this::display);
    }

    /**
     * Getter for the NPCs ID.
     *
     * @return The NPCs UUID.
     */
    public UUID getNpcId() {
        return npcId;
    }

    /**
     * This method runs all tasks assigned to this NPC.
     *
     * @param type     The Type of click. Can be left-click or right-click.
     * @param operator The {@link GrapesPlayer}, who clicked the NPC.
     */
    public void callTasks(GrapesPlayer operator, ClickType type) {
        synchronized (tasks) {
            tasks.forEach(t -> t.click(operator, type));
        }
    }

    /**
     * This method adds all given tasks to the npc as soon as it is possible.
     * Sometimes it isn't possible, because the NPC just got clicked and the list cant be edited or a {@link java.util.ConcurrentModificationException} would be thrown.
     *
     * @param tasks An array of tasks, which contains all tasks, you want to add.
     */
    public void addTask(NPCTask... tasks) {
        synchronized (this.tasks) {
            Collections.addAll(this.tasks, tasks);
        }
    }
}
