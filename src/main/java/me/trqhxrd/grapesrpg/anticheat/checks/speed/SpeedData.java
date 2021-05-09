package me.trqhxrd.grapesrpg.anticheat.checks.speed;

import me.trqhxrd.grapesrpg.anticheat.api.User;
import me.trqhxrd.grapesrpg.anticheat.api.data.ACData;
import org.bukkit.Location;

/**
 * Instances of this class store additional data for the speed-test.
 */
public class SpeedData extends ACData {

    /**
     * The location from where the player started to walk.
     */
    private final Location from;
    /**
     * The location to which the player moved.
     */
    private final Location to;

    /**
     * This creates a new instance of speed-data
     *
     * @param player The player, who will get checked.
     * @param from   The from-location from where the player started to move.
     * @param to     The to-location to which the player moved.
     */
    public SpeedData(User player, Location from, Location to) {
        super(SpeedCheck.class, player);
        this.from = from;
        this.to = to;
    }

    /**
     * Getter for the from-location.
     *
     * @return The from-location
     */
    public Location getFrom() {
        return from;
    }

    /**
     * Getter for the to-location.
     *
     * @return The to-location
     */
    public Location getTo() {
        return to;
    }
}
