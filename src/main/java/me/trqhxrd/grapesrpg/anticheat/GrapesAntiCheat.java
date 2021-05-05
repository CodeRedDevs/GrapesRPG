package me.trqhxrd.grapesrpg.anticheat;

import me.trqhxrd.grapesrpg.Grapes;
import me.trqhxrd.grapesrpg.anticheat.utils.ACClock;
import me.trqhxrd.grapesrpg.anticheat.utils.tasks.TimedPassTask;
import me.trqhxrd.grapesrpg.api.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * This is the API-Class with a lot of static method that you can use for manually running checks and stuff.
 * @author Trqhxrd
 */
public class GrapesAntiCheat {

    /**
     * The Plugin, which wil register the listeners 'n' stuff.
     */
    private static JavaPlugin plugin;
    /**
     * The clock, which manages all the tasks from the anticheat.
     */
    private static ACClock clock;

    /**
     * This should be called in the onEnable of your plugin.
     * @param plugin The plugin, which should register the listeners.
     */
    public static void init(JavaPlugin plugin) {
        GrapesAntiCheat.plugin = plugin;

        GrapesAntiCheat.clock = new ACClock(plugin);
        GrapesAntiCheat.clock.addTask(new TimedPassTask());
    }

    /**
     * This method should be called in the unload method.
     */
    public static void unload() {
        GrapesAntiCheat.plugin = null;
    }

    /**
     * This method sends a message to all players, who have the "grapes.anticheat.notify" and the console.
     * @param s The message.
     */
    public static void log(String s) {
        String message = Grapes.getGrapes().getUtils().getPrefix().colorize() + Utils.translateColorCodes(s);
        Bukkit.getOnlinePlayers().stream().filter(p -> p.hasPermission("grapes.anticheat.notify")).forEach(p -> p.sendMessage(message));
        Bukkit.getConsoleSender().sendMessage(message);
    }

    /**
     * Getter for the plugin, which owns the anticheat,
     * @return The plugin, which registers the listeners.
     */
    public static JavaPlugin getPlugin() {
        return plugin;
    }

    /**
     * getter for the anticheat-clock.
     * @return The anticheat-clock.
     */
    public static ACClock getClock() {
        return clock;
    }
}
