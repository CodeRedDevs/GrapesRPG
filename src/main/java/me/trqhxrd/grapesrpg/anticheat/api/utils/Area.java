package me.trqhxrd.grapesrpg.anticheat.api.utils;

import com.google.common.base.Preconditions;
import org.bukkit.Location;

import java.util.Objects;

/**
 * An area has two corners and you can check if something is inside.
 *
 * @author Trqhxrd
 */
public class Area {

    /**
     * The first corner of the area.
     */
    private final Location origin;
    /**
     * The second corner of the area.
     */
    private final Location area;

    /**
     * This creates a new area with it's two corners.
     *
     * @param origin The corner with the lower values.
     * @param area   The corner with the higher values.
     */
    public Area(Location origin, Location area) {
        Preconditions.checkArgument(origin.getWorld() != null && area.getWorld() != null);
        Preconditions.checkArgument(origin.getWorld().getName().equals(area.getWorld().getName()));

        this.origin = new Location(origin.getWorld(), Math.min(origin.getX(), area.getX()), Math.min(origin.getY(), area.getY()), Math.min(origin.getZ(), area.getZ()));
        this.area = new Location(origin.getWorld(), Math.max(origin.getX(), area.getX()), Math.max(origin.getY(), area.getY()), Math.max(origin.getZ(), area.getZ()));
    }

    /**
     * This method checks if a location is inside of this area.
     *
     * @param location The location, that should be checked.
     * @return true, if the location is inside of the area.
     */
    public boolean isInside(Location location) {
        return Objects.requireNonNull(location.getWorld()).getName().equals(Objects.requireNonNull(this.origin.getWorld()).getName()) &&
                this.origin.getX() <= location.getX() && location.getX() < this.area.getX() &&
                this.origin.getY() <= location.getY() && location.getY() < this.area.getY() &&
                this.origin.getZ() <= location.getZ() && location.getZ() < this.area.getZ();
    }

    /**
     * Getter for the first corner of the area.
     *
     * @return The first corner of the area.
     */
    public Location getOrigin() {
        return origin;
    }

    /**
     * Getter for the second corner of the area.
     *
     * @return The second corner of the area.
     */
    public Location getArea() {
        return area;
    }
}
