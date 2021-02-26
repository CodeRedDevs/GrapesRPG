package me.trqhxrd.grapesrpg.api.objects.blocks;

import me.trqhxrd.grapesrpg.api.GrapesPlayer;
import me.trqhxrd.grapesrpg.api.utils.ClickType;
import org.bukkit.Location;

/**
 * A GrapesBlockState is the data, a block stores.
 * It does not depend on a location, which means, that a BlockState can be moved freely between blocks.
 * Please note, that you can't make a BlockState with a constructor, which takes parameters.
 * If you do so, the method, which creates an object of it won't work.
 *
 * @author Trqhxrd
 */
public class GrapesBlockState {

    /**
     * This method will be executed, if the block gets clicked.
     *
     * @param player The Player, who clicked the block.
     * @param block  The block, which got clicked. (This Block stores this GrapesBlockState)
     * @param type   The Type of the click. (If it was left or right-click)
     * @return If you return true, the default-action of the block will be cancelled. (example: If you click a chest and return true, the chest won't open)
     */
    public boolean onClick(GrapesPlayer player, GrapesBlock block, ClickType type) {
        return false;
    }

    /**
     * Here you can load values from the config.
     * This method will be run after a new object of a GrapesBlockState is created.
     *
     * @param s The path to the configuration-section. It is build like this: "{@literal x.y.z}".
     */
    public void load(String s) {
    }

    /**
     * This method saves the block-state to a config-file.
     *
     * @param s          The path to the configuration-section of this state.
     * @param saveToFile If set to true, the config needs to be written to a file. If you want to store large amounts of blocks at the same time it might be a good idea to set this to false and save the file after you wrote every entry to save resources.
     */
    public void save(String s, boolean saveToFile) {
    }

    /**
     * This will write the values in the state to the original Bukkit-Block ate the location of our GrapesBlock.
     *
     * @param location The location of the block.
     */
    public void update(Location location) {
    }
}
