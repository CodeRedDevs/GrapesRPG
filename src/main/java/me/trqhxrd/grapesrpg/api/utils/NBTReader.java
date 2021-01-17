package me.trqhxrd.grapesrpg.api.utils;

import net.minecraft.server.v1_16_R3.NBTTagCompound;
import org.bukkit.craftbukkit.v1_16_R3.inventory.CraftItemStack;
import org.bukkit.inventory.ItemStack;

import java.util.Objects;

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
    public static String getNBTValue(ItemStack host, String path) {
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
}
