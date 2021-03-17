package me.trqhxrd.grapesrpg.api.objects.block.predefined;

import me.trqhxrd.grapesrpg.api.objects.block.GrapesBlockState;
import me.trqhxrd.grapesrpg.api.utils.Direction;
import me.trqhxrd.grapesrpg.game.config.BlockData;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.block.data.Directional;

public class DirectableBlock extends GrapesBlockState {

    private Direction direction;

    /**
     * Here you can load values from the config.
     * This method will be run after a new object of a GrapesBlockState is created.
     *
     * @param s The path to the configuration-section. It is build like this: "{@literal x.y.z}".
     */
    @Override
    public void load(String s) {
        this.direction = Direction.valueOf(BlockData.getInstance().getString(s + ".dir"));
    }

    /**
     * This method saves the block-state to a config-file.
     *
     * @param s          The path to the configuration-section of this state.
     * @param saveToFile If set to true, the config needs to be written to a file. If you want to store large amounts of blocks at the same time it might be a good idea to set this to false and save the file after you wrote every entry to save resources.
     */
    @Override
    public void save(String s, boolean saveToFile) {
        BlockData.getInstance().set(s + ".dir", this.direction.name());
        if (saveToFile) BlockData.getInstance().save();
    }

    @Override
    public void update(Location location) {
        Block b = location.getBlock();
        org.bukkit.block.data.BlockData data = b.getBlockData();
        if (data instanceof Directional) ((Directional) data).setFacing(this.direction.getBlockFace());
        b.setBlockData(data);
    }

    public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }
}
