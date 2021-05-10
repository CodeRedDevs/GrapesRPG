package me.trqhxrd.grapesrpg.anticheat.utils;

import me.trqhxrd.grapesrpg.api.utils.clock.Clock;
import org.bukkit.plugin.Plugin;

/**
 * This clock manages all the tasks regarding the anticheat.
 *
 * @author Trqhxrd
 */
public class ACClock extends Clock {

    public ACClock(Plugin plugin) {
        super(plugin, 0, 20);
    }
}
