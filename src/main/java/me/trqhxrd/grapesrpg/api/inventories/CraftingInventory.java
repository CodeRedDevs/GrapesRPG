package me.trqhxrd.grapesrpg.api.inventories;

import me.trqhxrd.grapesrpg.api.utils.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

/**
 * This Class represents a crafting inventory.
 * If you wish to open a new Crafting Grid, use <code>Player.openInventory(new CraftingInventory())</code>.
 * If you wish to get a CraftingInventory, with the contents of an already existing inventory, use {@link CraftingInventory#fromNative(Inventory)}.
 *
 * @author Trqhxrd
 */
public class CraftingInventory {

    /**
     * This constant contains the title of the CraftingInventory.
     * It is used for checking recipes in the {@link me.trqhxrd.grapesrpg.event.InventoryClickListener}.
     */
    public static final String TITLE = "§bCrafting";

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
     * If the recipe is invalid, the items in these slots will become {@link CraftingInventory#ITEM_RECIPE_INVALID}.
     * Otherwise they will become {@link CraftingInventory#ITEM_RECIPE_VALID}.
     */
    public static final int[] STATUS_SLOTS = {34};

    /**
     * This integer is the output-slot in the inventory. This is not an array, because there cannot be multiple output-slots.
     */
    public static final int OUTPUT_SLOT = 16;

    /**
     * This item will be placed in every slot, which is on the {@link CraftingInventory#STATUS_SLOTS}-array,
     * as soon as a valid recipe has been applied to the crafting table.
     */
    public static final ItemStack ITEM_RECIPE_VALID = new ItemBuilder(Material.LIME_STAINED_GLASS_PANE).setName("&eStatus: &aVALID").build();

    /**
     * This item will be placed in every slot, which is on the {@link CraftingInventory#STATUS_SLOTS}-array,
     * if the recipe on the crafting table is not valid.
     */
    public static final ItemStack ITEM_RECIPE_INVALID = new ItemBuilder(Material.RED_STAINED_GLASS_PANE).setName("&eStatus: &cINVALID").build();

    /**
     * The native bukkit inventory.
     */
    private final Inventory inventory;

    /**
     * The Status, if a recipe is valid.
     * Can be VALID or INVALID.
     */
    private Status status;

    /**
     * This constructor creates an empty crafting inventory.
     */
    public CraftingInventory() {
        this.inventory = Bukkit.createInventory(null, 6 * 9, TITLE);
        this.status = Status.INVALID;

        for (int i = 0; i < inventory.getSize(); i++) inventory.setItem(i, new ItemBuilder(Material.GRAY_STAINED_GLASS_PANE).setName("").build());
        for (int i : BINDING_SLOTS) inventory.setItem(i, null);
        for (int i : CRAFTING_SLOTS) inventory.setItem(i, null);
        for (int i : UPGRADE_SLOTS) inventory.setItem(i, null);
        for (int i : STATUS_SLOTS) inventory.setItem(i, ITEM_RECIPE_INVALID);
        inventory.setItem(OUTPUT_SLOT, null);
    }

    /**
     * This constructor will create a crafting inventory, with the contents of the inventory in the first parameter.
     * If you wish to call this constructor from outside, use {@link CraftingInventory#fromNative(Inventory)}.
     *
     * @param inv The inventory with the contents, which you want to have in your crafting inventory.
     */
    private CraftingInventory(Inventory inv) {
        this.inventory = inv;
        this.status = ITEM_RECIPE_VALID.isSimilar(inv.getItem(STATUS_SLOTS[0])) ? Status.VALID : Status.INVALID;
    }

    /**
     * This method returns a crafting inventory with the contents of the inventory given in the parameter.
     *
     * @param inv The inventory with the contents, which you want to use.
     * @return A CraftingInventory with the contents of the inventory in the parameters.
     */
    public static CraftingInventory fromNative(Inventory inv) {
        return new CraftingInventory(inv);
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
            for (int i = 0; i < CRAFTING_SLOTS.length; i++) this.inventory.setItem(CRAFTING_SLOTS[i], matrix[i]);
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
            for (int i = 0; i < BINDING_SLOTS.length; i++) this.inventory.setItem(BINDING_SLOTS[i], bindings[i]);
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
            for (int i = 0; i < UPGRADE_SLOTS.length; i++) this.inventory.setItem(UPGRADE_SLOTS[i], upgrades[i]);
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
            if (this.inventory.getItem(slots[i]) != null) items[i] = this.inventory.getItem(slots[i]).clone();
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
        return inventory.getItem(OUTPUT_SLOT);
    }

    /**
     * This method sets the content of the output-slot.
     *
     * @param itemStack The new output-item.
     */
    public void setResult(ItemStack itemStack) {
        this.inventory.setItem(OUTPUT_SLOT, itemStack);
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
        for (int i : STATUS_SLOTS) this.inventory.setItem(i, status.getStatusIcon());
    }

    /**
     * This method returns the native bukkit inventory.
     * The inventory can be edited if you wish to perform actions, which cannot be done with the other methods in this class.
     *
     * @return The native bukkit inventory.
     */
    public Inventory getInventory() {
        return inventory;
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
         * @param statusIcon The icon, which will be displayed in the {@link CraftingInventory#STATUS_SLOTS}, if the current status is correct.
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
