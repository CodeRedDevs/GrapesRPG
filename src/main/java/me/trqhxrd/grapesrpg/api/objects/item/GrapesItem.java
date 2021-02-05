package me.trqhxrd.grapesrpg.api.objects.item;

import me.trqhxrd.grapesrpg.Grapes;
import me.trqhxrd.grapesrpg.api.attribute.Serializable;
import me.trqhxrd.grapesrpg.api.utils.Builder;
import me.trqhxrd.grapesrpg.api.utils.items.NBTReader;
import me.trqhxrd.grapesrpg.api.utils.items.NBTValue;
import me.trqhxrd.grapesrpg.api.utils.Utils;
import me.trqhxrd.grapesrpg.api.utils.group.Group2;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.*;

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
     * The Rarity of the item.
     */
    private Rarity rarity;

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
     * The amount of the item.
     */
    private int amount;

    /**
     * A basic constructor to create a new GrapesItem.
     *
     * @param id       The id of the new item.
     * @param material The material of the new item.
     */
    public GrapesItem(int id, Material material) {
        this(id, material, null, 1, Rarity.COMMON, new HashMap<>());
    }

    /**
     * A basic constructor to create a new GrapesItem.
     *
     * @param id       The id of the new item.
     * @param material The material of the new item.
     * @param name     The name of the new item. Can be left empty in which case the name of the item will be set to it's material name in the players language.
     */
    public GrapesItem(int id, Material material, String name) {
        this(id, material, name, 1, Rarity.COMMON, new HashMap<>());
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
    public GrapesItem(int id, Material material, String name, Rarity rarity, Group2<String, NBTValue<?>>... nbtEntries) {
        this(id, material, name, 1, rarity);
        for (Group2<String, NBTValue<?>> entry : nbtEntries) this.nbt.put(entry.getX(), entry.getY());
    }

    /**
     * A constructor, which supports the items amount to be given.
     *
     * @param id       The items id.
     * @param material The Material of the item.
     * @param name     The name of the item. If black, will be set to the materials name.
     * @param amount   The amount of the item. (e.g. 64 = a stack).
     * @param rarity   The rarity of the item.
     */
    public GrapesItem(int id, Material material, String name, int amount, Rarity rarity) {
        this(id, material, name, amount, rarity, new HashMap<>());
    }

    /**
     * A constructor, which supports the items amount to be given.
     *
     * @param id       The items id.
     * @param material The Material of the item.
     * @param name     The name of the item. If black, will be set to the materials name.
     * @param amount   The amount of the item. (e.g. 64 = a stack).
     * @param rarity   The rarity of the item.
     * @param nbt      The custom nbt-tags, you want to set.
     */
    public GrapesItem(int id, Material material, String name, int amount, Rarity rarity, Map<String, NBTValue<?>> nbt) {
        this.nbt = nbt;
        this.id = id;
        this.material = material;
        this.name = name;
        this.amount = amount;
        this.rarity = rarity;
    }

    /**
     * Creates a new GrapesItem from the values stored in an ItemStack.
     *
     * @param is The native item.
     * @return A GrapesItem, that has the same data as the ItemStack, which you gave as a parameter.
     */
    public static GrapesItem fromItemStack(ItemStack is) {
        Integer id = NBTReader.getNBTValueInt(is, "grapes.id");
        if (id != null) {
            GrapesItem item = new GrapesItem(id, is.getType());
            item.setAmount(is.getAmount());
            item.getNbt().clear();
            item.getNbt().putAll(NBTReader.getAllNBTValues(is));

            item.setName((String) item.getNbt().get("grapes.name").getValue());
            item.setRarity(Rarity.getById(((int) item.getNbt().get("grapes.rarity").getValue())));

            if (item.getRarity() == null) item.setRarity(Rarity.DEFAULT_RARITY);

            // Removing all NBT-Values, which are stored in variables in an object of this class.
            Set<String> remove = new HashSet<>();
            for (String s : item.getNbt().keySet()) if (s.startsWith("grapes.")) remove.add(s);
            for (String s : remove) item.getNbt().remove(s);

            return item;
        }
        return null;
    }

    /**
     * Getter for the items rarity.
     *
     * @return The current rarity of the item.
     */
    public Rarity getRarity() {
        return rarity;
    }

    /**
     * This method overrides an items rarity.
     *
     * @param rarity The new rarity for the item.
     * @return The Item itself. Used for creating command chains.
     */
    private GrapesItem setRarity(Rarity rarity) {
        this.rarity = rarity;
        return this;
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
     * Basic getter for the items amount.
     *
     * @return The items amount.
     */
    public int getAmount() {
        return amount;
    }

    /**
     * Basic setter for the items amount.
     *
     * @param amount The items new amount.
     * @return The Item itself. Used for creating command chains.
     */
    public GrapesItem setAmount(int amount) {
        this.amount = amount;
        return this;
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
     * @return The GrapesItem. Used for creating command chains.
     */
    public GrapesItem setName(String name) {
        this.name = name;
        return this;
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

        is.setAmount(this.amount);

        ItemMeta meta = is.getItemMeta();
        if (meta != null) {
            if (this.name != null && !this.name.isBlank() && !this.name.isEmpty())
                meta.setDisplayName(Utils.translateColorCodes("&" + this.rarity.getColor() + this.name));

            List<String> lore = new ArrayList<>();

            //Add Rarity to item lore
            if (rarity != null) {
                lore.add("");
                lore.add(this.rarity.getFormattedName());
            }

            String[] strings = new String[lore.size()];
            for (int i = 0; i < strings.length; i++) strings[i] = Utils.translateColorCodes(lore.get(i));
            lore = Arrays.asList(strings);

            meta.setLore(lore);

            is.setItemMeta(meta);
        }

        for (String path : nbt.keySet()) is = NBTEditor.setNBTValue(is, path, this.nbt.get(path));
        is = NBTEditor.setNBTValue(is, "grapes.name", this.name);
        is = NBTEditor.setNBTValue(is, "grapes.id", this.id);
        is = NBTEditor.setNBTValue(is, "grapes.rarity", this.rarity != null ? this.rarity.getId() : Rarity.DEFAULT_RARITY.getId());

        return is;
    }
}
