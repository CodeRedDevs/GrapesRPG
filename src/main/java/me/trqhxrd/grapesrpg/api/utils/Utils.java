package me.trqhxrd.grapesrpg.api.utils;

import me.trqhxrd.grapesrpg.api.common.GrapesPlayer;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

/**
 * A utils class used for sending messages.
 *
 * @author Trqhxrd
 */
public class Utils {

    /**
     * The {@link Prefix} you want to use for the messages, which will be send with this object.
     */
    private final Prefix prefix;

    /**
     * Initializes a new Utils-Object with a certain Prefix.
     *
     * @param prefix the Prefix , which you want to use.
     */
    public Utils(Prefix prefix) {
        this.prefix = prefix;
    }

    /**
     * Sends a message to all players on the server.
     *
     * @param message The message, which you want to send.
     */
    public void broadcast(String message) {
        GrapesPlayer.forEach(p -> sendMessage(p, message));
    }

    /**
     * Sends a message to a specific player.
     *
     * @param player  The Player, which you want to send the message.
     * @param message The message itself.
     */
    public void sendMessage(GrapesPlayer player, String message) {
        player.getSpigotPlayer().sendMessage(ChatColor.translateAlternateColorCodes(prefix.getColorChar(), prefix.colorize() + message));
    }

    /**
     * Broadcasts a chat-message to all players using a custom prefix.
     *
     * @param p       The custom prefix.
     * @param message The message itself.
     */
    public void broadcast(Prefix p, String message) {
        GrapesPlayer.forEach(pl -> sendMessage(p, pl, message));
    }

    /**
     * Sends a message to a player with a custom prefix.
     *
     * @param p       The custom prefix.
     * @param player  The player, which you want to send a message to.
     * @param message The message, which you want to send.
     */
    public void sendMessage(Prefix p, GrapesPlayer player, String message) {
        player.getSpigotPlayer().sendMessage(ChatColor.translateAlternateColorCodes(p.getColorChar(), p.colorize() + message));
    }

    /**
     * Sends a message to a CommandSender with a custom prefix.
     *
     * @param p       The custom prefix.
     * @param cs      The CommandSender, which you want to send a message to.
     * @param message The message, which you want to send.
     * @see CommandSender
     */
    public void sendMessage(Prefix p, CommandSender cs, String message) {
        cs.sendMessage(ChatColor.translateAlternateColorCodes(p.getColorChar(), p.colorize() + message));
    }

    /**
     * Sends a message to a specific CommandSender.
     *
     * @param cs  The CommandSender, which you want to send the message.
     * @param message The message itself.
     * @see CommandSender
     */
    public void sendMessage(CommandSender cs, String message) {
        cs.sendMessage(ChatColor.translateAlternateColorCodes(prefix.getColorChar(), prefix.colorize() + message));
    }

    /**
     * Returns the prefix, which you set in the constructor or using the setter.
     *
     * @return The prefix of this utils-object.
     */
    public Prefix getPrefix() {
        return prefix;
    }
}
