package me.trqhxrd.grapesrpg.api.utils;

import me.trqhxrd.grapesrpg.api.GrapesPlayer;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.command.CommandSender;

import java.util.UUID;

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
     * This method checks, if a string is numeric.
     *
     * @param strNum The string which should be checked.
     * @return true if the string is convertible into a double. Otherwise false.
     */
    public static boolean isNumeric(String strNum) {
        if (strNum == null) {
            return false;
        }
        try {
            double d = Double.parseDouble(strNum);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
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
     * This method checks if a string is an integer.
     *
     * @param str The string for which you want to check, if it is an integer,
     * @return true if the string is numeric and an integer.
     */
    public static boolean isInteger(String str) {
        if (str == null) return true;
        try {
            int i = Integer.parseInt(str);
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
    }

    /**
     * This method formats a string to a uuid.
     * @param uuid The string, which should be formatted.
     * @return The UUID with the value of the string.
     */
    public static UUID formatStringToUUID(String uuid) {
        int[] hyphen = new int[]{8, 12, 16, 20};

        StringBuilder s = new StringBuilder();

        char[] chars = uuid.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            for (int hyphenIndex : hyphen) if (i == hyphenIndex) s.append("-");
            s.append(chars[i]);
        }

        return UUID.fromString(s.toString());
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
        player.getWrappedObject().sendMessage(translateColorCodes(prefix.getRaw() + message));
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
        player.getWrappedObject().sendMessage(translateColorCodes(p.getRaw() + message));
    }

    /**
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
     * This method sends the default "You don't have permission to use..."-message to the CommandSender, who was given in the arguments.
     *
     * @param sender The person, who should receive the message.
     */
    public void noPermission(CommandSender sender) {
        sender.sendMessage(Utils.translateColorCodes("&cI'm sorry, but you do not have permission to execute this command! " +
                "If you believe that this is an error, please contact the server administrator!"));
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
