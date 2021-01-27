package me.trqhxrd.grapesrpg.api.utils;

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
        List<String> list = new ArrayList<>(nbt.getKeys());

        for (int i = 0; i < list.size(); i++) {
            String path = list.get(i);
            if (path != null && !path.isEmpty() && !path.isBlank()) {
                if (getFolder(nbt, path) != null) {
                    nbt = getFolder(nbt, path);
                    for (String key : Objects.requireNonNull(nbt).getKeys()) {
                        list.add(!Objects.requireNonNull(path).isBlank() ? path + "." + key : key);
                    }
                }
            }
        }

        for (String s : list) {
            if (!s.isBlank()) {
                NBTBase base = getNBTValue(is, s);
                if (base instanceof NBTTagInt) out.put(s, new NBTValue.Integer(((NBTTagInt) base).asInt()));
                else if (base instanceof NBTTagString) out.put(s, new NBTValue.String(base.asString()));
                else if (base instanceof NBTTagIntArray) {
                    List<Integer> ints = new ArrayList<>();
                    NBTTagInt[] nbts = ((NBTTagIntArray) base).toArray(new NBTTagInt[0]);
                    for (NBTTagInt tag : nbts) ints.add(tag.asInt());

                    out.put(s, new NBTValue.IntegerArray(ints));
                } else if (base instanceof NBTTagDouble) out.put(s, new NBTValue.Double(((NBTTagDouble) base).asDouble()));
                else if (!(base instanceof NBTTagCompound)) throw new IllegalArgumentException("The path " + s + " does not contain a compatible data type");
            }
        }
        return out;
    }

    /**
     * This method gives a sub-folder, if you give the method its name and its parent folder.
     *
     * @param root The Parent-Directory.
     * @param name The Name of the Folder
     * @return If the Sub-Folder isn't null, it will return the sub-folder. Otherwise it will return null.
     */
    private static NBTTagCompound getFolder(NBTTagCompound root, String name) {
        String[] split = name.split("\\.");
        for (String s : split)
            try {
                if (root.get(s) instanceof NBTTagCompound) root = root.getCompound(s);
                else return null;
            } catch (Exception ignored) { }
        return root;
    }
}
