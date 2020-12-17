package me.trqhxrd.grapesrpg.api.objects;

import com.jojodmo.safeNBT.api.SafeNBT;
import me.trqhxrd.grapesrpg.api.utils.group.Group2;
import me.trqhxrd.grapesrpg.api.utils.group.Group3;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

/**
 * This Class represents a SkyBlockItem.
 * If you want to create a new Item, just create another Class, which extends this Class.
 *
 * @author Trqhxrd
 */
@SuppressWarnings("deprecation")
public class GrapesItem {

    /**
     * The default-value for booleans, which will get returned, if the NBT-Tag does not contain the path.
     */
    public static final boolean DEFAULT_BOOLEAN = false;
    /**
     * The default-value for Strings, which will get returned, if the NBT-Tag does not contain the path.
     */
    public static final String DEFAULT_STRING = "§c§l§f§e";
    /**
     * The default-value for ints, which will get returned, if the NBT-Tag does not contain the path.
     */
    public static final int DEFAULT_INTEGER = 0;

    /**
     * This method gets the id of the item. Can be used to bind special abilities to that item.
     *
     * @param is The {@link ItemStack}, from which you want to get the ID.
     * @return The id of that item. (Path: grapes.id).
     */
    public static int getID(ItemStack is) {
        return getNBTValueInt(is, "grapes.id");
    }

    /**
     * This method gets the damage, an item does, when using it on another player.
     * Value X is the physical damage of the item.
     * Value Y is the magical damage of the item.
     * Value Z is the void damage of the item.
     *
     * @param is The Item, from which you want to get the damage-stats.
     * @return The damage-stats of that item.
     */
    public static Group3<Integer, Integer, Integer> getDamage(ItemStack is) {
        Group3<Integer, Integer, Integer> damage = new Group3<>();
        damage.setX(getNBTValueInt(is, "grapes.damage.physical"));
        damage.setY(getNBTValueInt(is, "grapes.damage.magical"));
        damage.setZ(getNBTValueInt(is, "grapes.damage.void"));

        return damage;
    }

    /**
     * This method gets the defence, the item does, when you wear it.
     * These defence values don't have to be 0 if you can't wear it.
     * The can be {@literal >} 0 even if you can't wear the item.
     * Value X in the group represents the physical defence it does.
     * Value Y in the group represents the magical defence it does.
     * Value Z in the group represents the void defence it does.
     *
     * @param is The item from which you want to get the defence values.
     * @return The defence values, wrapped in a group with three content fields.
     */
    public static Group3<Integer, Integer, Integer> getDefence(ItemStack is) {
        Group3<Integer, Integer, Integer> defence = new Group3<>();
        defence.setX(getNBTValueInt(is, "grapes.defence.physical"));
        defence.setY(getNBTValueInt(is, "grapes.defence.magical"));
        defence.setZ(getNBTValueInt(is, "grapes.defence.void"));

        return defence;
    }

    /**
     * This method returns the durability and max durability of the item.
     * Value X of the Group contains the current durability.
     * Value Y of the Group contains the maximum durability possible.
     *
     * @param is The ItemStack from which you want to get the durability.
     * @return The durability of the item wrapped in a Group with two content fields.
     */
    public static Group2<Integer, Integer> getDurability(ItemStack is) {
        Group2<Integer, Integer> durability = new Group3<>();
        durability.setX(getNBTValueInt(is, "grapes.durability.current"));
        durability.setY(getNBTValueInt(is, "grapes.durability.max"));

        return durability;
    }

    /**
     * Returns the custom name, you set for this item.
     *
     * @param is The item from which you want to get the name.
     * @return The name you set for this item.
     */
    public static String getName(ItemStack is) {
        return getNBTValueString(is, "grapes.name");
    }

    /**
     * This method returns the lore you set for the item.
     * Please remind, that the original item lore a result of all the grapes-values (Damage, Defence, Durability, etc.) is.
     * This method returns the custom text you added for this and it doesn't return the lore how it is displayed.
     *
     * @param is The ItemStack from which you want to get the lore.
     * @return The lore of the item.
     */
    public static List<String> getLore(ItemStack is) {
        List<String> lore = new ArrayList<>();
        boolean b = true;
        int i = -1;
        while (b) {
            String s = getNBTValueString(is, "grapes.lore." + i++);
            if (!s.equals(DEFAULT_STRING)) lore.add(s);
            else b = false;
        }
        return lore;
    }

    /**
     * Returns true, if the item is broken.
     *
     * @param is The item for which you want to check if it's broken.
     * @return {@literal true -> item is broken; false -> item is not broken}.
     */
    public static boolean isBroken(ItemStack is) {
        return getDurability(is).getX() <= 0;
    }

    /**
     * This method returns a list of all enchantments on this item.
     *
     * @param is The item of which you want to get the enchantments.
     * @return A Map of all enchantments with their levels.
     */
    public static List<Group2<Enchantment, Integer>> getEnchantments(ItemStack is) {
        List<Group2<Enchantment, Integer>> enchantments = new ArrayList<>();
        int i = -1;
        while (!getNBTValueString(is, "grapes.enchantments." + i++ + ".type").equals(""))
            enchantments.add(new Group2<>(
                    Enchantment.getByKey(
                            new NamespacedKey(
                                    getNBTValueString(is, "grapes.enchantments." + i++ + ".type.key"),
                                    getNBTValueString(is, "grapes.enchantments." + i + ".type.name")
                            )
                    ),
                    getNBTValueInt(is, "grapes.enchants." + i++ + ".level")
            ));
        return enchantments;
    }

    /**
     * This method will return the Integer, saved at the path given.
     * In case, that this path doesn't exist, it will return {@link GrapesItem#DEFAULT_INTEGER}.
     * Please remember: If you give for example "test.xyz" as a path it will return the value of {path:{xyz:[value]}}.
     *
     * @param itemStack The ItemStack from which you want to get the nbt value.
     * @param path      The path of the NBTTag you want to get.
     * @return The value of the nbt tag.
     */
    public static int getNBTValueInt(ItemStack itemStack, String path) {
        try {
            String[] pathArray = path.split("\\.");
            SafeNBT nbt = SafeNBT.get(itemStack);
            for (int i = 0; i < pathArray.length; i++) {
                String part = pathArray[i];
                if (i != pathArray.length - 1) {
                    nbt = nbt.getCompoundThrows(part);
                } else {
                    return nbt.getInt(part);
                }
            }
        } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return DEFAULT_INTEGER;
    }

    /**
     * This method will return the Boolean, saved at the path given.
     * In case, that this path doesn't exist, it will return {@link GrapesItem#DEFAULT_BOOLEAN}.
     * Please remember: If you give for example "test.xyz" as a path it will return the value of {path:{xyz:[value]}}.
     *
     * @param itemStack The ItemStack from which you want to get the nbt value.
     * @param path      The path of the NBTTag you want to get.
     * @return The value of the nbt tag.
     */
    public static boolean getNBTValueBoolean(ItemStack itemStack, String path) {
        try {
            String[] pathArray = path.split("\\.");
            SafeNBT nbt = SafeNBT.get(itemStack);
            for (int i = 0; i < pathArray.length; i++) {
                String part = pathArray[i];
                if (i != pathArray.length - 1) {
                    nbt = nbt.getCompoundThrows(part);
                } else {
                    return nbt.getString(part).equalsIgnoreCase("true");
                }
            }
        } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return DEFAULT_BOOLEAN;
    }

    /**
     * This method will return the Integer, saved at the path given.
     * In case, that this path doesn't exist, it will return {@link GrapesItem#DEFAULT_STRING}.
     * Please remember: If you give for example "test.xyz" as a path it will return the value of {path:{xyz:[value]}}.
     * Note: The default String are some ColorCodes, because otherwise you wouldn't be able to add black lines to the lore.
     *
     * @param itemStack The ItemStack from which you want to get the nbt value.
     * @param path      The path of the NBTTag you want to get.
     * @return The value of the nbt tag.
     */
    public static String getNBTValueString(ItemStack itemStack, String path) {
        try {
            String[] pathArray = path.split("\\.");
            SafeNBT nbt = SafeNBT.get(itemStack);
            for (int i = 0; i < pathArray.length; i++) {
                String part = pathArray[i];
                if (i != pathArray.length - 1) {
                    nbt = nbt.getCompoundThrows(part);
                } else {
                    return nbt.getString(part);
                }
            }
        } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return DEFAULT_STRING;
    }
}