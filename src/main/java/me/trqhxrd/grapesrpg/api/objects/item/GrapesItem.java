package me.trqhxrd.grapesrpg.api.objects.item;

import me.trqhxrd.grapesrpg.Grapes;
import me.trqhxrd.grapesrpg.api.attribute.Serializable;
import me.trqhxrd.grapesrpg.api.utils.Builder;
import me.trqhxrd.grapesrpg.api.utils.NBTValue;
import me.trqhxrd.grapesrpg.api.utils.group.Group2;
import net.minecraft.server.v1_16_R3.NBTTagCompound;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_16_R3.inventory.CraftItemStack;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * This Class represents a serializable item from the GrapesAPI
 *
 * @author Trqhxrd
 */
public class GrapesItem implements Serializable<GrapesItem>, Builder<ItemStack> {

    /**
     * Any custom NBT-Tags.
     * The Key is the path for the value.
     * '.' in the path mean, that there is a subgroup of NBT-Tags.
     * ("test.helloWorld", "value" -> {test: {helloWorld:value}})
     */
    private final Map<String, NBTValue<?>> nbt;

    /**
     * The Item-Id.
     * Used for binding abilities to the item.
     */
    private int id;

    /**
     * The Material of the Item (e.g. Diamond Sword)
     * Used for generating the item correctly.
     */
    private Material material;

    /**
     * The name of the item.
     * Used for naming the item.
     */
    private String name;

    /**
     * A basic constructor to create a new GrapesItem.
     *
     * @param id       The id of the new item.
     * @param material The material of the new item.
     * @param name     The name of the new item. Can be left empty in which case the name of the item will be set to it's material name in the players language.
     */
    public GrapesItem(int id, Material material, String name) {
        this.id = id;
        this.material = material;
        this.name = name;
        this.nbt = new HashMap<>();
    }

    /**
     * A basic constructor to create a new GrapesItem.
     *
     * @param id         The id of the new item.
     * @param material   The material of the new item.
     * @param name       The name of the new item. Can be left empty in which case the name of the item will be set to it's material name in the players language.
     * @param nbtEntries The NBT-Values, that will be set to the item.
     */
    @SafeVarargs
    public GrapesItem(int id, Material material, String name, Group2<String, NBTValue<?>>... nbtEntries) {
        this.id = id;
        this.material = material;
        this.name = name;
        this.nbt = new HashMap<>();

        for (Group2<String, NBTValue<?>> entry : nbtEntries) this.nbt.put(entry.getX(), entry.getY());
    }

    /**
     * This method adds a custom NBT-Value to the map of values, that will be set as soon as the {@link GrapesItem#build()}-method gets called.
     *
     * @param path  The path of the NBT-Value, which you want to set.
     * @param value The value, that will be stored at the path.
     * @return The GrapesItem, for which this method got called. Used for creating command chains.
     */
    public GrapesItem addNBT(String path, NBTValue<?> value) {
        this.nbt.put(path, value);
        return this;
    }

    /**
     * This method adds a custom NBT-Value to the map of values, that will be set as soon as the {@link GrapesItem#build()}-method gets called.
     *
     * @param path  The path of the NBT-Value, which you want to set.
     * @param value The value, that will be stored at the path.
     * @return The GrapesItem, for which this method got called. Used for creating command chains.
     */
    public GrapesItem addNBT(String path, String value) {
        return this.addNBT(path, new NBTValue.String(value));
    }

    /**
     * This method adds a custom NBT-Value to the map of values, that will be set as soon as the {@link GrapesItem#build()}-method gets called.
     *
     * @param path  The path of the NBT-Value, which you want to set.
     * @param value The value, that will be stored at the path.
     * @return The GrapesItem, for which this method got called. Used for creating command chains.
     */
    public GrapesItem addNBT(String path, int value) {
        return this.addNBT(path, new NBTValue.Integer(value));
    }

    /**
     * This method adds a custom NBT-Value to the map of values, that will be set as soon as the {@link GrapesItem#build()}-method gets called.
     *
     * @param path  The path of the NBT-Value, which you want to set.
     * @param value The value, that will be stored at the path.
     * @return The GrapesItem, for which this method got called. Used for creating command chains.
     */
    public GrapesItem addNBT(String path, double value) {
        return this.addNBT(path, new NBTValue.Double(value));
    }

    /**
     * This method adds a custom NBT-Value to the map of values, that will be set as soon as the {@link GrapesItem#build()}-method gets called.
     *
     * @param path  The path of the NBT-Value, which you want to set.
     * @param value The value, that will be stored at the path.
     * @return The GrapesItem, for which this method got called. Used for creating command chains.
     */
    public GrapesItem addNBT(String path, boolean value) {
        return this.addNBT(path, new NBTValue.Boolean(value));
    }

    /**
     * This method adds a custom NBT-Value to the map of values, that will be set as soon as the {@link GrapesItem#build()}-method gets called.
     *
     * @param path  The path of the NBT-Value, which you want to set.
     * @param value The value, that will be stored at the path.
     * @return The GrapesItem, for which this method got called. Used for creating command chains.
     */
    public GrapesItem addNBT(String path, Integer... value) {
        return this.addNBT(path, new NBTValue.IntegerArray(Arrays.asList(value)));
    }

    /**
     * This method adds a custom NBT-Value to the map of values, that will be set as soon as the {@link GrapesItem#build()}-method gets called.
     *
     * @param path  The path of the NBT-Value, which you want to set.
     * @param value The value, that will be stored at the path.
     * @return The GrapesItem, for which this method got called. Used for creating command chains.
     */
    public GrapesItem addNBT(String path, Collection<Integer> value) {
        return this.addNBT(path, new NBTValue.IntegerArray(value));
    }

    /**
     * The Getter for the custom tags.
     *
     * @return A map of Strings. The Key is the nbt path while the maps value represents the entry into the items nbt data.
     */
    public Map<String, NBTValue<?>> getNbt() {
        return nbt;
    }

    /**
     * Getter for the items id.
     *
     * @return The items id.
     */
    public int getId() {
        return id;
    }

    /**
     * Setter for the items id.
     *
     * @param id The new id.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Getter for the material of the item.
     *
     * @return The material of the item.
     */
    public Material getMaterial() {
        return material;
    }

    /**
     * Setter of the material of the item.
     *
     * @param material The new material of the item.
     */
    public void setMaterial(Material material) {
        this.material = material;
    }

    /**
     * Getter for the items name.
     *
     * @return The items name.
     */
    public String getName() {
        return name;
    }

    /**
     * Setter for the items name.
     *
     * @param name The items new name.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * This method serializes an Object (t) into a String.
     *
     * @param grapesItem The Object, which you want to serialize.
     * @return The String containing all data about the object.
     */
    @Override
    public String serialize(GrapesItem grapesItem) {
        return Grapes.GSON.toJson(this);
    }

    /**
     * This method serializes the Object, from which it will be executed.
     *
     * @return The serialized object.
     */
    @Override
    public String serialize() {
        return this.serialize(this);
    }

    /**
     * This method is able to create an object from a serialized String.
     *
     * @param s The String you want to deserialize.
     * @return The Object.
     */
    @Override
    public GrapesItem deserialize(String s) {
        return Grapes.GSON.fromJson(s, this.getClass());
    }

    /**
     * This method can build an object of the type T.
     * You have to create your own Class, which implements the Builder.
     *
     * @return The built object, which was created by the builder.
     */
    @Override
    public ItemStack build() {
        ItemStack is = new ItemStack(this.material);
        ItemMeta meta = is.getItemMeta();
        if (meta != null) {
            if (this.name != null && !this.name.isBlank() && !this.name.isEmpty())
                meta.setDisplayName("§7" + ChatColor.translateAlternateColorCodes('&', this.name));

            is.setItemMeta(meta);
        }

        for (String path : nbt.keySet()) is = NBTEditor.setNBTValue(is, path, this.nbt.get(path));
        is = NBTEditor.setNBTValue(is, "grapes.name", this.name);
        is = NBTEditor.setNBTValue(is, "grapes.id", this.id);

        return is;
    }

    /**
     * This class contains all methods for storing nbt data onto an ItemStack.
     */
    private static class NBTEditor {

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
                if (i == 0) nbts[i] = nms.getOrCreateTag();
                else {
                    if (i == pathArray.length - 1) {
                        if (value instanceof NBTValue.String) nbts[i - 1].setString(part, (((NBTValue.String) value).getValue() == null) ? "" : (String) value.getValue());
                        else if (value instanceof NBTValue.Integer) nbts[i - 1].setInt(part, (int) value.getValue());
                        else if (value instanceof NBTValue.Double) nbts[i - 1].setDouble(part, (double) value.getValue());
                        else if (value instanceof NBTValue.Boolean) nbts[i - 1].setBoolean(part, (boolean) value.getValue());
                        else if (value instanceof NBTValue.IntegerArray) nbts[i - 1].setIntArray(part, (int[]) value.getValue());
                        else nbts[i - 1].setString(part, value.getValue().toString());
                    } else nbts[i] = nbts[i - 1].getCompound(pathArray[i]);
                }
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
        public static ItemStack setNBTValue(ItemStack is, String path, boolean value) {
            return setNBTValue(is, path, new NBTValue.Boolean(value));
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
}
