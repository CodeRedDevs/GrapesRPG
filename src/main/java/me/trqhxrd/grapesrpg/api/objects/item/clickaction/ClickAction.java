package me.trqhxrd.grapesrpg.api.objects.item.clickaction;

import me.trqhxrd.grapesrpg.api.GrapesPlayer;
import me.trqhxrd.grapesrpg.api.objects.item.GrapesItem;
import me.trqhxrd.grapesrpg.api.utils.ClickType;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;

import java.util.HashMap;
import java.util.Map;

/**
 * Every Class, that implements this interface can be run by an item if it is used on a block or on the air.
 *
 * @author Trqhxrd
 */
public interface ClickAction {

    /**
     * This Map stores the click-actions for the different items.
     */
    Map<Integer, ClickAction> CLICK_ACTIONS = new HashMap<>();
    /**
     * This is the default-option, that will be executed, if no other click-action is found.
     * The default does nothing on run.
     */
    ClickAction DEFAULT = (p, i, b, f, c) -> false;

    /**
     * This method adds a click-action to the map.
     *
     * @param id     The id of the item, for which you want to add a click-action.
     * @param action The click-action itself.
     * @param force  If set to true, a value in the map can be overwritten. If set to false and the map contains the key, the value won't be changed.
     */
    static void addClickAction(int id, ClickAction action, boolean force) {
        if (!CLICK_ACTIONS.containsKey(id) || force) CLICK_ACTIONS.put(id, action);
    }

    /**
     * This method adds a click-action to the map.
     * If the map already contains the value, the map won't be changed.
     *
     * @param id     The id of the item, for which you want to change the default click-action.
     * @param action The actual action, that you want to set.
     */
    static void addClickAction(int id, ClickAction action) {
        ClickAction.addClickAction(id, action, false);
    }

    /**
     * This method returns the default click-action for the item-id given.
     * If the map does not contain the id, this method will return null.
     *
     * @param id The id for which you want to get the default-id.
     * @return The ClickAction for that id.
     */
    static ClickAction getClickAction(int id) {
        return CLICK_ACTIONS.get(id);
    }

    /**
     * This is the method, that will be executed, if the item gets clicked.
     *
     * @param player The player who clicks the item.
     * @param item   The item, that he clicks.
     * @param block  The block, that is clicked. If this is null, the player did not aim at a block.
     * @param face   The BlockFace, that got clicked by the player. If this is null, the player did not aim at a block.
     * @param type   The button on the mouse, that got clicked. (left or right-click)
     * @return Returns whether the default-action if the item is clicked should be cancelled. If set to true, the default action will be suppressed.
     */
    boolean onClick(GrapesPlayer player, GrapesItem item, Block block, BlockFace face, ClickType type);
}
