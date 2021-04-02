package me.trqhxrd.grapesrpg.api.utils.items.nbt;

import net.minecraft.server.v1_16_R3.NBTTagCompound;
import org.bukkit.craftbukkit.v1_16_R3.inventory.CraftItemStack;
import org.bukkit.inventory.ItemStack;

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
    public static ItemStack setNBTValue(ItemStack is, String path, Object value) {
        path = "null." + path;
        String[] pathArray = path.split("\\.");
        pathArray[0] = null;
        net.minecraft.server.v1_16_R3.ItemStack nms = CraftItemStack.asNMSCopy(is);
        NBTTagCompound[] nbts = new NBTTagCompound[pathArray.length - 1];
        for (int i = 0; i < pathArray.length; i++) {
            String part = pathArray[i];
            if (i != 0) {
                if (i == pathArray.length - 1) {
                    if (value instanceof String) nbts[i - 1].setString(part, (String) value);
                    else if (value instanceof Integer) nbts[i - 1].setInt(part, (int) value);
                    else if (value instanceof Double) nbts[i - 1].setDouble(part, (double) value);
                    else if (value != null) System.err.println("Could not save \"" + value.toString() + "\" at \"" + path + "\"");
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
}
