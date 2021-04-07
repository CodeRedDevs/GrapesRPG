package me.trqhxrd.grapesrpg.api.objects.entity.npc;

import me.trqhxrd.grapesrpg.api.utils.Utils;
import me.trqhxrd.grapesrpg.api.utils.Wrapper;
import net.citizensnpcs.api.CitizensAPI;
import net.citizensnpcs.api.npc.NPC;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * This class represents Citizens-NPCs, which are used by GrapesRPG.
 *
 * @author Trqhxrd
 */
public class GrapesNPC extends Wrapper<NPC> {
    /**
     * This map contains all npcs.
     */
    private static final Map<UUID, GrapesNPC> npcs = new HashMap<>();

    /**
     * The id of the npc.
     * This is used for identifying the NPC.
     */
    private final UUID uuid;
    /**
     * The name of the npc.
     * ColorCodes can be used.
     */
    private final String name;
    /**
     * The spawn-location for the npc.
     */
    private final Location spawn;
    /**
     * This field contains all data about the npcs skin.
     */
    private Skin skin;
    /**
     * This field contains the action, that will be executed as soon as the player clicks on the npc.
     */
    private NPCClickAction clickAction;

    /**
     * This constructor creates a new npc with a random id.
     *
     * @param name  The name of the npc. Color Codes are supported.
     * @param spawn The spawn-location of the npc.
     */
    public GrapesNPC(String name, Location spawn) {
        this(UUID.randomUUID(), name, spawn, Skin.from(ChatColor.stripColor(name)));
    }

    /**
     * This constructor creates a new npc with a random id.
     *
     * @param name  The name of the npc. Color Codes are supported.
     * @param spawn The spawn-location of the npc.
     * @param uuid  The id of the npc.
     */
    public GrapesNPC(UUID uuid, String name, Location spawn) {
        this(uuid, name, spawn, Skin.from(uuid));
    }

    /**
     * This constructor creates a new npc with a random id.
     *
     * @param name  The name of the npc. Color Codes are supported.
     * @param spawn The spawn-location of the npc.
     * @param skin  The skin of this npc.
     */
    public GrapesNPC(String name, Location spawn, Skin skin) {
        this(UUID.randomUUID(), name, spawn, skin);
    }

    /**
     * This constructor creates a new npc.
     *
     * @param uuid  The id of the npc.
     * @param name  The name of the npc. Color codes are supported.
     * @param spawn The spawn-location of the npc.
     * @param skin  The skin of this npc.
     */
    public GrapesNPC(UUID uuid, String name, Location spawn, Skin skin) {
        super(CitizensAPI.getNPCRegistry().createNPC(EntityType.PLAYER, Utils.translateColorCodes(name)));

        this.uuid = uuid;
        this.name = Utils.translateColorCodes(name);
        this.spawn = spawn;
        this.clickAction = NPCClickAction.DEFAULT;

        this.setSkin(skin);

        npcs.put(this.uuid, this);
    }

    /**
     * Getter for the npc-map.
     *
     * @return A map containing all npcs.
     */
    public static Map<UUID, GrapesNPC> getNPCS() {
        return npcs;
    }

    /**
     * This method returns, if available the grapes-npc, which represents the npc given.
     *
     * @param npc The npc, for which you want to get the grapes-npc.
     * @return If a Grapes-npc exists for the npc given, it will be returned. Otherwise this method returns null.
     */
    public static GrapesNPC getByNative(NPC npc) {
        for (GrapesNPC grapesNPC : npcs.values()) if (npc.equals(grapesNPC.getWrappedObject())) return grapesNPC;
        return null;
    }

    /**
     * This method returns the npc with the id given.
     * If no npc with that id exists, this method returns null.
     *
     * @param uuid The uuid for which you want to get the npc.
     * @return The npc with the id given. If no npc with that id exists the method returns null.
     */
    public static GrapesNPC getByUUID(UUID uuid) {
        return npcs.getOrDefault(uuid, null);
    }

    /**
     * This method removes all npcs.
     * It is called as soon as the server shuts down to prevent Citizens2 from saving the npcs.
     */
    public static void destroyAll() {
        npcs.forEach((id, npc) -> npc.destroy());
    }

    /**
     * Getter for the npcs click-action.
     *
     * @return The npcs click-action.
     */
    public NPCClickAction getClickAction() {
        return clickAction;
    }

    /**
     * Setter for the npcs click-action.
     *
     * @param clickAction The npcs new click-action.
     */
    public void setClickAction(NPCClickAction clickAction) {
        this.clickAction = clickAction;
    }

    /**
     * Getter for the npcs UUID.
     *
     * @return The npcs uuid.
     */
    public UUID getUUID() {
        return uuid;
    }

    /**
     * Getter for the NPCs name.
     *
     * @return The NPCs current name
     */
    public String getName() {
        return name;
    }

    /**
     * Getter for the NPCs spawn-location.
     *
     * @return The NPCs spawn-location.
     */
    public Location getSpawn() {
        return spawn;
    }

    /**
     * Getter for the NPCs Skin.
     *
     * @return The NPCs Skin.
     */
    public Skin getSkin() {
        return skin;
    }

    /**
     * Setter for the NPCs Skin.
     *
     * @param skin The new Skin for the NPC.
     */
    public void setSkin(Skin skin) {
        this.skin = skin;
        if (this.skin != null) this.skin.apply(this);
    }

    /**
     * This method checks if the npc is spawned.
     *
     * @return Returns true, if the npc is spawned. Otherwise false.
     */
    public boolean isSpawned() {
        return this.getWrappedObject().isSpawned();
    }

    /**
     * This method spawns the npc at its spawn-location
     *
     * @return true, if the npc was spawned successfully. Otherwise false.
     */
    public boolean spawn() {
        if (!this.isSpawned()) {
            this.getWrappedObject().spawn(this.spawn);
            return true;
        }
        return false;
    }

    /**
     * This method despawns the npc at its spawn-location
     *
     * @return true, if the npc was despawned successfully. Otherwise false.
     */
    public boolean despawn() {
        if (this.isSpawned()) {
            this.getWrappedObject().despawn();
            return true;
        }
        return false;
    }

    /**
     * This method removes the NPC.
     * After running this method the NPC can't be spawned in again.
     */
    public void destroy() {
        this.getWrappedObject().destroy();
    }
}
