package me.trqhxrd.grapesrpg.game.config;

import me.trqhxrd.grapesrpg.Grapes;
import me.trqhxrd.grapesrpg.api.utils.config.Config;
import org.bukkit.Bukkit;
import org.bukkit.Location;

import java.io.File;

/**
 * This config stores all the locations for all the NPCs.
 *
 * @author Trqhxrd
 */
public class NPCLocationsConfig extends Config {

    /**
     * This field contains an instance of this config.
     */
    private static NPCLocationsConfig instance;

    /**
     * This constructor creates a new instance of this config.
     */
    public NPCLocationsConfig() {
        super(new File(Grapes.getGrapes().getDataFolder(), "locations/npc.yml"));
    }

    /**
     * This static method returns the locations, which is stored for that entry name.
     *
     * @param entryName The entry-name, that should be queried.
     * @return The location stored at that spot.
     */
    public static Location getLocation(String entryName) {
        NPCLocationsConfig.init();
        if (!instance.contains(entryName)) {
            instance.set(entryName, new Location(Bukkit.getWorlds().get(0), 0, 255, 0));
            instance.save();
        }
        return instance.getData().getLocation(entryName);
    }

    /**
     * This method sets a location in the config.
     *
     * @param entryName The spot, where the location should be saved.
     * @param location  The location, that you want to store.
     */
    public static void setLocation(String entryName, Location location) {
        NPCLocationsConfig.init();
        instance.set(entryName, location);
        instance.save();
    }

    /**
     * This method initializes the instance-field.
     */
    private static void init() {
        if (instance == null) instance = new NPCLocationsConfig();
    }
}
