package me.trqhxrd.grapesrpg.event;

import me.trqhxrd.grapesrpg.Grapes;
import me.trqhxrd.grapesrpg.api.inventories.CraftingInventory;
import me.trqhxrd.grapesrpg.api.objects.recipe.GrapesRecipe;
import me.trqhxrd.grapesrpg.api.objects.recipe.GrapesShapedRecipe;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashSet;
import java.util.Objects;
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
        for (int i : CraftingInventory.CRAFTING_SLOTS) this.allowedSlots.add(i);
        for (int i : CraftingInventory.BINDING_SLOTS) this.allowedSlots.add(i);
        for (int i : CraftingInventory.UPGRADE_SLOTS) this.allowedSlots.add(i);
        this.allowedSlots.add(CraftingInventory.OUTPUT_SLOT);
    }

    /**
     * This is the handler-method.
     *
     * @param e The InventoryClickEvent.
     */
    @EventHandler()
    public void onInventoryClick(InventoryClickEvent e) {
        if (e.getView().getTitle().equals(CraftingInventory.TITLE)) {

            if (e.getClickedInventory() != null) {
                ItemStack cursor = e.getCursor() != null ? e.getCursor().clone() : null;
                ItemStack result = e.getClickedInventory().getItem(CraftingInventory.OUTPUT_SLOT) != null ?
                        Objects.requireNonNull(e.getClickedInventory().getItem(CraftingInventory.OUTPUT_SLOT)).clone() : null;

                if (Objects.equals(e.getClickedInventory(), e.getView().getTopInventory())) {

                    int slot = e.getSlot();

                    if (!this.allowedSlots.contains(slot)) {
                        e.setCancelled(true);
                        return;
                    }

                    //Cancel placing items in output-slot
                    if (slot == CraftingInventory.OUTPUT_SLOT && e.getClickedInventory().getItem(slot) == null && e.getCursor() != null) {
                        e.setCancelled(true);
                        return;
                    }

                    new BukkitRunnable() {
                        @Override
                        public void run() {
                            CraftingInventory inv = CraftingInventory.fromNative(e.getClickedInventory());
                            ItemStack[] matrix = inv.getMatrix();

                            if (slot == CraftingInventory.OUTPUT_SLOT) {
                                for (int i = 0; i < matrix.length; i++) {
                                    if (matrix[i] != null) {
                                        if (matrix[i].getAmount() > 1) matrix[i].setAmount(matrix[i].getAmount() - 1);
                                        else matrix[i] = null;
                                    }
                                }
                                inv.setMatrix(matrix);

                                if (cursor != null) {
                                    if (Objects.requireNonNull(cursor).isSimilar(result)) {
                                        inv.getResult().setAmount(inv.getResult().getAmount() + e.getCursor().getAmount());
                                        e.getWhoClicked().setItemOnCursor(inv.getResult());
                                        inv.setResult(null);
                                    }
                                }
                            }

                            CraftingInventory.Status status = CraftingInventory.Status.INVALID;

                            for (GrapesRecipe r : GrapesShapedRecipe.getRecipes()) {
                                if (r.check(matrix)) {
                                    inv.setStatus(CraftingInventory.Status.VALID);
                                    inv.setResult(r.getResult().build());
                                    status = CraftingInventory.Status.VALID;
                                    break;
                                }
                            }

                            if (status == CraftingInventory.Status.INVALID) inv.setResult(null);
                            inv.setStatus(status);
                        }
                    }.runTaskLater(Grapes.getGrapes(), 0);
                }
            }
        }
    }
}
