package me.trqhxrd.grapesrpg.api.objects.block.predefined;

import me.trqhxrd.grapesrpg.api.objects.block.GrapesBlockState;
import me.trqhxrd.grapesrpg.api.utils.block.Rotation;
import me.trqhxrd.grapesrpg.game.config.BlockData;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.block.data.Rotatable;
import org.bukkit.entity.Player;

/**
 * This class represents a BlockState, which is rotatable.
 * Rotatable means, it can face in any direction including up and down.
 * All possible options are visible here: {@link Rotation}.
 *
 * @author Trqhxrd
 */
public class RotatableBlock extends GrapesBlockState implements me.trqhxrd.grapesrpg.api.attribute.Rotatable {

    /**
     * This field stores the current rotation.
     * The default-rotation is {@link Rotation#NORTH}.
     */
    private Rotation rotation;

    /**
     * This constructor creates a new RotatableBlock.
     */
    public RotatableBlock() {
        this.rotation = Rotation.NORTH;
    }

    @Override
    public void onPlace(Location block, Player player) {
        this.setRotation(Rotation.getRotation(player.getLocation()));
    }

    /**
     * Here you can load values from the config.
     * This method will be run after a new object of a GrapesBlockState is created.
     *
     * @param s The path to the configuration-section. It is build like this: "{@literal x.y.z}".
     */
    @Override
    public void load(String s) {
        this.rotation = Rotation.valueOf(BlockData.getInstance().getString(s + ".rot"));
    }

    /**
     * This method saves the block-state to a config-file.
     *
     * @param s          The path to the configuration-section of this state.
     * @param saveToFile If set to true, the config needs to be written to a file. If you want to store large amounts of blocks at the same time it might be a good idea to set this to false and save the file after you wrote every entry to save resources.
     */
    @Override
    public void save(String s, boolean saveToFile) {
        BlockData.getInstance().set(s + ".rot", this.rotation.name());
        if (saveToFile) BlockData.getInstance().save();
    }

    @Override
    public void update(Location location) {
        Block b = location.getBlock();
        org.bukkit.block.data.BlockData data = b.getBlockData();
        if (data instanceof Rotatable) ((Rotatable) data).setRotation(this.rotation.getBlockFace());
        b.setBlockData(data);
    }

    /**
     * Getter for the block's rotation.
     *
     * @return The block's rotation.
     */
    @Override
    public Rotation getRotation() {
        return rotation;
    }

    /**
     * Setter for the block's rotation.
     *
     * @param rotation The blocks new rotation.
     */
    @Override
    public void setRotation(Rotation rotation) {
        this.rotation = rotation;
    }
}
