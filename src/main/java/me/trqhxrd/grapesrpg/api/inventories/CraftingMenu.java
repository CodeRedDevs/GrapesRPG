package me.trqhxrd.grapesrpg.api.inventories;

import me.trqhxrd.grapesrpg.Grapes;
import me.trqhxrd.grapesrpg.api.objects.recipe.GrapesRecipe;
import me.trqhxrd.grapesrpg.api.objects.recipe.GrapesRecipeChoice;
import me.trqhxrd.grapesrpg.api.objects.recipe.GrapesShapedRecipe;
import me.trqhxrd.grapesrpg.api.utils.ItemBuilder;
import me.trqhxrd.grapesrpg.api.utils.Utils;
import me.trqhxrd.grapesrpg.api.utils.group.Group2;
import org.bukkit.Material;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.*;

public class CraftingMenu extends Menu {

    /**
     * This constant contains the title of the CraftingInventory.
     * It is used for checking recipes in the {@link me.trqhxrd.grapesrpg.event.InventoryClickListener}.
     */
    public static final String TITLE = "&#065499Crafting";

    /**
     * These integers are the slots, which will contain binding items.
     * Binding items are a special ingredient, which are needed by some special crafting recipes.
     */
    public static final int[] BINDING_SLOTS = {9, 18, 27, 36};

    /**
     * The default 3x3 crafting matrix.
     * Every integer is one slot in the inventory.
     */
    public static final int[] CRAFTING_SLOTS = {11, 12, 13, 20, 21, 22, 29, 30, 31};

    /**
     * These are slots for additional upgrades like runes or enchanted books.
     * Upgrades are not needed to create a valid recipe but they are able to modify the output of the recipe.
     */
    public static final int[] UPGRADE_SLOTS = {47, 48, 49, 50, 51};

    /**
     * These slots will change their item, depending on the status of the current recipe in the inventory.
     * If the recipe is invalid, the items in these slots will become {@link CraftingMenu#ITEM_RECIPE_INVALID}.
     * Otherwise they will become {@link CraftingMenu#ITEM_RECIPE_VALID}.
     */
    public static final int[] STATUS_SLOTS = {34};

    /**
     * This integer is the output-slot in the inventory. This is not an array, because there cannot be multiple output-slots.
     */
    public static final int OUTPUT_SLOT = 16;

    /**
     * This item will be placed in every slot, which is on the {@link CraftingMenu#STATUS_SLOTS}-array,
     * as soon as a valid recipe has been applied to the crafting table.
     */
    public static final ItemStack ITEM_RECIPE_VALID = new ItemBuilder(Material.LIME_STAINED_GLASS_PANE).setName("&eStatus: &aVALID").build();

    /**
     * This item will be placed in every slot, which is on the {@link CraftingMenu#STATUS_SLOTS}-array,
     * if the recipe on the crafting table is not valid.
     */
    public static final ItemStack ITEM_RECIPE_INVALID = new ItemBuilder(Material.RED_STAINED_GLASS_PANE).setName("&eStatus: &cINVALID").build();

    /**
     * The Status, if a recipe is valid.
     * Can be VALID or INVALID.
     */
    private Status status;

    /**
     * This constructor creates a new CraftingInventory.
     */
    public CraftingMenu() {
        super(TITLE, Size.NINE_SIX, true);
        this.status = Status.INVALID;
    }

    /**
     * This method returns a crafting-menu from a native {@link Inventory}.
     *
     * @param inv The inventory, from which you want to get the wrapper-menu.
     * @return The Holder of the inventory given.
     */
    private static CraftingMenu fromNative(Inventory inv) {
        if (inv.getHolder() instanceof CraftingMenu) return (CraftingMenu) inv.getHolder();
        return null;
    }

    /**
     * This method handles all Player interaction with the inventory.
     *
     * @param e The Event, which you want to handle.
     */
    @Override
    public void handleMenuClick(InventoryClickEvent e) {
        if (e.getView().getTitle().equals(Utils.translateColorCodes(TITLE))) {
            if (e.getClickedInventory() != null) {
                ItemStack cursor = e.getCursor() != null ? e.getCursor().clone() : null;
                ItemStack result = e.getClickedInventory().getItem(OUTPUT_SLOT) != null ?
                        Objects.requireNonNull(e.getClickedInventory().getItem(OUTPUT_SLOT)).clone() : null;

                if (Objects.equals(e.getClickedInventory(), e.getView().getTopInventory())) {
                    int slot = e.getSlot();

                    Set<Integer> allowedSlots = new HashSet<>();
                    for (int i : CRAFTING_SLOTS) allowedSlots.add(i);
                    for (int i : UPGRADE_SLOTS) allowedSlots.add(i);
                    for (int i : BINDING_SLOTS) allowedSlots.add(i);
                    allowedSlots.add(OUTPUT_SLOT);


                    if (!allowedSlots.contains(slot)) {
                        e.setCancelled(true);
                        return;
                    }

                    if (slot == OUTPUT_SLOT) {
                        //Cancel placing items in output-slot
                        if (e.getClickedInventory().getItem(slot) == null && e.getCursor() != null) {
                            e.setCancelled(true);
                            return;
                        }

                        //Cancel over-stacking
                        if (e.getCursor() != null && e.getCursor().getType() != Material.AIR) {
                            if (e.getCursor().getAmount() >= e.getCursor().getType().getMaxStackSize()) {
                                e.setCancelled(true);
                                return;
                            }
                        }
                    }

                    new BukkitRunnable() {
                        @Override
                        public void run() {
                            CraftingMenu inv = CraftingMenu.fromNative(e.getClickedInventory());
                            if (inv != null) {
                                ItemStack[] matrix = inv.getMatrix();
                                ItemStack[] bindings = inv.getBindings();

                                if (slot == CraftingMenu.OUTPUT_SLOT) {
                                    for (int i = 0; i < matrix.length; i++) {
                                        if (matrix[i] != null) {
                                            if (matrix[i].getAmount() > 1) matrix[i].setAmount(matrix[i].getAmount() - 1);
                                            else matrix[i] = null;
                                        }
                                    }

                                    for (GrapesRecipe r : GrapesRecipe.getRecipes()) {
                                        if (r instanceof GrapesShapedRecipe) {
                                            if (r.check(matrix, bindings)) {
                                                List<Group2<GrapesRecipeChoice, Integer>> bindingsList = new ArrayList<>();
                                                for (Group2<GrapesRecipeChoice, Integer> group2 : ((GrapesShapedRecipe) r).getBindings())
                                                    bindingsList.add(new Group2<>(group2));

                                                for (Group2<GrapesRecipeChoice, Integer> group : bindingsList) {
                                                    for (int i = 0; i < bindings.length; i++) {
                                                        ItemStack current = bindings[i];
                                                        if (group.getY() > 0) {
                                                            if (current != null) {
                                                                if (group.getX().check(current)) {
                                                                    if (group.getY() > current.getAmount()) {
                                                                        int amount = current.getAmount();
                                                                        bindings[i] = null;
                                                                        group.setY(group.getY() - amount);
                                                                    } else {
                                                                        bindings[i].setAmount(bindings[i].getAmount() - group.getY());
                                                                        group.setY(0);
                                                                    }
                                                                }
                                                            }
                                                        } else break;
                                                    }
                                                }
                                                break;
                                            }
                                        }
                                    }

                                    // System.arraycopy(bindingsNew, 0, bindings, 0, bindingsNew.length);

                                    inv.setMatrix(matrix);
                                    inv.setBindings(bindings);

                                    if (cursor != null) {
                                        if (Objects.requireNonNull(cursor).isSimilar(result)) {
                                            inv.getResult().setAmount(inv.getResult().getAmount() + e.getCursor().getAmount());
                                            e.getWhoClicked().setItemOnCursor(inv.getResult());
                                            inv.setResult(null);
                                        }
                                    }
                                }

                                Status status = Status.INVALID;

                                for (GrapesRecipe r : GrapesShapedRecipe.getRecipes()) {
                                    if (r.check(matrix, bindings)) {
                                        inv.setStatus(Status.VALID);
                                        inv.setResult(r.getResult().build());
                                        status = Status.VALID;
                                        break;
                                    }
                                }

                                if (status == Status.INVALID) inv.setResult(null);
                                inv.setStatus(status);
                            }
                        }
                    }.runTaskLater(Grapes.getGrapes(), 0);
                }
            }
        }
    }

    /**
     * This method sets all the default-items in the inventory.
     */
    @Override
    public void setupMenu() {
        for (int i : BINDING_SLOTS) super.getInventory().setItem(i, null);
        for (int i : CRAFTING_SLOTS) super.getInventory().setItem(i, null);
        for (int i : UPGRADE_SLOTS) super.getInventory().setItem(i, null);
        for (int i : STATUS_SLOTS) super.getInventory().setItem(i, ITEM_RECIPE_INVALID);
        super.getInventory().setItem(OUTPUT_SLOT, null);
    }

    /**
     * This method gets called everytime the inventory gets closed.
     *
     * @param e The Event with all information about the closing.
     */
    @Override
    public void handleMenuClose(InventoryCloseEvent e) {
        if (e.getView().getTitle().equals(Utils.translateColorCodes(CraftingMenu.TITLE))) {
            this.dropItems((Player) e.getPlayer(), e.getInventory(), CraftingMenu.CRAFTING_SLOTS);
            this.dropItems((Player) e.getPlayer(), e.getInventory(), CraftingMenu.BINDING_SLOTS);
            this.dropItems((Player) e.getPlayer(), e.getInventory(), CraftingMenu.UPGRADE_SLOTS);
        }
    }

    /**
     * This method returns the 3x3 area of items in the crafting grid.
     * index 0 is the top left item, index 1 is the top middle item, etc.
     * If an index in the array is null, the slot in the inventory is empty.
     *
     * @return An array of items, which are in the crafting grid.
     */
    public ItemStack[] getMatrix() {
        return this.getItems(CRAFTING_SLOTS);
    }

    /**
     * This method overwrites the 3x3 crafting grid.
     * If a index in the matrix is null, the slot will be empty.
     *
     * @param matrix The new content of the crafting grid.
     */
    public void setMatrix(ItemStack[] matrix) {
        if (matrix.length >= CRAFTING_SLOTS.length)
            for (int i = 0; i < CRAFTING_SLOTS.length; i++) super.getInventory().setItem(CRAFTING_SLOTS[i], matrix[i]);
    }

    /**
     * This method returns an array of items in the binding slots.
     * If an index in the array is null, the slot in the inventory is empty.
     *
     * @return An array of the items in the binding slots.
     */
    public ItemStack[] getBindings() {
        return this.getItems(BINDING_SLOTS);
    }

    /**
     * This method sets the content of the binding slots.
     * If an index of the array is null, the slot of that index will be empty.
     *
     * @param bindings The new content for the binding slots.
     */
    public void setBindings(ItemStack[] bindings) {
        try {
            for (int i = 0; i < BINDING_SLOTS.length; i++) super.getInventory().setItem(BINDING_SLOTS[i], bindings[i]);
        } catch (Exception ignored) {
        }
    }

    /**
     * This method returns the content of the upgrade-slots in form of an ItemStack-Array.
     * If an index of the array is null, the slot is empty.
     *
     * @return An array of items, filled with the contents of the upgrade-slots.
     */
    public ItemStack[] getUpgrades() {
        return this.getItems(UPGRADE_SLOTS);
    }

    /**
     * This method sets the content of the upgrade slots.
     * If an index of the array is null, the slot of that index will be empty.
     *
     * @param upgrades The new content for the upgrade slots.
     */
    public void setUpgrades(ItemStack[] upgrades) {
        if (upgrades.length >= UPGRADE_SLOTS.length)
            for (int i = 0; i < UPGRADE_SLOTS.length; i++) super.getInventory().setItem(UPGRADE_SLOTS[i], upgrades[i]);
    }

    /**
     * This utility-method returns the items of the slots given in the parameters.
     *
     * @param slots The slots, from which you want to get the items.
     * @return An array of the items.
     */
    private ItemStack[] getItems(int... slots) {
        ItemStack[] items = new ItemStack[slots.length];
        for (int i = 0; i < items.length; i++) {
            if (super.getInventory().getItem(slots[i]) != null) items[i] = super.getInventory().getItem(slots[i]).clone();
            else items[i] = null;
        }
        return items;
    }

    /**
     * This method returns the content of the result slot.
     *
     * @return The content of the output slot.
     */
    public ItemStack getResult() {
        return super.getInventory().getItem(OUTPUT_SLOT);
    }

    /**
     * This method sets the content of the output-slot.
     *
     * @param itemStack The new output-item.
     */
    public void setResult(ItemStack itemStack) {
        super.getInventory().setItem(OUTPUT_SLOT, itemStack);
    }

    /**
     * This method returns VALID, if the recipe is valid and INVALID if the recipe is invalid.
     *
     * @return An object of the enum Status.
     */
    public Status getStatus() {
        return status;
    }

    /**
     * Sets the status of the CraftingInventory.
     * If the status is set to invalid, the output-slot will be cleared.
     *
     * @param status The new status of the current inventory.
     */
    public void setStatus(Status status) {
        this.status = status;
        for (int i : STATUS_SLOTS) super.getInventory().setItem(i, status.getStatusIcon());
    }

    /**
     * This method drops the items in all named slots at the players location.
     *
     * @param player The player, who is supposed to receive the items.
     * @param inv    The Inventory, which contains the items.
     * @param slots  The slots, which contain the items.
     */
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

    /**
     * The status of a recipe.
     * Can be valid or invalid.
     *
     * @author Trqhxrd
     */
    public enum Status {

        /**
         * This status means, that the recipe in the crafting grid is valid.
         */
        VALID(ITEM_RECIPE_VALID),

        /**
         * This status means, that the recipe in the crafting grid is invalid.
         */
        INVALID(ITEM_RECIPE_INVALID);

        /**
         * This field contains the ItemStack, for the current recipe status.
         */
        private final ItemStack statusIcon;

        /**
         * A basic constructor for setting the status-item.
         *
         * @param statusIcon The icon, which will be displayed in the {@link CraftingMenu#STATUS_SLOTS}, if the current status is correct.
         */
        Status(ItemStack statusIcon) {
            this.statusIcon = statusIcon;
        }

        /**
         * This method returns the status-icon of the current status.
         *
         * @return The status-icon for the current recipe-status.
         */
        public ItemStack getStatusIcon() {
            return statusIcon;
        }
    }
}
