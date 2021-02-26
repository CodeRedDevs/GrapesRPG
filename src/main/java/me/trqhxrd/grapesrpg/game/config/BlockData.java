package me.trqhxrd.grapesrpg.game.config;

import me.trqhxrd.grapesrpg.Grapes;
import me.trqhxrd.grapesrpg.api.objects.blocks.GrapesBlock;
import me.trqhxrd.grapesrpg.api.utils.config.Config;

import java.io.File;

/**
 * This class contains a config, that stores all information about blocks.
 *
 * @author Trqhxrd
 */
public class BlockData extends Config {

    /**
     * The instance of this config.
     */
    private static BlockData instance;

    /**
     * This constructor creates a instance of this config.
     */
    private BlockData() {
        super(new File(Grapes.getGrapes().getDataFolder(), "blocks.yml"));
    }

    /**
     * This method overwrites the old instance with a new one.
     * If the old one is not null, all values wil be saved.
     */
    public static void init() {
        if (instance != null) instance.save();
        instance = new BlockData();
    }

    /**
     * Getter for the instance of this class.
     * If the instance is null it will be initialized.
     *
     * @return The instance of this class.
     */
    public static BlockData getInstance() {
        if (instance == null) init();
        return instance;
    }
}
