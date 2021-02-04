package me.trqhxrd.grapesrpg.api.inventories;

import me.trqhxrd.grapesrpg.api.utils.ItemBuilder;
import me.trqhxrd.grapesrpg.api.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.HumanEntity;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;

/**
 * This Class represents an inventory menu.
 * It can be opened using {@link Menu#openInventory(HumanEntity)}.
 *
 * @author Trqhxrd
 */
public abstract class Menu implements InventoryHolder {

    /**
     * This is just a constant, with some standardized filler glass for slot, which aren't used.
     */
    public static final ItemStack FILLER_GLASS = new ItemBuilder(Material.GRAY_STAINED_GLASS_PANE).setName(" ").build();

    /**
     * This field contains the actual inventory.
     */
    private final Inventory inventory;

    /**
     * This String is the title of the inventory.
     */
    private final String title;

    /**
     * This Field contains the size of the inventory.
     * It can only be a multiple of 9 and must be greater/equals 9 and smaller/equals 56.
     */
    private final int size;

    /**
     * This constructor creates a new menu, with self-defined title, size and if you wish even with filler glass.
     *
     * @param title               The title of the inventory. ColorCodes can be used.
     * @param size                The size of the inventory. Must be one of these numbers: {9, 18, 27, 36, 45, 54}.
     * @param setup               If true, the constructor will run the setup method.
     * @param fillWithFillerGlass If set to true, the whole inventory will be filled with filler glass and you can overwrite the slot, which you want to use.
     */
    public Menu(String title, Size size, boolean setup, boolean fillWithFillerGlass) {
        this.title = title;
        this.size = size.getSlots();
        this.inventory = Bukkit.createInventory(this, this.size, Utils.translateColorCodes(title));
        if (fillWithFillerGlass) for (int i = 0; i < this.size; i++) inventory.setItem(i, FILLER_GLASS);
        if (setup) this.setupMenu();
    }

    /**
     * This constructor creates a new menu, with self-defined title, size and if you wish even with filler glass.
     *
     * @param title               The title of the inventory. ColorCodes can be used.
     * @param size                The size of the inventory. Must be one of these numbers: {9, 18, 27, 36, 45, 54}.
     * @param fillWithFillerGlass If set to true, the whole inventory will be filled with filler glass and you can overwrite the slot, which you want to use.
     */
    public Menu(String title, Size size, boolean fillWithFillerGlass) {
        this(title, size, true, fillWithFillerGlass);
    }

    /**
     * This constructor creates a new inventory with the title and size given.
     * The filler glass won't be used.
     *
     * @param title The title of the inventory. ColorCodes can be used.
     * @param size  The size of the inventory. Must be one of these numbers: {9, 18, 27, 36, 45, 54}.
     */
    public Menu(String title, Size size) {
        this(title, size, true, false);
    }

    /**
     * This method gets called everytime the inventory gets closed.
     *
     * @param event The Event with all information about the closing.
     */
    public abstract void handleMenuClose(InventoryCloseEvent event);

    /**
     * This method handles all Player interaction with the inventory.
     *
     * @param event The Event, which you want to handle.
     */
    public abstract void handleMenuClick(InventoryClickEvent event);

    /**
     * This method sets all the default-items in the inventory.
     */
    public abstract void setupMenu();

    /**
     * Get the object's inventory.
     *
     * @return The inventory.
     */
    @SuppressWarnings("NullableProblems")
    @Override
    public Inventory getInventory() {
        return inventory;
    }

    /**
     * This method opens the inventory for the player specified in the method arguments.
     *
     * @param player The HumanEntity which should open the inventory.
     */
    public void openInventory(HumanEntity player) {
        player.openInventory(this.inventory);
    }

    /**
     * Getter for the inventories title.
     *
     * @return The title of the inventory.
     */
    public String getTitle() {
        return title;
    }

    /**
     * Getter for the inventories size.
     *
     * @return The inventories size.
     */
    public int getSize() {
        return size;
    }

    /**
     * This enum contains all sizes, you can use for creating an inventory.
     *
     * @author Trqhxrd
     */
    public enum Size {
        /**
         * This state is for creating an inventory with one line.
         */
        NINE_ONE(9),
        /**
         * This state is for creating an inventory with two lines.
         */
        NINE_TWO(18),
        /**
         * This state is for creating an inventory with three lines.
         */
        NINE_THREE(27),
        /**
         * This state is for creating an inventory with four lines.
         */
        NINE_FOUR(36),
        /**
         * This state is for creating an inventory with five lines.
         */
        NINE_FIVE(45),
        /**
         * This state is for creating an inventory with six lines.
         */
        NINE_SIX(54);

        /**
         * The actual amount of slots the inventory has.
         */
        private final int slots;

        /**
         * This constructor creates a new state with the set amount of slots.
         *
         * @param slots The amount of slots, which the inventory should have.
         */
        Size(int slots) {
            this.slots = slots;
        }

        /**
         * This method returns the actual amount of slots, the inventory has.
         *
         * @return The actual amount of the inventory.
         */
        public int getSlots() {
            return slots;
        }
    }
}
