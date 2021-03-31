package me.trqhxrd.grapesrpg.api.utils.block;

import org.bukkit.block.BlockFace;

/**
 * This enum contains all possible block directions.
 * If a block is directable it means he can be rotated around his y axis. Only compass directions are valid directions.
 *
 * @author Trqhxrd
 */
public enum Direction {
    NORTH(Rotation.NORTH, BlockFace.NORTH, 135, 225),
    SOUTH(Rotation.SOUTH, BlockFace.SOUTH, 315, 45),
    EAST(Rotation.EAST, BlockFace.EAST, 225, 315),
    WEST(Rotation.WEST, BlockFace.WEST, 45, 135);

    /**
     * This field contains the equivalent block-rotation.
     */
    private final Rotation rotation;
    /**
     * This field contains the BlockFace-Equivalent to this direction.
     */
    private final BlockFace blockFace;
    /**
     * This is the start-value of the direction.
     */
    private final int startYaw;
    /**
     * This is the end-value of the direction.
     */
    private final int endYaw;

    /**
     * @param rotation  The rotation, which is equivalent to this direction.
     * @param blockFace The BlockFace, which is equivalent to this direction.
     * @param startYaw  The start-yaw of this direction.
     * @param endYaw    The end-yaw of this direction.
     */
    Direction(Rotation rotation, BlockFace blockFace, int startYaw, int endYaw) {
        this.rotation = rotation;
        this.blockFace = blockFace;
        this.startYaw = startYaw;
        if (endYaw < startYaw) endYaw += 360;
        this.endYaw = endYaw;
    }

    /**
     * This method returns the direction of a certain angle.
     * The angle will be entered in {@literal 0° - 360°}
     *
     * @param angle The angle for which you want to get the direction.
     * @return The direction of that angle.
     */
    public static Direction getDirection(double angle) {
        for (Direction dir : Direction.values()) {
            if (dir.isInRange(angle)) return dir;
        }
        return null;
    }

    /**
     * This method returns the opposite direction of this direction.
     * For example it will return {@code Direction#NORTH} when the object from which it was called is equivalent to {@code Direction#SOUTH}.
     *
     * @return The opposite direction to this direction.
     */
    public Direction getOpposite() {
        switch (this) {
            case NORTH:
                return SOUTH;
            case SOUTH:
                return NORTH;
            case EAST:
                return WEST;
            case WEST:
                return EAST;
            default:
                return null;
        }
    }

    /**
     * This method checks if a certain yaw is in this direction.
     *
     * @param yaw The angle for which you want to check, if it is in range of this direction.
     * @return true if the yaw is between this directions start and end. Otherwise false.
     */
    public boolean isInRange(double yaw) {
        if (yaw < 45) yaw += 360;
        return yaw >= this.startYaw && yaw < this.endYaw;
    }

    /**
     * Getter for the start-yaw of this direction.
     *
     * @return The start-yaw of this direction.
     */
    public int getStartYaw() {
        return startYaw;
    }

    /**
     * getter for the end-yaw of this direction.
     *
     * @return The end-yaw of this direction.
     */
    public int getEndYaw() {
        return endYaw;
    }

    /**
     * Getter for an array of start- and end- yaw.
     *
     * @return An array of the start- and the end-yaw
     */
    public int[] getRange() {
        return new int[]{this.startYaw, this.endYaw};
    }

    /**
     * Getter for the equivalent rotation of this direction.
     *
     * @return The rotation, which is equivalent to this direction.
     */
    public Rotation getRotation() {
        return rotation;
    }

    /**
     * Getter for the BlockFace, which is equivalent to this direction.
     *
     * @return The BlockFace, which is equivalent to this direction.
     */
    public BlockFace getBlockFace() {
        return blockFace;
    }
}
