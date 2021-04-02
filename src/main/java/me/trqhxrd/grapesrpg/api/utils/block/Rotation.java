package me.trqhxrd.grapesrpg.api.utils.block;

import org.bukkit.Location;
import org.bukkit.block.BlockFace;

/**
 * This enum contains all possible block rotations.
 * If a block is rotatable it means he can be rotated around any axis.
 *
 * @author Trqhxrd
 */
public enum Rotation {
    NORTH(Direction.NORTH, BlockFace.NORTH, 135, 225, Trigger.YAW),
    SOUTH(Direction.SOUTH, BlockFace.SOUTH, 315, 360 + 45, Trigger.YAW),
    EAST(Direction.EAST, BlockFace.EAST, 225, 315, Trigger.YAW),
    WEST(Direction.WEST, BlockFace.WEST, 45, 135, Trigger.YAW),
    UP(null, BlockFace.UP, 45, 90, Trigger.PITCH),
    DOWN(null, BlockFace.DOWN, -90, -45, Trigger.PITCH);

    /**
     * This field contains the Direction, which is equivalent to this rotation.
     * This can be null, if this rotation is up or down, because there is no direction for that.
     */
    private final Direction direction;

    /**
     * This field contains the start angle of this rotation.
     */
    private final int start;
    /**
     * This field contains the end angle of this rotation.
     */
    private final int end;
    /**
     * This field contains the trigger for the check. This can either be yaw or pitch.
     */
    private final Trigger trigger;

    /**
     * This field contains the BlockFace, which is equivalent to this rotation.
     */
    private final BlockFace face;

    Rotation(Direction direction, BlockFace face, int start, int end, Trigger trigger) {
        this.direction = direction;
        this.start = start;
        this.end = end;
        this.trigger = trigger;
        this.face = face;
    }

    /**
     * This method returns the rotation for the players location.
     *
     * @param location The location with yaw and pitch.
     * @return The rotation for the yaw and pitch of the player.
     */
    public static Rotation getRotation(Location location) {
        Rotation rot = null;

        for (Rotation value : Rotation.values()) {
            if (value.trigger == Trigger.PITCH) {
                if (value.inRange(location.getPitch())) {
                    rot = value;
                    break;
                }
            }
        }

        if (rot == null) {
            for (Rotation value : Rotation.values()) {
                if (value.trigger == Trigger.YAW) {
                    if (value.inRange(location.getY())) {
                        rot = value;
                        break;
                    }
                }
            }
        }

        return rot;
    }

    /**
     * This method checks of a certain value is between the start and the end value.
     *
     * @param value The value, which should be checked.
     * @return true if the value is between the start and the end value of this state. Otherwise false.
     */
    public boolean inRange(double value) {
        return value <= this.start && value > this.end;
    }

    /**
     * This method returns the opposite rotation of the object, from which this was called.
     *
     * @return The opposite rotation of the one from which it was called.
     */
    public Rotation getOpposite() {
        switch (this) {
            case NORTH:
                return SOUTH;
            case WEST:
                return EAST;
            case EAST:
                return WEST;
            case UP:
                return DOWN;
            case DOWN:
                return UP;
            case SOUTH:
                return NORTH;
            default:
                return null;
        }
    }

    /**
     * Getter for the direction equivalent to this rotation.
     * This may be null.
     *
     * @return The direction equivalent to this rotation.
     */
    public Direction getDirection() {
        return direction;
    }

    /**
     * Getter for the BlockFace, which is equivalent to this rotation.
     *
     * @return The BlockFace, which is equivalent to this rotation.
     */
    public BlockFace getBlockFace() {
        return face;
    }

    private enum Trigger {
        PITCH, YAW
    }
}
