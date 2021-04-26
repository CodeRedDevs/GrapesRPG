package me.trqhxrd.grapesrpg.api;

import me.trqhxrd.grapesrpg.Grapes;
import me.trqhxrd.grapesrpg.api.attribute.Savable;
import me.trqhxrd.grapesrpg.api.economy.EcoSet;
import me.trqhxrd.grapesrpg.api.event.player.GrapesPlayerInitEvent;
import me.trqhxrd.grapesrpg.api.skill.Skills;
import me.trqhxrd.grapesrpg.api.utils.Prefix;
import me.trqhxrd.grapesrpg.api.utils.Wrapper;
import me.trqhxrd.grapesrpg.api.utils.packet.PacketReader;
import me.trqhxrd.grapesrpg.game.config.PlayerFile;
import me.trqhxrd.grapesrpg.game.inventory.EnderChestMenu;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import java.util.function.Consumer;

/**
 * This Class represents a GrapesRPG-Player.
 * It contains methods for sending a Player messages using a {@link Prefix};
 *
 * @author Trqhxrd
 * @see Prefix
 */
public class GrapesPlayer extends Wrapper<Player> implements Savable {
    /**
     * A list of all {@link GrapesPlayer}s.
     * Players will be added automatically added as soon as the {@link GrapesPlayer#GrapesPlayer(Player)} Constructor is called.
     */
    private static final Set<GrapesPlayer> players = new HashSet<>();
    /**
     * This is the PacketReader of the the Player.
     * Everytime the Player sends a Packet, it will run all the tasks in this PacketReader.
     */
    private transient final PacketReader packetReader;
    /**
     * This field contains a list of all skills the player has and their levels.
     */
    private final Skills skills;
    /**
     * This is the file, that stores the players data once he logged out.
     */
    private final PlayerFile file;
    /**
     * This field stores the EnderChestMenu of the player.
     */
    private final EnderChestMenu enderChest;
    /**
     * This field contains all data about the players financial status.
     */
    private final EcoSet ecoSet;

    /**
     * The Main Constructor for {@link GrapesPlayer}s.
     *
     * @param player A Player from the org.bukkit.entity Package.
     */
    public GrapesPlayer(Player player) {
        super(player);

        this.file = new PlayerFile(this);
        this.skills = new Skills(this);
        this.enderChest = new EnderChestMenu(this);
        this.ecoSet = new EcoSet(this);

        this.packetReader = new PacketReader(this);

        //Call GrapesPlayerInitEvent
        GrapesPlayerInitEvent event = new GrapesPlayerInitEvent(Grapes.getGrapes(), this);
        Bukkit.getPluginManager().callEvent(event);

        if (!event.isCancelled()) players.add(this);
    }

    /**
     * Returns a set, which contains all {@link GrapesPlayer}s.
     *
     * @return A set of {@link GrapesPlayer}s.
     */
    public static Set<GrapesPlayer> getPlayers() {
        return players;
    }

    /**
     * Can iterate through every Player.
     * Needs a Consumer.
     *
     * @param action The Action you want to perform for every Player.
     * @see Consumer
     */
    public static void forEach(Consumer<GrapesPlayer> action) {
        players.forEach(action);
    }

    /**
     * Used to get a player by his {@link UUID}.
     * Replacement for deprecated {@link GrapesPlayer#getByName(String)}.
     *
     * @param uuid The {@link UUID} of the GrapesPlayer you want to get.
     * @return The GrapesPlayer with the UUID given as an argument. In case, that there is no GrapesPlayer, it will return null.
     * @throws NullPointerException In case, that there is no player with the UUID given.
     */
    public static GrapesPlayer getByUniqueId(UUID uuid) {
        for (GrapesPlayer p : GrapesPlayer.getPlayers())
            if (p.getUniqueId().equals(uuid)) return p;
        return null;
    }

    /**
     * This method returns the GrapesPlayer with the same UUID as the player given. If there is no player with that UUId this method will throw a NullPointerException.
     *
     * @param player The player for which you want to get the GrapesPlayer.
     * @return The player with the same UUID as the UUID of the player from the {@link Player}.
     * @throws NullPointerException In case, that there is no player with the UUID given. If this exception is thrown, something is wrong because there has to be one GrapesPlayer for each {@link Player}.
     * @see UUID
     */
    public static GrapesPlayer getByPlayer(Player player) {
        return GrapesPlayer.getByUniqueId(player.getUniqueId());
    }

    /**
     * This method is able to get a Player with a certain name, but only if that player is online.
     *
     * @param name The name of the Player you want to get.
     * @return The GrapesPlayer with the name, which was give in the arguments. In case there is no Player with the given name, this method will return null.
     */
    public static GrapesPlayer getByName(String name) {
        for (GrapesPlayer p : GrapesPlayer.getPlayers())
            if (p.getName().equals(name)) return p;
        return null;
    }

    /**
     * This method returns true, if the Player with the {@link UUID} exists.
     *
     * @param uuid The UUID, for which you want to check id there is a player.
     * @return {@literal true -> Player exists / false -> Player doesn't exists.}
     */
    public static boolean exists(UUID uuid) {
        for (GrapesPlayer player : players)
            if (player.getUniqueId().equals(uuid)) return true;
        return false;
    }

    /**
     * Returns true, if the player with a certain name exists.
     *
     * @param name The name for which you want to check.
     * @return {@literal  true -> player exists / false -> player doesn't exists.}
     */
    public static boolean exists(String name) {
        for (GrapesPlayer player : players)
            if (player.getName().equals(name)) return true;
        return false;
    }

    /**
     * This method saves the data for all players, that are logged in.
     */
    public static void saveAll() {
        GrapesPlayer.forEach(GrapesPlayer::save);
    }

    /**
     * This method will save the current state of this class in some way.
     *
     * @param flush If flush is true, the changes will automatically be written to a file.
     */
    @Override
    public void save(boolean flush) {
        this.skills.save(false);
        this.enderChest.save(false);
        this.ecoSet.save(false);
        if (flush) this.file.save();
    }

    /**
     * This method just runs {@code this.save(true);}, which will save the state of this class and automatically write it to the file.
     * This method has to be overwritten tho.
     */
    @Override
    public void save() {
        this.save(true);
    }

    /**
     * Gets the Players name.
     *
     * @return The players name.
     */
    public String getName() {
        return wrappedObject.getName();
    }

    /**
     * Returns the Players {@link Location}.
     *
     * @return The players {@link Location}.
     */
    public Location getLocation() {
        return wrappedObject.getLocation();
    }

    /**
     * Sends the player a message using the default Plugin {@link Prefix}.
     *
     * @param s The Message, which you want to send. {@literal ('&' can also be used for color-codes.)}
     */
    public void sendMessage(String s) {
        Grapes.getGrapes().getUtils().sendMessage(this, s);
    }

    /**
     * Sends the player message using a custom {@link Prefix}.
     *
     * @param p The custom {@link Prefix}.
     * @param s The Message as a String.
     */
    public void sendMessage(Prefix p, String s) {
        Grapes.getGrapes().getUtils().sendMessage(p, this, s);
    }

    /**
     * Short way to get the legacy-{@link Player}s {@link UUID}.
     *
     * @return The Players UUID.
     */
    public UUID getUniqueId() {
        return wrappedObject.getUniqueId();
    }

    /**
     * Getter for the Players PacketReader.
     *
     * @return The GrapesPlayers PacketReader.
     */
    public PacketReader getPacketReader() {
        return packetReader;
    }

    /**
     * Getter for the players skill-list.
     *
     * @return The player skill-list.
     */
    public Skills getSkills() {
        return skills;
    }

    /**
     * Getter for the players data file.
     *
     * @return The players data file.
     */
    public PlayerFile getFile() {
        return file;
    }

    /**
     * Getter for the players enderchest.
     *
     * @return The enderchest of the player.
     */
    public EnderChestMenu getEnderChest() {
        return enderChest;
    }

    /**
     * Getter for the player's EcoSet.
     *
     * @return The player's EcoSet.
     */
    public EcoSet getEcoSet() {
        return ecoSet;
    }
}
