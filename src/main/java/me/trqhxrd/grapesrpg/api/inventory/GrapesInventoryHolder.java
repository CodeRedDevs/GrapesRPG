package me.trqhxrd.grapesrpg.api.inventory;

/**
 * Any class, that implements this interface is able to store a menu.
 *
 * @author Trqhxrd
 */
public interface GrapesInventoryHolder {
    /**
     * This method returns the objects Menu.
     *
     * @return The objects menu.
     */
    GrapesInventory getMenu();
}
