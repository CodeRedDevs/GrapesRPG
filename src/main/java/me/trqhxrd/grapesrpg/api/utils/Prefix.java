package me.trqhxrd.grapesrpg.api.utils;

import me.trqhxrd.grapesrpg.api.common.GrapesPlayer;
import org.bukkit.ChatColor;

/**
 * This class represents a Prefix for a Message, which can be send to a Player using {@link me.trqhxrd.grapesrpg.api.common.GrapesPlayer#sendMessage(Prefix, String)} or {@link Utils#sendMessage(Prefix, GrapesPlayer, String)}.
 *
 * @author Trqhxrd
 */
public class Prefix {
    /**
     * The actual String, which you want to have as your Plugin-Prefix.
     * Can be set using the Constructor {@link Prefix#Prefix(String)} or {@link Prefix#Prefix(String, char)} or with the setter {@link Prefix#setRaw(String)}.
     */
    private String raw;
    /**
     * The character, which you want to use while writing your messages. It will be translated for sending the message.
     * (default {@link ChatColor#COLOR_CHAR})
     */
    private char colorChar;

    /**
     * Creates a new Prefix-Object.
     *
     * @param raw The String you want to have as your prefix. (Use '&' + 0-9 / a-f / k-r to symbolize a color. https://minecraft.gamepedia.com/Formatting_codes for color-codes)
     */
    public Prefix(String raw) {
        this.raw = raw;
        this.colorChar = '&';
    }

    /**
     * Creates a new Prefix-Object with a custom color-translate-char.
     *
     * @param raw       The Prefix, you want to have in front of your message.
     * @param colorChar The Color char, which you use in you messages and want to be translated.
     */
    public Prefix(String raw, char colorChar) {
        this.raw = raw;
        this.colorChar = colorChar;
    }

    /**
     * A static method, which creates a Prefix with raw as the text and the default color-char.
     *
     * @param raw The Text, which you want to have in front of your message.
     * @return A new Prefix with the set values.
     */
    public static Prefix of(String raw) {
        return new Prefix(raw);
    }

    /**
     * Returns the char, which will be translated, if you use this prefix for your message.
     *
     * @return The color-char.
     */
    public char getColorChar() {
        return colorChar;
    }

    /**
     * Sets the color-char, which will be translated.
     *
     * @param colorChar The character, which you want to be translated in your messages.
     */
    public void setColorChar(char colorChar) {
        this.colorChar = colorChar;
    }

    /**
     * Returns the colored Prefix.
     *
     * @return the prefix but with colors in it.
     */
    public String colorize() {
        return this.colorize(colorChar);
    }

    /**
     * Colorizes the prefix using a custom char.
     *
     * @param colorChar the character you want to use.
     * @return The colorized version of raw.
     * @deprecated
     */
    public String colorize(char colorChar) {
        this.raw = ChatColor.translateAlternateColorCodes(colorChar, raw);
        return this.raw;
    }

    /**
     * Returns the raw version of the prefix.
     *
     * @return the raw prefix
     */
    public String getRaw() {
        return raw;
    }

    /**
     * Sets the raw version of the prefix.
     *
     * @param raw The raw version of the prefix, which you want to use.
     */
    public void setRaw(String raw) {
        this.raw = raw;
    }
}
