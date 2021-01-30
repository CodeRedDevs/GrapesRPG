package me.trqhxrd.grapesrpg.event;

import me.trqhxrd.grapesrpg.Grapes;
import me.trqhxrd.grapesrpg.api.inventories.CraftingInventory;
import me.trqhxrd.grapesrpg.api.objects.recipe.GrapesRecipe;
import me.trqhxrd.grapesrpg.api.objects.recipe.GrapesRecipeChoice;
import me.trqhxrd.grapesrpg.api.objects.recipe.GrapesShapedRecipe;
import me.trqhxrd.grapesrpg.api.utils.Utils;
import me.trqhxrd.grapesrpg.api.utils.group.Group2;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.*;

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
                            ItemStack[] bindings = inv.getBindings();

                            if (slot == CraftingInventory.OUTPUT_SLOT) {
                                for (int i = 0; i < matrix.length; i++) {
                                    if (matrix[i] != null) {
                                        if (matrix[i].getAmount() > 1) matrix[i].setAmount(matrix[i].getAmount() - 1);
                                        else matrix[i] = null;
                                    }
                                }

                                ItemStack[] bindingsClone = new ItemStack[bindings.length];
                                for (int i = 0; i < bindings.length; i++)
                                    if (bindings[i] != null) bindingsClone[i] = bindings[i].clone();
                                    else bindingsClone[i] = null;

                                Set<ItemStack> compressed = new HashSet<>();
                                for (ItemStack binding : bindingsClone) {
                                    if (binding != null) {
                                        if (compressed.size() > 0) {
                                            for (ItemStack compress : compressed)
                                                if (compress != null && compress.isSimilar(binding)) compress.setAmount(compress.getAmount() + binding.getAmount());
                                        } else compressed.add(binding);
                                    }
                                }

                                ItemStack[] bindingsNew = new ItemStack[compressed.size()];

                                for (GrapesRecipe r : GrapesRecipe.getRecipes()) {
                                    if (r instanceof GrapesShapedRecipe) {
                                        if (r.check(matrix, bindings)) {
                                            List<Group2<GrapesRecipeChoice, Integer>> bindingsList = new ArrayList<>(((GrapesShapedRecipe) r).getBindings());
                                            int i = 0;
                                            boolean[] skip = new boolean[compressed.size()];
                                            for (Group2<GrapesRecipeChoice, Integer> grapesRecipeChoiceIntegerGroup2 : bindingsList) {
                                                ArrayList<ItemStack> itemStacks = new ArrayList<>(compressed);
                                                for (ItemStack is : itemStacks) {
                                                    if (!skip[i]) {
                                                        if (grapesRecipeChoiceIntegerGroup2.getX().check(is)) {
                                                            is.setAmount(Math.max(0, is.getAmount() - grapesRecipeChoiceIntegerGroup2.getY()));
                                                            bindingsNew[i] = is;
                                                            skip[i] = true;
                                                            i++;
                                                        }
                                                    }
                                                }
                                            }
                                            break;
                                        }
                                    }
                                }

                                inv.setMatrix(matrix);
                                inv.setBindings(bindingsNew);

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
                                if (r.check(matrix, bindings)) {
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
        } else {
            ItemStack is = e.getCurrentItem();
            if (is != null) {
                ItemMeta meta = is.getItemMeta();
                if (meta != null) {
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
