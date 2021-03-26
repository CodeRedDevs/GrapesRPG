package me.trqhxrd.grapesrpg.game.inventory;

import me.trqhxrd.grapesrpg.Grapes;
import me.trqhxrd.grapesrpg.api.GrapesPlayer;
import me.trqhxrd.grapesrpg.api.attribute.Owneable;
import me.trqhxrd.grapesrpg.api.attribute.Savable;
import me.trqhxrd.grapesrpg.api.utils.Utils;
import me.trqhxrd.menus.Menu;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.Map;

/**
 * This Menu represents a players enderchest.
 * If you want to get the enderchest of a specific player please use the {@link GrapesPlayer#getEnderChest()}.
 *
 * @author Trqhxrd
 */
public class EnderChestMenu extends Menu implements Owneable<GrapesPlayer>, Savable {

    /**
     * This is currently unused.
     * These are the different sizes of the inventory.
     */
    public static final int[] sizes = new int[]{1, 2, 3, 4, 5, 6};
    /**
     * This variable contains the owner of this inventory.
     */
    private final GrapesPlayer owner;

    /**
     * This constructor creates a new enderchest-menu for a specific player.
     *
     * @param owner The owner of this enderchest.
     */
    public EnderChestMenu(GrapesPlayer owner) {
        super(Utils.translateColorCodes("&d" + owner.getName() + "'s &5Enderchest"),
                "enderchest_" + owner.getUniqueId().toString(),
                (owner.getFile().contains("enderchest.size") ? owner.getFile().getInt("enderchest.size") * 9 : 9),
                null,
                false);
        this.owner = owner;

        this.setupMenu(this.getContent());
    }

    /**
     * This method will setup the inventory.
     *
     * @param map This map can be edited to set items in certain slots. The key is the slot of the item and the value is the item itself.
     */
    @Override
    public void setupMenu(Map<Integer, ItemStack> map) {
        ConfigurationSection section = owner.getFile().getConfigurationSection("enderchest.content");
        if (section != null) section.getKeys(false).forEach(key -> map.put(Integer.parseInt(key), Grapes.GSON.fromJson(section.getString(key), ItemStack.class)));
    }

    /**
     * This method will be executed everytime a player clicks an item in the inventory.
     *
     * @param e The InventoryClickEvent, which got called by the click. You can get all information, that you require like the inventory, the player and the slot, that got clicked.
     */
    @Override
    public void handleMenuClick(InventoryClickEvent e) {
    }

    /**
     * This method gets called everytime the menu gets closed. You can use this method for saving the inventory or dropping its contents.
     *
     * @param e An InventoryCloseEvent, which stores the player, who closed the inventory, the inventory itself and much more.
     */
    @Override
    public void handleMenuClose(InventoryCloseEvent e) {
        Inventory inv = e.getView().getTopInventory();
        for (int i = 0; i < inv.getSize(); i++) {
            int start = this.getPageRange()[0];
            ItemStack item = inv.getItem(i);
            if (item != null) this.getContent().put(start + i, item);
            else this.getContent().remove(start + i);
        }

        this.getOwner().getFile().set("enderchest.size", this.getSize() / 9);
        this.getContent().forEach((slot, item) -> this.getOwner().getFile().set("enderchest.content." + slot, Grapes.GSON_NO_PRETTY_PRINT.toJson(item)));
        this.getOwner().getFile().save();
    }


    /**
     * This method returns the owner of this object.
     *
     * @return The owner of this object.
     */
    @Override
    public GrapesPlayer getOwner() {
        return owner;
    }

    /**
     * This method will save the current state of this class in some way.
     *
     * @param flush If flush is true, the changes will automatically be written to a file.
     */
    @Override
    public void save(boolean flush) {
        this.owner.getFile().set("enderchest.size", this.getSize() / 9);
        this.getContent().forEach((slot, item) -> this.getOwner().getFile().set("enderchest.content." + slot, Grapes.GSON_NO_PRETTY_PRINT.toJson(item)));
        if (flush) this.getOwner().getFile().save();
    }

    /**
     * This method just runs {@code this.save(true);}, which will save the state of this class and automatically write it to the file.
     * This method has to be overwritten tho.
     */
    @Override
    public void save() {
        this.save(true);
    }
}
