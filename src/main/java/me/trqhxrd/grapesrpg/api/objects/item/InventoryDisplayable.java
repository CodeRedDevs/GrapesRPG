package me.trqhxrd.grapesrpg.api.objects.item;

import org.bukkit.inventory.ItemStack;

/**
 * This interface can be used for getting an "icon" for an ingame menu.
 *
 * @author Trqhxrd
 */
public interface InventoryDisplayable {
    /**
     * This method returns the icon for the item.
     *
     * @return The icon for the item.
     */
    ItemStack getDisplayItem();
}
