package me.trqhxrd.grapesrpg;

import me.trqhxrd.grapesrpg.api.common.GrapesPlayer;
import me.trqhxrd.grapesrpg.api.utils.Prefix;
import me.trqhxrd.grapesrpg.api.utils.Utils;
import me.trqhxrd.grapesrpg.event.PlayerJoinListener;
import me.trqhxrd.grapesrpg.event.PlayerQuitListener;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * The GrapesRPG-Main-Class.
 * It extends from a {@link JavaPlugin}.
 *
 * @author Trqhxrd
 */
public class Grapes extends JavaPlugin {

    /**
     * This is the instance of the GrapesRPG plugin.
     * It is used to register Listeners and other things, which need a JavaPlugin.
     * Also if you want to use Plugin-Specific {@link Utils}, it is usable to get this Plugins Utils.
     */
    private static Grapes grapes;

    /**
     * This object is used for sending Messages to the Player.
     *
     * @see Utils
     */
    private Utils utils;

    /**
     * You need this method, if you want to do something with the Plugin instance.
     * (e.g. Registering a listener.)
     *
     * @return The Plugins instance.
     */
    public static Grapes getGrapes() {
        return grapes;
    }

    /**
     * The default onEnable method for Bukkit/Spigot-Plugins.
     * Overwritten from the {@link JavaPlugin}
     *
     * @see JavaPlugin#onEnable()
     */
    @Override
    public void onEnable() {
        grapes = this;
        this.utils = new Utils(Prefix.of("&c[&aGra&bpes&c] &7"));
        for (Player p : Bukkit.getOnlinePlayers()) new GrapesPlayer(p);

        //Registering Listeners:
        new PlayerJoinListener();
        new PlayerQuitListener();
    }

    /**
     * The default onDisable method for Bukkit/Spigot-Plugins.
     *
     * @see JavaPlugin#onDisable()
     */
    @Override
    public void onDisable() {

    }

    /**
     * Getter for the {@link Utils}
     *
     * @return The Plugin-Specific Utils.
     */
    public Utils getUtils() {
        return utils;
    }
}