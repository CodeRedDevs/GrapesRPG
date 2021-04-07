package me.trqhxrd.grapesrpg.api.objects.item.artifact;

import me.trqhxrd.grapesrpg.Grapes;
import me.trqhxrd.grapesrpg.api.attribute.Serializable;
import me.trqhxrd.grapesrpg.api.objects.item.GrapesItem;
import me.trqhxrd.grapesrpg.api.utils.Utils;
import me.trqhxrd.grapesrpg.api.utils.clock.Clock;
import org.bukkit.Location;
import org.bukkit.entity.Item;
import org.bukkit.util.Vector;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * This class represents an artifact of the GrapesRPG-Plugin.
 * An Artifact is a item, which exists only one time.
 * If it gets destroyed it either gets spawned at it's home-location or it will be stored in a config and
 * you have to do something to spawn it in again.
 * <p>
 * If you want to create an artifact please use {@link Artifact#createArtifact(int, GrapesItem, Location)} or {@link Artifact#createArtifact(int, GrapesItem, Location, boolean)}.
 *
 * @author Trqhxrd
 */
public class Artifact implements Serializable<Artifact> {

    /**
     * This static field contains a map of all artifacts.
     * The keys are the id of the artifact stored in the value.
     */
    private static final Map<Integer, Artifact> artifacts = new HashMap<>();
    /**
     * This field contains the actual item, which should be spawned and that can be picked up and used by the player.
     */
    private final GrapesItem item;
    /**
     * This field stores the id of the artifact. If there are multiple items with the same id, all but one will be removed.
     */
    private final int id;
    /**
     * This field stores the location, where the item should be spawned in.
     */
    private final Location spawn;
    /**
     * This is the state of the artifact.
     * For an explanation take a look at {@link ArtifactState}.
     */
    private ArtifactState state;
    /**
     * If the item is currently dropped this field contains the item's location.
     * If the item is currently owned by a player or it did not got spawned yet this location will be {@code null}.
     */
    private Location location;
    /**
     * If the item got dropped, this field contains the actual entity.
     */
    private transient Item entity;

    /**
     * This constructor creates a new artifact.
     * It is unsafe to use. please use one of these static methods:
     * {@link Artifact#createArtifact(int, GrapesItem, Location, boolean)}.
     * {@link Artifact#createArtifact(int, GrapesItem, Location)}.
     *
     * @param id    The id of the artifact. This integer has to be unique.
     * @param item  The actual item, that the player should obtain once he picked up this artifact.
     * @param spawn The spawn-location of this artifact. This is where the artifact will be spawned once the old one got destroyed.
     * @param state The state of the artifact. For an explanation take a look at this class: {@link ArtifactState}.
     */
    private Artifact(int id, GrapesItem item, Location spawn, ArtifactState state) {
        this.item = item;
        this.spawn = spawn;
        this.id = id;
        this.state = state;
    }

    /**
     * This static method creates an artifact with the parameter, that were given.
     * The artifact will be despawned by default. It can be spawned using {@link Artifact#spawn()}.
     * If there is already an Artifact with the ID given, this method will return that artifact instead of creating a new one.
     * If you want to overwrite an old artifact please use {@link Artifact#createArtifact(int, GrapesItem, Location, boolean)} and set the last boolean to true.
     *
     * @param id            The id of your new artifact.
     * @param item          The item of your artifact.
     * @param spawnLocation The spawn location of your artifact.
     * @return If an artifact was created successfully it will be returned. if the artifact already existed the old artifact will be returned.
     */
    public static Artifact createArtifact(int id, GrapesItem item, Location spawnLocation) {
        return Artifact.createArtifact(id, item, spawnLocation, false);
    }

    /**
     * This static method creates an artifact with the parameter, that were given.
     * The artifact will be despawned by default. It can be spawned using {@link Artifact#spawn()}.
     * If there is already an Artifact with the ID given, this method will return that artifact instead of creating a new one.
     *
     * @param id            The id of your new artifact.
     * @param item          The item of your artifact.
     * @param spawnLocation The spawn location of your artifact.
     * @param force         If you want to overwrite an existing artifact you have to set this boolean to true.
     * @return If an artifact was created successfully it will be returned. if the artifact already existed the old artifact will be returned.
     */
    public static Artifact createArtifact(int id, GrapesItem item, Location spawnLocation, boolean force) {
        if (!artifacts.containsKey(id) || force) {
            Artifact a = new Artifact(id, item, spawnLocation, ArtifactState.DESPAWNED);
            artifacts.put(id, a);
            return a;
        } else return artifacts.get(id);
    }

    /**
     * This method updates the location of every artifact, that is spawned.
     * If the server stops and the locations would not be updated the item would spawn where it was dropped.
     * Even if it got moved by water or if it was dropped down a cliff.
     * This method runs automatically. If you want to disable the automatic execution of this method please take a look at {@link me.trqhxrd.grapesrpg.game.tasks.clock.ArtifactTask#execute(UUID, Clock)}.
     */
    public static void updateLocations() {
        artifacts.forEach((id, artifact) -> {
            if (artifact.getState() == ArtifactState.SPAWNED && artifact.getEntity() != null) artifact.setLocation(artifact.getEntity().getLocation());
            else if (artifact.getLocation() != null) artifact.setLocation(null);
        });
    }

    /**
     * This method returns the artifact with the item you gave.
     * If there is no artifact with that item, this method returns null.
     * Note: The items will not be compared. Instead we check for the id, which is stored in the item you gave.
     *
     * @param item The item, for which you want to get the artifact.
     * @return The artifact with the same item as the one given.
     */
    public static Artifact fromGrapesItem(GrapesItem item) {
        return Artifact.getArtifact((Integer) item.getNbt().get("grapes.artifact.id"));
    }

    /**
     * This method can be used to get the artifact with the id, that was given in the parameters.
     *
     * @param id The id, for which you want to check, if there is an artifact.
     * @return If there is an artifact with the id given, the artifact will be returned. other wise it will return null.
     */
    public static Artifact getArtifact(int id) {
        return artifacts.getOrDefault(id, null);
    }

    /**
     * This method is required by the {@link Serializable}-interface.
     *
     * @param object The serialized object, that needs to be deserialized.
     * @return An actual artifact with all the values, that were stored in the string-input.
     */
    public static Artifact deserialize(String object) {
        return Grapes.GSON.fromJson(object, Artifact.class);
    }

    /**
     * Getter for the map of all artifacts.
     *
     * @return The map, which stores all artifacts.
     */
    public static Map<Integer, Artifact> getArtifacts() {
        return artifacts;
    }

    /**
     * This method checks, if the artifact is despawned.
     * if it is, the artifact will be spawned at its spawn-location.
     *
     * @return The item, which was created. if no item was created it will return null.
     */
    public Item spawn() {
        return this.spawn(this.spawn);
    }

    /**
     * This method checks, if the artifact is despawned.
     * if it is, the artifact will be spawned at the given location.
     *
     * @param loc The location, where the artifact should be spawned.
     * @return The item, which was created. if no item was created it will return null.
     */
    public Item spawn(Location loc) {
        return this.spawn(loc, false);
    }

    /**
     * This method spawns the artifact at the given location if either the artifact is despawned or
     * if the force-parameter is set to true. Or both of there cases are true.
     * BE AWARE, THAT YOU CAN CREATE MASSIVE CHAOS IF YOU SPAWN IN AN ARTIFACT, WHICH IS ALREADY SPAWNED SOMEWHERE!
     *
     * @param loc   The location, where the item should be spawned.
     * @param force If this is set to true, the artifact wil be spawned. even if it already exists.
     * @return The item, which was created. if no item was created it will return null.
     */
    public Item spawn(Location loc, boolean force) {
        if (loc != null && loc.getWorld() != null && (this.state == ArtifactState.DESPAWNED || force)) {
            this.state = ArtifactState.SPAWNED;

            item.addNBT("grapes.artifact.id", this.id);

            this.entity = loc.getWorld().dropItem(loc, item.build());
            this.entity.setGravity(false);
            this.entity.setGlowing(true);
            this.entity.setVelocity(new Vector(0, 0, 0));
            this.entity.teleport(loc);
            this.entity.setCustomName(Utils.translateColorCodes(this.item.getName()));
            this.entity.setCustomNameVisible(true);
            return this.entity;
        }
        return null;
    }

    /**
     * Getter for the location, that will be entered into the config.
     *
     * @return The location, that the item will be spawned at after the server reboots.
     */
    public Location getLocation() {
        return location;
    }

    /**
     * Setter for the location, that the artifact will be spawned on after a server reboot.
     *
     * @param location The new location, where the artifact will be spawned after a server reboot.
     */
    public void setLocation(Location location) {
        this.location = location;
    }

    /**
     * Getter for the actual item, that the player can use once he obtained this artifact.
     *
     * @return The actual item, that can be used by the player.
     */
    public GrapesItem getItem() {
        return item;
    }

    /**
     * Getter for the spawn-location of this artifact.
     *
     * @return The spawn-location of this artifact.
     */
    public Location getSpawn() {
        return spawn;
    }

    /**
     * Getter for the id of this artifact.
     *
     * @return The id of this artifact.
     */
    public int getID() {
        return id;
    }

    /**
     * Getter for the entity, that represents this artifact once it lays on the ground.
     *
     * @return The entity, that represents this artifact once it lays on the ground.
     */
    public Item getEntity() {
        return entity;
    }

    /**
     * Setter for the item, that represents this artifact.
     *
     * @param entity The entity, that is supposed to represent this artifact.
     */
    public void setEntity(Item entity) {
        this.entity = entity;
    }

    /**
     * Getter for this artifacts state.
     *
     * @return The artifacts current state.
     */
    public ArtifactState getState() {
        return state;
    }

    /**
     * Setter for the artifacts state.
     *
     * @param state The artifacts new state.
     */
    public void setState(ArtifactState state) {
        this.state = state;
    }

    /**
     * This method serializes the Object, from which it will be executed.
     *
     * @return The serialized object.
     */
    @Override
    public String serialize() {
        return Grapes.GSON.toJson(this);
    }
}
