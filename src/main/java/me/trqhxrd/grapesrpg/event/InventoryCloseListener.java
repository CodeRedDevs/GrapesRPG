package me.trqhxrd.grapesrpg.event;

import me.trqhxrd.grapesrpg.api.attribute.Register;
import me.trqhxrd.grapesrpg.api.inventories.Menu;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.InventoryHolder;

/**
 * This listener drops the inventory-content, if you close certain inventories (for example the crafting-gui).
 */
@Register
public class InventoryCloseListener implements Listener {

    /**
     * The handler-method.
     *
     * @param e The InventoryCloseEvent.
     */
    @EventHandler
    public void onInventoryClose(InventoryCloseEvent e) {
        InventoryHolder holder = e.getView().getTopInventory().getHolder();
        if (holder instanceof Menu) ((Menu) holder).handleMenuClose(e);
    }
}
