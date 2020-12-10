package me.trqhxrd.grapesrpg.api.common;

import me.trqhxrd.grapesrpg.Grapes;
import me.trqhxrd.grapesrpg.api.utils.Prefix;
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
public class GrapesPlayer {

    /**
     * A list of all {@link GrapesPlayer}s.
     * Players will be added automatically added as soon as the {@link GrapesPlayer#GrapesPlayer(Player)} Constructor is called.
     */
    private static final Set<GrapesPlayer> players = new HashSet<>();
    /**
     * An instance of the legacy Spigot {@link Player}.
     * Will be set in the {@link GrapesPlayer#GrapesPlayer(Player)} Constructor.
     */
    private final Player spigotPlayer;

    /**
     * The Main Constructor for {@link GrapesPlayer}s.
     *
     * @param spigotPlayer A legacy Player, for which you want to access new
     */
    public GrapesPlayer(Player spigotPlayer) {
        this.spigotPlayer = spigotPlayer;
        players.add(this);
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
     * Short way for {@link GrapesPlayer#getPlayers()#forEach(Consumer)}.
     *
     * @param action The Action you want to perform for every Player.
     * @see Consumer
     */
    public static void forEach(Consumer<GrapesPlayer> action) {
        players.forEach(action);
    }

    /**
     * Returns the legacy-spigot-{@link Player}.
     *
     * @return The Spigot-Player, for which this is the extension.
     */
    public Player getSpigotPlayer() {
        return spigotPlayer;
    }

    /**
     * Short way for {@link GrapesPlayer#getSpigotPlayer()#getName()}.
     *
     * @return The players name.
     */
    public String getName() {
        return spigotPlayer.getName();
    }

    /**
     * Short way for {@link GrapesPlayer#getSpigotPlayer()#getLocation()}.
     *
     * @return The players {@link Location}.
     */
    public Location getLocation() {
        return spigotPlayer.getLocation();
    }

    /**
     * Sends the player a message using the default Plugin {@link Prefix}.
     *
     * @param s The Message, which you want to send. ('&' can also be used for color-codes.)
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
     * Long way would be {@link GrapesPlayer#getSpigotPlayer()#getUniqueId()}.
     *
     * @return The Players UUID.
     */
    public UUID getUniqueId() {
        return spigotPlayer.getUniqueId();
    }

    /**
     * Used to get a player by his {@link UUID}.
     * Replacement for deprecated {@link GrapesPlayer#getByName(String)}.
     *
     * @param uuid The {@link UUID} of the GrapesPlayer you want to get.
     * @return The GrapesPlayer with the UUID given as an argument. In case, that there is no GrapesPlayer, it will return null.
     */
    public GrapesPlayer getByUniqueId(UUID uuid) {
        for (GrapesPlayer p : GrapesPlayer.getPlayers())
            if (p.getUniqueId().equals(uuid)) return p;
        return null;
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
    public GrapesPlayer getByName(String name) {
        for (GrapesPlayer p : GrapesPlayer.getPlayers())
            if (p.getName().equals(name)) return p;
        return null;
    }
}
