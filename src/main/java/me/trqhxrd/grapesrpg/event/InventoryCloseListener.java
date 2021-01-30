package me.trqhxrd.grapesrpg.event;

import me.trqhxrd.grapesrpg.Grapes;
import me.trqhxrd.grapesrpg.api.inventories.CraftingInventory;
import org.bukkit.Bukkit;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

/**
 * This listener drops the inventory-content, if you close certain inventories (for example the crafting-gui).
 */
public class InventoryCloseListener implements Listener {

    /**
     * This constructor registers the listener.
     */
    public InventoryCloseListener() {
        Bukkit.getPluginManager().registerEvents(this, Grapes.getGrapes());
    }

    /**
     * The handler-method.
     *
     * @param e The InventoryCloseEvent.
     */
    @EventHandler
    public void onInventoryClose(InventoryCloseEvent e) {
        if (e.getView().getTitle().equals(CraftingInventory.TITLE)) {
            this.dropItems((Player) e.getPlayer(), e.getInventory(), CraftingInventory.CRAFTING_SLOTS);
            this.dropItems((Player) e.getPlayer(), e.getInventory(), CraftingInventory.BINDING_SLOTS);
            this.dropItems((Player) e.getPlayer(), e.getInventory(), CraftingInventory.UPGRADE_SLOTS);
        }
    }

    private void dropItems(Player player, Inventory inv, int... slots) {
        for (int i : slots) {
            ItemStack is = inv.getItem(i);
            if (is != null) {
                Item item = player.getWorld().dropItem(player.getLocation(), is);
                item.setPickupDelay(0);
                item.setOwner(player.getUniqueId());
                item.setThrower(player.getUniqueId());
            }
        }
    }
}
