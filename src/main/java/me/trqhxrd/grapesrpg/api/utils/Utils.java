package me.trqhxrd.grapesrpg.api.utils;

import me.trqhxrd.grapesrpg.api.common.GrapesPlayer;
import net.md_5.bungee.api.ChatColor;
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
     * @param text The string of text to apply color/effects to
     * @return Returns a string of text with color/effects applied
     */
    public static String translateColorCodes(String text) {
        String[] message = text.replace("&", "统&").split("统");

        StringBuilder result = new StringBuilder();
        for (int i = 1; i < message.length; i++) {
            String part = message[i];
            if (part != null && !part.isBlank()) {
                if (part.charAt(0) == '&') {
                    if (part.charAt(1) == '#') {
                        char[] colorCode = new char[7];
                        part.getChars(1, 8, colorCode, 0);
                        String s = String.valueOf(colorCode);
                        part = part.replace("&" + s, ChatColor.of(s) + "");
                    } else part = ChatColor.translateAlternateColorCodes('&', part);
                    result.append(part);
                }
            }
        }
        return result.toString();
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
     * Sends a message to a specific player
     *
     * @param player  The Player, which you want to send the message.
     * @param message The message itself.
     */
    public void sendMessage(GrapesPlayer player, String message) {
        player.getSpigotPlayer().sendMessage(translateColorCodes(prefix.getRaw() + message));
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
        player.getSpigotPlayer().sendMessage(translateColorCodes(p.getRaw() + message));
    }

    /**
     * <<<<<<< HEAD
     * Sends a message to a player with a custom prefix.
     *
     * @param p       The custom prefix.
     * @param player  The player, which you want to send a message to.
     * @param message The message, which you want to send.
     */
    public void sendMessage(Prefix p, CommandSender player, String message) {
        player.sendMessage(translateColorCodes(p.getRaw() + message));
    }

    /**
     * Sends a message to a player with a custom prefix.
     *
     * @param player  The player, which you want to send a message to.
     * @param message The message, which you want to send.
     */
    public void sendMessage(CommandSender player, String message) {
        player.sendMessage(translateColorCodes(this.prefix.getRaw() + message));
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
