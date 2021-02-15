package me.trqhxrd.grapesrpg.event;

import me.trqhxrd.grapesrpg.api.attribute.Register;
import me.trqhxrd.grapesrpg.api.inventories.Menu;
import me.trqhxrd.grapesrpg.api.objects.item.GrapesItem;
import me.trqhxrd.grapesrpg.api.utils.Utils;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

/**
 * This Listener handles inventory-clicks.
 * For example the crafting-gui.
 *
 * @author Trqhxrd
 */
@Register
public class InventoryClickListener implements Listener {

    /**
     * This is the handler-method.
     *
     * @param e The InventoryClickEvent.
     */
    @EventHandler()
    public void onInventoryClick(InventoryClickEvent e) {
        ItemStack is = e.getCurrentItem();

        // Update item lore on click
        if (is != null) {
            GrapesItem item = GrapesItem.fromItemStack(is);
            if (item != null) {
                e.setCurrentItem(item.build());
            }
        }

        // Run Menu options
        if (e.getClickedInventory() != null) {
            InventoryHolder holder = e.getClickedInventory().getHolder();
            if (holder instanceof Menu) ((Menu) holder).handleMenuClick(e);
        }

        // Update colors in meta etc.
        if (is != null) {
            ItemMeta meta = is.getItemMeta();
            if (meta != null) {
                if (meta.getDisplayName().equals(ChatColor.stripColor(meta.getDisplayName()))) {
                    if (meta.hasDisplayName()) meta.setDisplayName(Utils.translateColorCodes(meta.getDisplayName()));
                    if (meta.hasLore()) {
                        List<String> lore = meta.getLore();
                        if (lore != null) for (int i = 0; i < lore.size(); i++) lore.set(i, Utils.translateColorCodes(lore.get(i)));
                        meta.setLore(lore);
                    }
                    is.setItemMeta(meta);
                }
            }
        }
    }
}
