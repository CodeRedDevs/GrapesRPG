package me.trqhxrd.grapesrpg.api.utils.items.nbt;

import net.minecraft.server.v1_16_R3.*;
import org.bukkit.craftbukkit.v1_16_R3.inventory.CraftItemStack;
import org.bukkit.inventory.ItemStack;

import java.util.*;

/**
 * This utility class can read the NBT Value of an item.
 *
 * @author Trqhxrd
 */
public class NBTReader {

    /**
     * The method for reading a String stored in an items NBT-Tag.
     *
     * @param host The item with the NBT-tag.
     * @param path The path to the value inside the NBT-Tag.
     * @return The value found at said path. In case the path doesn't exist, the return value will be null
     */
    public static String getNBTValueString(ItemStack host, String path) {
        try {
            net.minecraft.server.v1_16_R3.ItemStack nms = CraftItemStack.asNMSCopy(host);
            String[] split = path.split("\\.");
            NBTTagCompound base = nms.getTag();
            for (int i = 0; i < split.length; i++) {
                if (i != split.length - 1) base = base != null ? base.getCompound(split[i]) : null;
                else return Objects.requireNonNull(base).getString(split[i]);
            }
        } catch (Exception ignored) {
        }
        return null;
    }

    /**
     * The method for reading a String stored in an items NBT-Tag.
     *
     * @param host The item with the NBT-tag.
     * @param path The path to the value inside the NBT-Tag.
     * @return The value found at said path. In case the path doesn't exist, the return value will be null
     */
    public static Integer getNBTValueInt(ItemStack host, String path) {
        try {
            net.minecraft.server.v1_16_R3.ItemStack nms = CraftItemStack.asNMSCopy(host);
            String[] split = path.split("\\.");
            NBTTagCompound base = nms.getTag();
            for (int i = 0; i < split.length; i++) {
                if (i != split.length - 1) base = base != null ? base.getCompound(split[i]) : null;
                else return Objects.requireNonNull(base).getInt(split[i]);
            }
        } catch (Exception ignored) {
        }
        return null;
    }

    /**
     * The method for reading a String stored in an items NBT-Tag.
     *
     * @param host The item with the NBT-tag.
     * @param path The path to the value inside the NBT-Tag.
     * @return The value found at said path. In case the path doesn't exist, the return value will be null
     */
    public static NBTBase getNBTValue(ItemStack host, String path) {
        try {
            net.minecraft.server.v1_16_R3.ItemStack nms = CraftItemStack.asNMSCopy(host);
            String[] split = path.split("\\.");
            NBTTagCompound base = nms.getTag();
            for (int i = 0; i < split.length; i++) {
                if (i != split.length - 1) base = base != null ? base.getCompound(split[i]) : null;
                else return Objects.requireNonNull(base).get(split[i]);
            }
        } catch (Exception ignored) {
        }
        return null;
    }

    /**
     * This method returns a Map of Strings and NBTValues.
     * The Strings are the Paths to the Values.
     * Sub-Folders are marked with a '.'.
     * For example: "test.test" as a path is {@literal "{test: {test: <value>}}"} in NBT.
     *
     * @param is The item, from which you want to get all NBT-Values.
     * @return A Map containing all NBT-Values and their Paths.
     */
    public static Map<String, NBTValue<?>> getAllNBTValues(ItemStack is) {
        Map<String, NBTValue<?>> out = new HashMap<>();
        net.minecraft.server.v1_16_R3.ItemStack nms = CraftItemStack.asNMSCopy(is);
        NBTTagCompound nbt = nms.getOrCreateTag();
        List<String> list = detectEntries(nbt);

        for (String s : list) {
            if (!s.isBlank()) {
                NBTBase base = getNBTValue(is, s);
                if (base instanceof NBTTagCompound) continue;
                if (base instanceof NBTTagInt) out.put(s, new NBTValue.Integer(((NBTTagInt) base).asInt()));
                else if (base instanceof NBTTagString) out.put(s, new NBTValue.String(base.asString()));
                else if (base instanceof NBTTagIntArray) {
                    List<Integer> ints = new ArrayList<>();
                    NBTTagInt[] nbts = ((NBTTagIntArray) base).toArray(new NBTTagInt[0]);
                    for (NBTTagInt tag : nbts) ints.add(tag.asInt());
                    out.put(s, new NBTValue.IntegerArray(ints));
                } else if (base instanceof NBTTagDouble) out.put(s, new NBTValue.Double(((NBTTagDouble) base).asDouble()));
            }
        }
        return out;
    }

    /**
     * This method returns a list of all keys of NBT-Entries in the root folder.
     * This method works recursive, which means, that it will detect also entries, that are in a sub entry of root.
     * However you have to detect all the values for these paths.
     *
     * @param root The parent directory, which you want to scan for entries.
     * @return A List of all entries in the NBT-Directory.
     */
    public static List<String> detectEntries(NBTTagCompound root) {
        List<String> entries = new ArrayList<>(root.getKeys());
        for (int i = 0; i < entries.size(); i++) {
            String parentName = entries.get(i);
            NBTBase base = root.get(parentName);
            if (base instanceof NBTTagCompound) {
                List<String> sub = detectEntries(((NBTTagCompound) base));
                for (String s : sub) entries.add(parentName + "." + s);
            }
        }

        return entries;
    }
}
