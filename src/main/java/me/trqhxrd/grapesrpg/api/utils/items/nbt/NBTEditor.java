package me.trqhxrd.grapesrpg.api.utils.items.nbt;

import net.minecraft.server.v1_16_R3.NBTTagCompound;
import org.bukkit.craftbukkit.v1_16_R3.inventory.CraftItemStack;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Collection;

/**
 * This class contains all methods for storing nbt data onto an ItemStack.
 */
public class NBTEditor {

    /**
     * This method is able to set an NBT-Tag to an ItemStack.
     *
     * @param is    The ItemStack, for which you want to set an NBT-Tag.
     * @param path  The Path to the NBT-Value.
     * @param value The Value, which you want to set at the path in the items NBT-Tag.
     * @return The ItemStack, but with the set NBT-Value.
     */
    public static ItemStack setNBTValue(ItemStack is, String path, NBTValue<?> value) {
        path = "null." + path;
        String[] pathArray = path.split("\\.");
        pathArray[0] = null;
        net.minecraft.server.v1_16_R3.ItemStack nms = CraftItemStack.asNMSCopy(is);
        NBTTagCompound[] nbts = new NBTTagCompound[pathArray.length - 1];
        for (int i = 0; i < pathArray.length; i++) {
            String part = pathArray[i];
            if (i != 0) {
                if (i == pathArray.length - 1) {
                    if (value instanceof NBTValue.String) nbts[i - 1].setString(part, (((NBTValue.String) value).getValue() == null) ? "" : (String) value.getValue());
                    else if (value instanceof NBTValue.Integer) nbts[i - 1].setInt(part, (int) value.getValue());
                    else if (value instanceof NBTValue.Double) nbts[i - 1].setDouble(part, (double) value.getValue());
                    else if (value instanceof NBTValue.IntegerArray) {
                        int[] ints = new int[((NBTValue.IntegerArray) value).getValue().size()];
                        for (int j = 0; j < ints.length; j++) ints[i] = new ArrayList<>(((NBTValue.IntegerArray) value).getValue()).get(i);
                        nbts[i - 1].setIntArray(part, ints);
                    } else nbts[i - 1].setString(part, value.getValue().toString());
                } else nbts[i] = nbts[i - 1].getCompound(pathArray[i]);
            } else nbts[i] = nms.getOrCreateTag();
        }
        for (int i = nbts.length; i-- > 0; ) {
            if (i - 1 < nbts.length && i - 1 >= 0) {
                nbts[i - 1].set(pathArray[i], nbts[i]);
            }
        }
        nms.setTag(nbts[0]);
        return CraftItemStack.asBukkitCopy(nms);
    }

    /**
     * This method is able to set an NBT-Tag to an ItemStack.
     *
     * @param is    The ItemStack, for which you want to set an NBT-Tag.
     * @param path  The Path to the NBT-Value.
     * @param value The Value, which you want to set at the path in the items NBT-Tag.
     * @return The ItemStack, but with the set NBT-Value.
     */
    public static ItemStack setNBTValue(ItemStack is, String path, String value) {
        return setNBTValue(is, path, new NBTValue.String(value));
    }

    /**
     * This method is able to set an NBT-Tag to an ItemStack.
     *
     * @param is    The ItemStack, for which you want to set an NBT-Tag.
     * @param path  The Path to the NBT-Value.
     * @param value The Value, which you want to set at the path in the items NBT-Tag.
     * @return The ItemStack, but with the set NBT-Value.
     */
    public static ItemStack setNBTValue(ItemStack is, String path, int value) {
        return setNBTValue(is, path, new NBTValue.Integer(value));
    }

    /**
     * This method is able to set an NBT-Tag to an ItemStack.
     *
     * @param is    The ItemStack, for which you want to set an NBT-Tag.
     * @param path  The Path to the NBT-Value.
     * @param value The Value, which you want to set at the path in the items NBT-Tag.
     * @return The ItemStack, but with the set NBT-Value.
     */
    public static ItemStack setNBTValue(ItemStack is, String path, double value) {
        return setNBTValue(is, path, new NBTValue.Double(value));
    }

    /**
     * This method is able to set an NBT-Tag to an ItemStack.
     *
     * @param is    The ItemStack, for which you want to set an NBT-Tag.
     * @param path  The Path to the NBT-Value.
     * @param value The Value, which you want to set at the path in the items NBT-Tag.
     * @return The ItemStack, but with the set NBT-Value.
     */
    public static ItemStack setNBTValue(ItemStack is, String path, Integer... value) {
        return setNBTValue(is, path, new NBTValue.IntegerArray(value));
    }

    /**
     * This method is able to set an NBT-Tag to an ItemStack.
     *
     * @param is    The ItemStack, for which you want to set an NBT-Tag.
     * @param path  The Path to the NBT-Value.
     * @param value The Value, which you want to set at the path in the items NBT-Tag.
     * @return The ItemStack, but with the set NBT-Value.
     */
    public static ItemStack setNBTValue(ItemStack is, String path, Collection<Integer> value) {
        return setNBTValue(is, path, new NBTValue.IntegerArray(value));
    }
}