package me.trqhxrd.grapesrpg.api.objects.item;

import me.trqhxrd.grapesrpg.Grapes;
import me.trqhxrd.grapesrpg.api.attribute.Serializable;
import me.trqhxrd.grapesrpg.api.utils.Builder;
import me.trqhxrd.grapesrpg.api.utils.Utils;
import me.trqhxrd.grapesrpg.api.utils.group.Group2;
import me.trqhxrd.grapesrpg.api.utils.group.Group3;
import me.trqhxrd.grapesrpg.api.utils.items.nbt.NBTEditor;
import me.trqhxrd.grapesrpg.api.utils.items.nbt.NBTReader;
import me.trqhxrd.grapesrpg.api.utils.items.nbt.NBTValue;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;

import java.awt.*;
import java.util.List;
import java.util.*;

/**
 * This Class represents a serializable item from the GrapesAPI
 *
 * @author Trqhxrd
 */
public class GrapesItem implements Serializable<GrapesItem>, Builder<ItemStack> {

    /**
     * The default damage-values of an item.
     */
    public static final Group3<Integer, Integer, Integer> DEFAULT_STATS = new Group3<>(1, 0, 0);

    /**
     * Any custom NBT-Tags.
     * The Key is the path for the value.
     * '.' in the path mean, that there is a subgroup of NBT-Tags.
     * ("test.helloWorld", "value" -> {test: {helloWorld:value}})
     */
    private final Map<String, NBTValue<?>> nbt;
    /**
     * This field contains all information about the items protection or damage.
     * This field is a {@link Group3}.
     * The physical damage / protection is stored in a field called {@code x}.
     * The magical damage / protection is stored in a field called {@code y}.
     * The void damage / protection is stored in a field called {@code z}.
     */
    private Group3<Integer, Integer, Integer> stats;
    /**
     * This field contains the type of the item.
     * It can either be "ARMOR", "MELEE" or "RANGED".
     *
     * @see ItemType
     */
    private ItemType type;
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
     * If you can dye the item, this int array stores the rgb color values.
     * Index 0 is red. 1 is green and 2 is blue.
     */
    private int[] color;

    /**
     * A basic constructor to create a new GrapesItem.
     *
     * @param id       The id of the new item.
     * @param material The material of the new item.
     */
    public GrapesItem(int id, Material material) {
        this(id, material, null, 1, Rarity.DEFAULT_RARITY, DEFAULT_STATS, ItemType.MELEE, new HashMap<>());
    }

    /**
     * A basic constructor to create a new GrapesItem.
     *
     * @param id       The id of the new item.
     * @param material The material of the new item.
     * @param name     The name of the new item. Can be left empty in which case the name of the item will be set to it's material name in the players language.
     */
    public GrapesItem(int id, Material material, String name) {
        this(id, material, name, 1, Rarity.DEFAULT_RARITY, DEFAULT_STATS, ItemType.MELEE, new HashMap<>());
    }

    /**
     * A basic constructor to create a new GrapesItem.
     *
     * @param id             The id of the new item.
     * @param material       The material of the new item.
     * @param name           The name of the new item. Can be left empty in which case the name of the item will be set to it's material name in the players language.
     * @param rarity         The rarity of the item.
     * @param physicalDamage The physical damage, the item is supposed to deal.
     * @param magicalDamage  The magical damage, the item is supposed to deal.
     * @param voidDamage     The void damage, the item is supposed to deal.
     */
    public GrapesItem(int id, Material material, String name, Rarity rarity, int physicalDamage, int magicalDamage, int voidDamage) {
        this(id, material, name, 1, rarity, physicalDamage, magicalDamage, voidDamage, ItemType.MELEE, new HashMap<>());
    }

    /**
     * A basic constructor to create a new GrapesItem.
     *
     * @param id         The id of the new item.
     * @param material   The material of the new item.
     * @param name       The name of the new item. Can be left empty in which case the name of the item will be set to it's material name in the players language.
     * @param rarity     The rarity of the new item.
     * @param nbtEntries The NBT-Values, that will be set to the item.
     */
    @SafeVarargs
    public GrapesItem(int id, Material material, String name, Rarity rarity, Group2<String, NBTValue<?>>... nbtEntries) {
        this(id, material, name, 1, rarity, DEFAULT_STATS, ItemType.MELEE, new HashMap<>());
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
        this(id, material, name, amount, rarity, DEFAULT_STATS, ItemType.MELEE, new HashMap<>());
    }

    /**
     * A constructor, which supports the items amount to be given.
     *
     * @param id            The items id.
     * @param material      The Material of the item.
     * @param name          The name of the item. If black, will be set to the materials name.
     * @param amount        The amount of the item. (e.g. 64 = a stack).
     * @param rarity        The rarity of the item.
     * @param nbt           The custom nbt-tags, you want to set.
     * @param statsPhysical The physical damage / protection, that the item deals on hit / protects against on impact.
     * @param statsMagical  The magical damage / protection, that the item deals on hit / protects against on impact.
     * @param statsVoid     The void damage / protection, that the item deals on hit / protects against on impact.
     * @param type          This defines the type of the item.
     */
    public GrapesItem(int id, Material material, String name, int amount, Rarity rarity, int statsPhysical, int statsMagical, int statsVoid, ItemType type, Map<String, NBTValue<?>> nbt) {
        this(id, material, name, amount, rarity, new Group3<>(statsPhysical, statsMagical, statsVoid), type, nbt);
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
     * @param stats    The  damage / protection, that the item deals on hit / protects against on impact.
     * @param type     This defines the type of the item.
     */
    public GrapesItem(int id, Material material, String name, int amount, Rarity rarity, Group3<Integer, Integer, Integer> stats, ItemType type, Map<String, NBTValue<?>> nbt) {
        this.nbt = nbt;
        this.id = id;
        this.material = material;
        this.name = name;
        this.amount = amount;
        this.rarity = rarity;
        this.stats = stats;
        this.type = type;
    }

    /**
     * Creates a new GrapesItem from the values stored in an ItemStack.
     *
     * @param is The native item.
     * @return A GrapesItem, that has the same data as the ItemStack, which you gave as a parameter.
     */
    public static GrapesItem fromItemStack(ItemStack is) {
        if (is != null) {
            Integer id = NBTReader.getNBTValueInt(is, "grapes.id");
            if (id != null) {
                GrapesItem item = new GrapesItem(id, is.getType());
                item.setAmount(is.getAmount());
                item.getNbt().clear();
                item.getNbt().putAll(NBTReader.getAllNBTValues(is));

                if (is.getItemMeta() instanceof LeatherArmorMeta) {
                    int[] color = new int[3];
                    LeatherArmorMeta colorMeta = ((LeatherArmorMeta) is.getItemMeta());
                    color[0] = colorMeta.getColor().getRed();
                    color[1] = colorMeta.getColor().getGreen();
                    color[2] = colorMeta.getColor().getBlue();
                    item.setColor(color[0], color[1], color[2]);
                }

                if (item.getNbt().containsKey("grapes.name")) item.setName((String) item.getNbt().get("grapes.name").getValue());
                else item.setName(null);
                if (item.getNbt().containsKey("grapes.rarity")) item.setRarity(Rarity.getById(((int) item.getNbt().get("grapes.rarity").getValue())));
                else item.setRarity(Rarity.DEFAULT_RARITY);

                Group3<Integer, Integer, Integer> statsNew = new Group3<>(DEFAULT_STATS);
                if (item.getNbt().containsKey("grapes.stats.physical")) statsNew.setX((Integer) item.getNbt().get("grapes.stats.physical").getValue());
                if (item.getNbt().containsKey("grapes.stats.magical")) statsNew.setY((Integer) item.getNbt().get("grapes.stats.magical").getValue());
                if (item.getNbt().containsKey("grapes.stats.void")) statsNew.setZ((Integer) item.getNbt().get("grapes.stats.void").getValue());
                item.setStats(statsNew);

                ItemType typeNew = ItemType.DEFAULT_TYPE;
                if (item.getNbt().containsKey("grapes.stats.type")) typeNew = ItemType.valueOf(((String) item.getNbt().get("grapes.stats.type").getValue()));
                item.setType(typeNew);

                if (item.getRarity() == null) item.setRarity(Rarity.DEFAULT_RARITY);

                // Removing all NBT-Values, which are stored in variables in an object of this class.
                Set<String> remove = new HashSet<>();
                for (String s : item.getNbt().keySet()) if (s.startsWith("grapes.")) remove.add(s);
                for (String s : remove) item.getNbt().remove(s);

                return item;
            }
        }
        return null;
    }

    /**
     * This method is required by the interface {@link Serializable} but can't be forced, because it has to be static.
     * It is used for deserializing an Object from a String.
     *
     * @param serializedObject The Serialized Object, which you want to deserialize.
     * @return An Object of the Class, this method was written in. The serialized values got also copied into this object.
     */
    public static GrapesItem deserialize(String serializedObject) {
        return Grapes.GSON.fromJson(serializedObject, GrapesItem.class);
    }

    /**
     * Getter for the items damage and defence stats.
     *
     * @return The items damage and defence stats.
     */
    public Group3<Integer, Integer, Integer> getStats() {
        return stats;
    }

    /**
     * Setter for all of the items stats.
     *
     * @param stats The new damage / protections values for physical, magical and void damage / protection.
     * @return The instance of the item. Can be used similar to a pipeline.
     */
    public GrapesItem setStats(Group3<Integer, Integer, Integer> stats) {
        this.stats = stats;
        return this;
    }

    /**
     * Getter for the items type.
     *
     * @return The items type.
     */
    public ItemType getType() {
        return type;
    }

    /**
     * Setter for the items type.
     *
     * @param type The new type of the item.
     * @return The instance of the item. Can be used similar to a pipeline.
     */
    public GrapesItem setType(ItemType type) {
        this.type = type;
        return this;
    }

    /**
     * Getter for the physical damage / protection.
     *
     * @return The items physical damage / protection.
     */
    public int getPhysicalStats() {
        return this.stats.getX();
    }

    /**
     * Setter for the items physical damage / protection
     *
     * @param newStats The new physical damage / protection-value.
     * @return The instance of the item. Can be used similar to a pipeline.
     */
    public GrapesItem setPhysicalStats(int newStats) {
        this.stats.setX(newStats);
        return this;
    }

    /**
     * Getter for the magical damage / protection.
     *
     * @return The items magical damage / protection.
     */
    public int getMagicalStats() {
        return this.stats.getY();
    }

    /**
     * Setter for the items magical damage / protection
     *
     * @param newStats The new magical damage / protection-value.
     * @return The instance of the item. Can be used similar to a pipeline.
     */
    public GrapesItem setMagicalStats(int newStats) {
        this.stats.setY(newStats);
        return this;
    }

    /**
     * Getter for the void damage / protection.
     *
     * @return The items void damage / protection.
     */
    public int getVoidStats() {
        return this.stats.getZ();
    }

    /**
     * Setter for the items void damage / protection
     *
     * @param newStats The new void damage / protection-value.
     * @return The instance of the item. Can be used similar to a pipeline.
     */
    public GrapesItem setVoidStats(int newStats) {
        this.stats.setZ(newStats);
        return this;
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
     * This method sets the color of the item.
     * The color will only be applied if the item can be colored.
     *
     * @param color The new color for the item.
     * @return The GrapesItem. Used for creating command chains.
     */
    public GrapesItem setColor(java.awt.Color color) {
        return this.setColor(org.bukkit.Color.fromRGB(color.getRed(), color.getGreen(), color.getBlue()));
    }

    /**
     * This method sets the color of the item.
     * The color will only be applied if the item can be colored.
     *
     * @param color The new color for the item.
     * @return The GrapesItem. Used for creating command chains.
     */
    public GrapesItem setColor(org.bukkit.Color color) {
        this.color = new int[]{color.getRed(), color.getGreen(), color.getBlue()};
        return this;
    }

    /**
     * This method sets the color of the item.
     *
     * @param r The amount of red in the color (0 - 255).
     * @param g The amount of green in the color (0 - 255).
     * @param b The amount of blue in the color (0 - 255).
     * @return The GrapesItem. Used for creating command chains.
     */
    public GrapesItem setColor(int r, int g, int b) {
        return this.setColor(new Color(r, g, b));
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

            if (meta instanceof LeatherArmorMeta && color != null) ((LeatherArmorMeta) meta).setColor(org.bukkit.Color.fromRGB(color[0], color[1], color[2]));

            List<String> lore = new ArrayList<>();

            //Add item damage / protection
            if (!this.stats.equals(DEFAULT_STATS)) {
                String typeString = this.type.getLoreEntry();
                String[] lines = new String[]{
                        "    &#9d9fa3" + this.stats.getX() + " physical",
                        "    &#ae55d4" + this.stats.getY() + " magical",
                        "    &#441957" + this.stats.getZ() + " void",
                };
                lore.add("&7Type: " + typeString);
                lore.add("&c&l&a&b");
                lore.add("&cStats:");
                lore.addAll(Arrays.asList(lines));
            }

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
        is = NBTEditor.setNBTValue(is, "grapes.stats.type", this.type.name());
        is = NBTEditor.setNBTValue(is, "grapes.stats.physical", this.stats.getX());
        is = NBTEditor.setNBTValue(is, "grapes.stats.magical", this.stats.getY());
        is = NBTEditor.setNBTValue(is, "grapes.stats.void", this.stats.getZ());

        return is;
    }
}
