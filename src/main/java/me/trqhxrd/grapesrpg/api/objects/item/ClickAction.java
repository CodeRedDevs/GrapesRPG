package me.trqhxrd.grapesrpg.api.objects.item;

import me.trqhxrd.grapesrpg.api.GrapesPlayer;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;

/**
 * Every Class, that implements this interface can be run by an item if it is used on a block or on the air.
 *
 * @author Trqhxrd
 */
public interface ClickAction {
    /**
     * This is the default-option, that will be executed, if no other click-action is found.
     * The default does nothing on run.
     */
    ClickAction DEFAULT = (p, i, b, f) -> false;

    /**
     * This is the method, that will be executed, if the item gets clicked.
     *
     * @param player The player who clicks the item.
     * @param item   The item, that he clicks.
     * @param block  The block, that is clicked. If this is null, the player did not aim at a block.
     * @param face   The BlockFace, that got clicked by the player. If this is null, the player did not aim at a block.
     * @return Returns whether the default-action if the item is clicked should be cancelled. If set to true, the default action will be supressed.
     */
    boolean onClick(GrapesPlayer player, GrapesItem item, Block block, BlockFace face);
}
