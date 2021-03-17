package me.trqhxrd.grapesrpg.api.objects.block.predefined;

import me.trqhxrd.grapesrpg.api.objects.block.GrapesBlockState;
import me.trqhxrd.grapesrpg.api.attribute.Rotatable;
import me.trqhxrd.grapesrpg.api.utils.Rotation;
import me.trqhxrd.grapesrpg.game.config.BlockData;

public class RotatableBlock extends GrapesBlockState implements Rotatable {

    private Rotation rotation;

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
    public Rotation getRotation() {
        return rotation;
    }

    @Override
    public void setRotation(Rotation rotation) {
        this.rotation = rotation;
    }
}
