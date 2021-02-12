package me.trqhxrd.grapesrpg.api.common;

import me.trqhxrd.grapesrpg.Grapes;
import me.trqhxrd.grapesrpg.api.event.GrapesPlayerInitEvent;
import me.trqhxrd.grapesrpg.api.utils.PacketReader;
import me.trqhxrd.grapesrpg.api.utils.Prefix;
import me.trqhxrd.grapesrpg.api.utils.Wrapper;
import me.trqhxrd.grapesrpg.game.tasks.packet.NPCInteractionTask;
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
public class GrapesPlayer extends Wrapper<Player> {

    /**
     * A list of all {@link GrapesPlayer}s.
     * Players will be added automatically added as soon as the {@link GrapesPlayer#GrapesPlayer(Player)} Constructor is called.
     */
    private static final Set<GrapesPlayer> players = new HashSet<>();

    /**
     * This is the PacketReader of the the Player.
     * Everytime the Player sends a Packet, it will run all the tasks in this PacketReader.
     */
    private final PacketReader packetReader;

    /**
     * The Main Constructor for {@link GrapesPlayer}s.
     *
     * @param player A Player from the org.bukkit.entity Package.
     */
    public GrapesPlayer(Player player) {
        super(player);

        //Call GrapesPlayerInitEvent
        GrapesPlayerInitEvent event = new GrapesPlayerInitEvent(Grapes.getGrapes(), this);
        Bukkit.getPluginManager().callEvent(event);

        this.packetReader = new PacketReader(this);
        new NPCInteractionTask(this);

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
     */
    public static GrapesPlayer getByUniqueId(UUID uuid) {
        for (GrapesPlayer p : GrapesPlayer.getPlayers())
            if (p.getUniqueId().equals(uuid)) return p;
        throw new NullPointerException("The Player with UUID=" + uuid.toString() + " is null.");
    }

    /**
     * This method is deprecated.
     * The replacement is {@link GrapesPlayer#getUniqueId()}.
     * <p>
     * This method is able to get a Player with a certain name, but only if that player is online.
     *
     * @param name The name of the Player you want to get.
     * @return The GrapesPlayer with the name, which was give in the arguments. In case there is no Player with the given name, this method will return null.
     * @deprecated
     */
    @Deprecated
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
     * @deprecated
     */
    @Deprecated
    public static boolean exists(String name) {
        for (GrapesPlayer player : players)
            if (player.getName().equals(name)) return true;
        return false;
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
}
