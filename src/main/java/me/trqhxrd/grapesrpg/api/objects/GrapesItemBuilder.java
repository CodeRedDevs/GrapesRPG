package me.trqhxrd.grapesrpg.api.objects;

import com.jojodmo.safeNBT.api.SafeNBT;
import me.trqhxrd.grapesrpg.api.utils.EnchantmentNames;
import me.trqhxrd.grapesrpg.api.utils.group.Group2;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_16_R3.inventory.CraftItemStack;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * This Class can create a new {@link GrapesItem}.
 * You can set every value using the methods in this class.
 * Once you set all values just run {@link GrapesItemBuilder#build()} and it will return the finished ItemStack.
 *
 * @author Trqhxrd
 */
public class GrapesItemBuilder {

    /**
     * The legacy-item, for which the NBT-Tag will be set.
     */
    private ItemStack itemStack;
    private List<Group2<Enchantment, Integer>> enchantments;

    /**
     * Creates a new Builder with the itemStack as its base.
     * Note: It clones the itemStack. You still need to overwrite the old ItemStack if you want to edit it.
     *
     * @param itemStack The item for which you want to start a Builder.
     */
    public GrapesItemBuilder(ItemStack itemStack) {
        this.itemStack = itemStack.clone();
        this.enchantments = GrapesItem.getEnchantments(itemStack);
    }

    /**
     * Creates a new Builder with a Black ItemStack as it's base.
     * The only value, which is set, is the {@link Material}.
     *
     * @param material The material of the item, for which you want to create a Builder.
     */
    public GrapesItemBuilder(Material material) {
        this.itemStack = new ItemStack(material);
        this.enchantments = new ArrayList<>();
    }

    /**
     * Sets the Grapes-Id of the Item.
     *
     * @param id The new Id, you want to set.
     * @return The Builder. Used for chaining commands.
     */
    public GrapesItemBuilder setID(int id) {
        return setNBTValue("skyblock.id", id);
    }

    /**
     * Sets the Name of the Item.
     *
     * @param name The new Name, you want to set.
     * @return The Builder. Used for chaining commands.
     */
    public GrapesItemBuilder setName(String name) {
        setNBTValue("grapes.name", ChatColor.translateAlternateColorCodes('&', name));
        return this;
    }

    /**
     * Sets theLore of the Item.
     *
     * @param lore The new lore, you want to set.
     * @return The Builder. Used for chaining commands.
     */
    public GrapesItemBuilder setLore(List<String> lore) {
        for (int i = 0; i < lore.size(); i++) setNBTValue("grapes.lore." + i, "§7" + lore.get(i));
        return this;
    }

    /**
     * Sets the Lore of the Item.
     *
     * @param lines The new lore, you want to set.
     * @return The Builder. Used for chaining commands.
     */
    public GrapesItemBuilder setLore(String... lines) {
        List<String> list = Arrays.asList(lines);
        return setLore(list);
    }

    /**
     * Sets the current and max durability of an Item.
     *
     * @param durability The durability. Value X is the current durability and Value Y is the maximum durability possible.
     * @return The Builder. Used for chaining commands.
     */
    public GrapesItemBuilder setDurability(Group2<Integer, Integer> durability) {
        return this.setDurability(durability.getX(), durability.getY());
    }

    /**
     * Sets the current and max durability of an Item.
     *
     * @param current The current durability of this item.
     * @param max     The maximum durability of this item.
     * @return The Builder. Used for chaining commands.
     */
    public GrapesItemBuilder setDurability(int current, int max) {
        this.setNBTValue("grapes.durability.current", current);
        this.setNBTValue("grapes.durability.max", max);
        return this;
    }

    /**
     * Adds an Enchantment to the list of enchantments, the item will get once it is updated.
     *
     * @param enchantment The Enchantment, which you want to add.
     * @param level       The level of the Enchantment, which you want to add.
     * @return The Builder. Used for chaining commands.
     */
    public GrapesItemBuilder addEnchantment(Enchantment enchantment, int level) {
        enchantments.add(new Group2<>(enchantment, level));
        return this;
    }

    /**
     * This method completely replaces all enchantments, which are already there.
     *
     * @param enchantments The new List of enchantments.
     * @return The Builder. Used for chaining commands.
     */
    public GrapesItemBuilder setEnchantments(List<Group2<Enchantment, Integer>> enchantments) {
        this.enchantments = enchantments;
        return this;
    }

    /**
     * Clears the list of the enchantments.
     *
     * @return The Builder. Used for chaining commands.
     */
    public GrapesItemBuilder clearEnchants() {
        this.enchantments = new ArrayList<>();
        return this;
    }

    /**
     * This method can set an NBT-Value, which contains an Integer.
     *
     * @param path  The Path to the NBT-Tag, you want to set. ({@literal "test.xyz" -> {test:{xyz:[value]}}}).
     * @param value The Value, you want to set.
     * @return The Builder. Used for chaining commands.
     */
    public GrapesItemBuilder setNBTValue(String path, int value) {
        net.minecraft.server.v1_16_R3.ItemStack nmsItem = CraftItemStack.asNMSCopy(itemStack);
        nmsItem.setTag(nmsItem.getOrCreateTag());
        itemStack = CraftItemStack.asBukkitCopy(nmsItem);

        path = "null." + path;
        String[] pathArray = path.split("\\.");
        pathArray[0] = null;
        SafeNBT[] nbts = new SafeNBT[pathArray.length - 1];
        for (int i = 0; i < pathArray.length; i++) {
            String part = pathArray[i];
            if (i == 0) {
                nbts[0] = SafeNBT.get(itemStack);
            } else {
                if (i == pathArray.length - 1) {
                    nbts[i - 1].setInt(part, value);
                } else {
                    try {
                        nbts[i] = nbts[i - 1].getCompoundThrows(pathArray[i]);
                    } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        for (int i = nbts.length; i-- > 0; ) {
            if (i - 1 < nbts.length && i - 1 >= 0) {
                nbts[i - 1].set(pathArray[i], nbts[i]);
            }
        }
        itemStack = nbts[0].apply(itemStack);
        return this;
    }

    /**
     * This method can set an NBT-Value, which contains a Boolean.
     *
     * @param path  The Path to the NBT-Tag, you want to set. ({@literal "test.xyz" -> {test:{xyz:[value]}}}).
     * @param value The Value, you want to set.
     * @return The Builder. Used for chaining commands.
     */
    public GrapesItemBuilder setNBTValue(String path, boolean value) {
        path = "null." + path;
        String[] pathArray = path.split("\\.");
        pathArray[0] = null;
        SafeNBT[] nbts = new SafeNBT[pathArray.length - 1];
        for (int i = 0; i < pathArray.length; i++) {
            String part = pathArray[i];
            if (i == 0) {
                nbts[i] = SafeNBT.get(itemStack);
            } else {
                if (i == pathArray.length - 1) {
                    nbts[i - 1].setString(part, (value) ? "true" : "false");
                } else {
                    try {
                        nbts[i] = nbts[i - 1].getCompoundThrows(pathArray[i]);
                    } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        for (int i = nbts.length; i-- > 0; ) {
            if (i - 1 < nbts.length && i - 1 >= 0) {
                nbts[i - 1].set(pathArray[i], nbts[i]);
            }
        }
        itemStack = nbts[0].apply(itemStack);
        return this;
    }

    /**
     * This method can set an NBT-Value, which contains a String.
     *
     * @param path  The Path to the NBT-Tag, you want to set. ({@literal "test.xyz" -> {test:{xyz:[value]}}}).
     * @param value The Value, you want to set.
     * @return The Builder. Used for chaining commands.
     */
    public GrapesItemBuilder setNBTValue(String path, String value) {
        path = "null." + path;
        String[] pathArray = path.split("\\.");
        pathArray[0] = null;
        SafeNBT[] nbts = new SafeNBT[pathArray.length - 1];
        for (int i = 0; i < pathArray.length; i++) {
            String part = pathArray[i];
            if (i == 0) {
                nbts[i] = SafeNBT.get(itemStack);
            } else {
                if (i == pathArray.length - 1) {
                    nbts[i - 1].setString(part, (value == null) ? "" : value);
                } else {
                    try {
                        nbts[i] = nbts[i - 1].getCompoundThrows(pathArray[i]);
                    } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        for (int i = nbts.length; i-- > 0; ) {
            if (i - 1 < nbts.length && i - 1 >= 0) {
                nbts[i - 1].set(pathArray[i], nbts[i]);
            }
        }
        itemStack = nbts[0].apply(itemStack);
        return this;
    }

    /**
     * This method displays the ItemStacks stats in his lore.
     *
     * @return The Builder. Used for chaining commands.
     */
    public GrapesItemBuilder update() {
        ItemMeta meta = itemStack.getItemMeta();
        if (meta != null) {
            // HIDE ALL VALUES
            for (ItemFlag flag : ItemFlag.values()) {
                meta.addItemFlags(flag);
            }

            //SET DISPLAY NAME
            meta.setDisplayName(GrapesItem.getNBTValueString(itemStack, "grapes.name"));

            //SET LORE
            List<String> list = new ArrayList<>();

            //REAL LORE
            list.add("");
            String line;
            int i = 0;
            while (!(line = GrapesItem.getNBTValueString(itemStack, "grapes.lore." + i++)).equals("")) list.add(line);

            //ENCHANTS
            list.add("");
            for (Group2<Enchantment, Integer> entry : enchantments) {
                Enchantment ench = entry.getX();
                int level = entry.getY();
                meta.addEnchant(ench, level, true);
                list.add("&7" + EnchantmentNames.getName(ench) + " " + EnchantmentNames.getLevel(level));
            }

            //DURABILITY
            list.add("");
            Group2<Integer, Integer> durabilityValues = GrapesItem.getDurability(itemStack);
            double percent = (((double) durabilityValues.getX()) / ((double) durabilityValues.getY())) * 100;
            char color = 'a';
            if (percent <= 50) color = 'e';
            if (percent <= 25) color = 'c';
            if (percent <= 0) color = '4';
            String durability;
            if (percent > 0)
                durability = "&" + color + "Durability: " + durabilityValues.getX() + "/" + durabilityValues.getY();
            else durability = "&" + color + "BROKEN";
            list.add(ChatColor.translateAlternateColorCodes('&', durability));

            //TRANSLATE COLORS
            List<String> finalList = new ArrayList<>();
            for (String s : list) finalList.add(ChatColor.translateAlternateColorCodes('&', s));

            meta.setLore(finalList);
        }
        itemStack.setItemMeta(meta);

        //ENCHANTMENTS
        for (int i = 0; i < enchantments.size(); i++) {
            Group2<Enchantment, Integer> entry = enchantments.get(i);
            Enchantment enchantment = entry.getX();
            int level = entry.getY();
            setNBTValue("grapes.enchantments." + i + ".type.key", enchantment.getKey().getKey());
            setNBTValue("grapes.enchantments." + i + ".type.name", enchantment.getKey().getNamespace());
            setNBTValue("grapes.enchantments." + i + ".level", level);
        }

        return this;
    }

    /**
     * The final Command of the Builder.
     * It updates the ItemStacks lore and returns the final ItemStack.
     *
     * @return The final ItemStack.
     */
    public ItemStack build() {
        update();
        return itemStack;
    }

    public ItemStack getItemStack() {
        return itemStack;
    }
}