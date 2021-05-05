package me.trqhxrd.grapesrpg.anticheat.api.pass;

import me.trqhxrd.grapesrpg.anticheat.api.User;
import me.trqhxrd.grapesrpg.anticheat.api.utils.Area;
import org.bukkit.Location;

/**
 * A LocationPass allows you to bypass a certain Check if the Player is in a certain Area.
 *
 * @author Trqhxrd
 */
public class LocationPass extends Pass {

    /**
     * The Area in which the player is allowed to bypass a check.
     */
    private final Area appliedArea;

    /**
     * This constructor creates a new LocationPass.
     *
     * @param id          The ID of the check, that you can bypass with this check.
     * @param owner       The player, who can bypass the check.
     * @param appliedArea The area im which the player can bypass the check.
     */
    public LocationPass(String id, User owner, Area appliedArea) {
        super(id, owner);
        this.appliedArea = appliedArea;
    }

    /**
     * This constructor creates a new LocationPass.
     *
     * @param id     The ID of the check, that you can bypass with this check.
     * @param owner  The player, who can bypass the check.
     * @param origin The origin corner of the area.
     * @param area   The other corner of the area.
     */
    public LocationPass(String id, User owner, Location origin, Location area) {
        super(id, owner);
        this.appliedArea = new Area(origin, area);
    }

    /**
     * This method returns true, if the player is in the area specified in the constructor.
     *
     * @return Whether the player is in the area specified in the constructor.
     */
    @Override
    public boolean bypass() {
        return this.appliedArea.isInside(this.owner.getLocation());
    }
}
