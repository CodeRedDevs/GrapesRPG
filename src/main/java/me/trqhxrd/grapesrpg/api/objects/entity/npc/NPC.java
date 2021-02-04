package me.trqhxrd.grapesrpg.api.objects.entity.npc;

import com.mojang.authlib.GameProfile;
import net.minecraft.server.v1_16_R3.*;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_16_R3.CraftServer;
import org.bukkit.craftbukkit.v1_16_R3.CraftWorld;
import org.bukkit.craftbukkit.v1_16_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class NPC extends EntityPlayer {

    private static final List<NPC> npcs = new ArrayList<>();
    private final UUID npcId;
    private final boolean tablist;

    public NPC(String name, Location location) {
        this(UUID.randomUUID(), name, Skin.getSkin(name), location, true);
    }

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

        if (skin != null) skin.apply(this);

        //DISPLAY OVERLAY
        this.getDataWatcher().set(new DataWatcherObject<>(16, DataWatcherRegistry.a), (byte) 127);

        npcs.add(this);
    }

    public static List<NPC> getNpcs() {
        return npcs;
    }

    public void setLocation(Location location) {
        super.setLocation(location.getX(), location.getY(), location.getZ(), location.getYaw(), location.getPitch());
    }

    public void display(Player p) {
        PlayerConnection connection = ((CraftPlayer) p).getHandle().playerConnection;
        connection.sendPacket(new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.ADD_PLAYER, this));
        connection.sendPacket(new PacketPlayOutNamedEntitySpawn(this));
        connection.sendPacket(new PacketPlayOutEntityHeadRotation(this, (byte) (this.yaw * 256 / 360)));
        if (!this.tablist)
            connection.sendPacket(new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.REMOVE_PLAYER, this));
    }

    public void display() {
        Bukkit.getOnlinePlayers().forEach(this::display);
    }

    public UUID getNpcId() {
        return npcId;
    }
}
