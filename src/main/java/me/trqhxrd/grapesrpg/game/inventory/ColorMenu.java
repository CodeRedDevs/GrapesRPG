package me.trqhxrd.grapesrpg.game.inventory;

import me.trqhxrd.color.Colors;
import me.trqhxrd.grapesrpg.Grapes;
import me.trqhxrd.grapesrpg.api.utils.items.ItemBuilder;
import me.trqhxrd.menus.Menu;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.entity.Item;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

public class ColorMenu extends Menu {

    public static final int[] ARMOR_SLOTS = new int[]{10, 19, 28, 37};
    public static final int[] RED_SLOTS = new int[]{14, 23, 32};
    public static final int[] GREEN_SLOTS = new int[]{15, 24, 33};
    public static final int[] BLUE_SLOTS = new int[]{16, 25, 34};
    public static final int TEXT_SLOT = 21;
    public static final ItemBuilder RED_DEFAULT = new ItemBuilder(Material.RED_DYE).setName("&eAMOUNT: &c0");
    public static final ItemBuilder GREEN_DEFAULT = new ItemBuilder(Material.LIME_DYE).setName("&eAMOUNT: &a0");
    public static final ItemBuilder BLUE_DEFAULT = new ItemBuilder(Material.BLUE_DYE).setName("&eAMOUNT: &90");
    private final int[] rgb;

    public ColorMenu() {
        super(Colors.translateColors("&cChoose &ayour &9color!"),
                "menu_color",
                6 * 9,
                new ItemBuilder(Material.GRAY_STAINED_GLASS_PANE).setName(" ").build(),
                false);
        rgb = new int[]{
                ThreadLocalRandom.current().nextInt(0, 256),
                ThreadLocalRandom.current().nextInt(0, 256),
                ThreadLocalRandom.current().nextInt(0, 256)};
        this.setupMenu(this.getContent());
    }

    /**
     * This method gets called everytime the inventory gets closed.
     *
     * @param event The Event with all information about the closing.
     */
    @Override
    public void handleMenuClose(InventoryCloseEvent event) {
        for (int slot : ARMOR_SLOTS) {
            ItemStack item = super.getInventory().getItem(slot);
            if (item != null) {
                Item i = event.getPlayer().getWorld().dropItem(event.getPlayer().getLocation(), item);
                i.setPickupDelay(0);
            }
        }
    }

    /**
     * This method handles all Player interaction with the inventory.
     *
     * @param e The Event, which you want to handle.
     */
    @Override
    public void handleMenuClick(InventoryClickEvent e) {
        if (e.getClickedInventory() != null) {
            if (e.getClickedInventory().equals(this.getInventory())) {
                int slot = e.getSlot();

                boolean b = true;
                for (int armorSlot : ARMOR_SLOTS) {
                    if (armorSlot == slot) {
                        b = false;
                        break;
                    }
                }
                e.setCancelled(b);

                int editValue = e.isShiftClick() || e.isRightClick() ? 10 : 1;

                if (slot == RED_SLOTS[0]) rgb[0] += editValue;
                else if (slot == GREEN_SLOTS[0]) rgb[1] += editValue;
                else if (slot == BLUE_SLOTS[0]) rgb[2] += editValue;
                else if (slot == RED_SLOTS[2]) rgb[0] -= editValue;
                else if (slot == GREEN_SLOTS[2]) rgb[1] -= editValue;
                else if (slot == BLUE_SLOTS[2]) rgb[2] -= editValue;


                for (int i = 0; i < rgb.length; i++) {
                    if (rgb[i] > 255) rgb[i] = 255;
                    if (rgb[i] < 0) rgb[i] = 0;
                }

                new BukkitRunnable() {
                    /**
                     * When an object implementing interface <code>Runnable</code> is used
                     * to create a thread, starting the thread causes the object's
                     * <code>run</code> method to be called in that separately executing
                     * thread.
                     * <p>
                     * The general contract of the method <code>run</code> is that it may
                     * take any action whatsoever.
                     *
                     * @see Thread#run()
                     */
                    @Override
                    public void run() {
                        getInventory().setItem(RED_SLOTS[1], RED_DEFAULT.setName("&eAMOUNT: &c" + rgb[0]).build());
                        getInventory().setItem(GREEN_SLOTS[1], GREEN_DEFAULT.setName("&eAMOUNT: &a" + rgb[1]).build());
                        getInventory().setItem(BLUE_SLOTS[1], BLUE_DEFAULT.setName("&eAMOUNT: &9" + rgb[2]).build());

                        for (int armorSlot : ARMOR_SLOTS) {
                            ItemStack item = getInventory().getItem(armorSlot);
                            if (item != null) {
                                ItemMeta meta = item.getItemMeta();
                                if (meta instanceof LeatherArmorMeta) {
                                    ((LeatherArmorMeta) meta).setColor(Color.fromRGB(rgb[0], rgb[1], rgb[2]));
                                    item.setItemMeta(meta);
                                }
                                getInventory().setItem(armorSlot, item);
                            }
                        }
                        getInventory().setItem(TEXT_SLOT, new ItemBuilder(Material.STONE).setName("&#" + toHex(rgb) + toHex(rgb)).build());
                    }
                }.runTaskLater(Grapes.getGrapes(), 0);
            }
        }
    }

    /**
     * This method sets all the default-items in the inventory.
     *
     * @param content A map of the content of this inventory. this can be edited or you can use {@code this.getContent()}. The result is the same.
     */
    @Override
    public void setupMenu(Map<Integer, ItemStack> content) {
        for (int i : ARMOR_SLOTS) content.put(i, null);
        content.put(RED_SLOTS[0], new ItemBuilder(Material.REDSTONE_TORCH).setName("&eIncrease: &cRED").build());
        content.put(RED_SLOTS[1], new ItemBuilder(Material.RED_DYE).setName("&c" + rgb[0]).build());
        content.put(RED_SLOTS[2], new ItemBuilder(Material.LEVER).setName("&eDecrease: &cRED").build());
        content.put(GREEN_SLOTS[0], new ItemBuilder(Material.REDSTONE_TORCH).setName("&eIncrease: &aGREEN").build());
        content.put(GREEN_SLOTS[1], new ItemBuilder(Material.LIME_DYE).setName("&a" + rgb[1]).build());
        content.put(GREEN_SLOTS[2], new ItemBuilder(Material.LEVER).setName("&eDecrease: &aGREEN").build());
        content.put(BLUE_SLOTS[0], new ItemBuilder(Material.REDSTONE_TORCH).setName("&eIncrease: &9Blue").build());
        content.put(BLUE_SLOTS[1], new ItemBuilder(Material.BLUE_DYE).setName("&9" + rgb[2]).build());
        content.put(BLUE_SLOTS[2], new ItemBuilder(Material.LEVER).setName("&eDecrease: &9Blue").build());
        content.put(TEXT_SLOT, new ItemBuilder(Material.STONE).setName("&#" + this.toHex(rgb) + "Example").build());
    }

    private String toHex(int[] rgb) {
        StringBuilder hex = new StringBuilder();
        for (int value : rgb) {
            String s = Integer.toHexString(value);
            if (s.length() == 1) s = "0" + s;
            hex.append(s);
        }
        return hex.toString();
    }
}
