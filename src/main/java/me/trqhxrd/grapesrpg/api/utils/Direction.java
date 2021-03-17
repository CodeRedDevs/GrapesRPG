package me.trqhxrd.grapesrpg.api.utils;

import org.bukkit.block.BlockFace;

public enum Direction {
    NORTH(Rotation.NORTH, BlockFace.NORTH),
    SOUTH(Rotation.SOUTH,BlockFace.SOUTH),
    EAST(Rotation.EAST,BlockFace.EAST),
    WEST(Rotation.WEST,BlockFace.WEST);

    private final Rotation rotation;
    private final BlockFace blockFace;

    Direction(Rotation rotation, BlockFace blockFace) {
        this.rotation = rotation;
        this.blockFace = blockFace;
    }

    public Rotation getRotation() {
        return rotation;
    }

    public BlockFace getBlockFace() {
        return blockFace;
    }
}
