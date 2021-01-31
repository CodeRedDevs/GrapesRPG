package me.trqhxrd.grapesrpg.event;

import me.trqhxrd.grapesrpg.Grapes;
import me.trqhxrd.grapesrpg.api.inventories.CraftingMenu;
import me.trqhxrd.grapesrpg.api.inventories.Menu;
import me.trqhxrd.grapesrpg.api.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * This Listener handles inventory-clicks.
 * For example the crafting-gui.
 *
 * @author Trqhxrd
 */
public class InventoryClickListener implements Listener {

    /**
     * A utility-field, which stores a set of all slots, the player is allowed to interact with.
     */
    private final Set<Integer> allowedSlots;

    /**
     * This constructor registers the listener and sets the value of the field {@link InventoryClickListener#allowedSlots}.
     */
    public InventoryClickListener() {
        Bukkit.getPluginManager().registerEvents(this, Grapes.getGrapes());

        this.allowedSlots = new HashSet<>();
        for (int i : CraftingMenu.CRAFTING_SLOTS) this.allowedSlots.add(i);
        for (int i : CraftingMenu.BINDING_SLOTS) this.allowedSlots.add(i);
        for (int i : CraftingMenu.UPGRADE_SLOTS) this.allowedSlots.add(i);
        this.allowedSlots.add(CraftingMenu.OUTPUT_SLOT);
    }

    /**
     * This is the handler-method.
     *
     * @param e The InventoryClickEvent.
     */
    @EventHandler()
    public void onInventoryClick(InventoryClickEvent e) {
        ItemStack is = e.getCurrentItem();
        if (is != null) {
            ItemMeta meta = is.getItemMeta();
            if (meta != null) {
                if (meta.hasDisplayName()) meta.setDisplayName(Utils.translateColorCodes(meta.getDisplayName()));
                if (meta.hasLore()) {
                    List<String> lore = meta.getLore();
                    if (lore != null)
                        for (int i = 0; i < lore.size(); i++) lore.set(i, Utils.translateColorCodes(lore.get(i)));
                    meta.setLore(lore);
                }
                is.setItemMeta(meta);
            }
        }

        if (e.getClickedInventory() != null) {
            InventoryHolder holder = e.getClickedInventory().getHolder();
            if (holder instanceof Menu) ((Menu) holder).handleMenu(e);
        }

    }
}
