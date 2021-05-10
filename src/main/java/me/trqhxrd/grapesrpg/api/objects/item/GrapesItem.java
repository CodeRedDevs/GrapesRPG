package me.trqhxrd.grapesrpg.api.objects.item;

import com.google.common.base.Preconditions;
import me.trqhxrd.color.Colors;
import me.trqhxrd.grapesrpg.Grapes;
import me.trqhxrd.grapesrpg.api.attribute.Serializable;
import me.trqhxrd.grapesrpg.api.objects.item.clickaction.ClickAction;
import me.trqhxrd.grapesrpg.api.utils.Builder;
import me.trqhxrd.grapesrpg.api.utils.group.Group2;
import me.trqhxrd.grapesrpg.api.utils.group.Group3;
import me.trqhxrd.grapesrpg.api.utils.items.MapRendererImage;
import me.trqhxrd.grapesrpg.api.utils.items.nbt.NBTEditor;
import me.trqhxrd.grapesrpg.api.utils.items.nbt.NBTReader;
import me.trqhxrd.grapesrpg.game.config.MapData;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.inventory.meta.MapMeta;
import org.bukkit.map.MapView;

import java.awt.*;
import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.*;

/**
 * This Class represents a serializable item from the GrapesAPI.
 * If you want to get an ItemStack from this GrapesItem, use {@link GrapesItem#build()}.
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
    private final Map<String, Object> nbt;
    /**
     * This action will be executed, if you right click the item.
     */
    private transient ClickAction clickAction;
    /**
     * This field contains all information about the items protection or damage.
     * This field is a {@link Group3}.
     * The physical damage / protection is stored in a field called {@code x}.
     * The magical damage / protection is stored in a field called {@code y}.
     * The void damage / protection is stored in a field called {@code z}.
     */
    private Group3<Integer, Integer, Integer> stats;
    /**
     * This field contains all data about the items durability.
     * Field X stores the items current durability.
     * Field Y stores the items maximum durability.
     * If the max durability is -1, the item is unbreakable.
     */
    private Group2<Integer, Integer> durability;
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
     * If you use this constructor, you have to set all values individually.
     * This constructor is generally unsafe to use.
     * DO NOT USE IT UNLESS YOU KNOW WHAT YOU'RE DOING!
     */
    private GrapesItem() {
        this.nbt = new HashMap<>();
    }

    /**
     * A basic constructor to create a new GrapesItem.
     *
     * @param id       The id of the new item.
     * @param material The material of the new item.
     */
    public GrapesItem(int id, Material material) {
        this(id, material, null, 1, Rarity.DEFAULT_RARITY, DEFAULT_STATS, ItemType.MELEE, new Group2<>(-1, -1), new HashMap<>());
    }

    /**
     * A basic constructor to create a new GrapesItem.
     *
     * @param id            The id of the new item.
     * @param material      The material of the new item.
     * @param name          The name of the new item. Can be left empty in which case the name of the item will be set to it's material name in the players language.
     * @param maxDurability The Maximum durability of the item. sIf the value is set to -1 the item is unbreakable.
     */
    public GrapesItem(int id, Material material, String name, int maxDurability) {
        this(id, material, name, 1, Rarity.DEFAULT_RARITY, DEFAULT_STATS, ItemType.MELEE, new Group2<>(maxDurability, maxDurability), new HashMap<>());
    }

    /**
     * A basic constructor to create a new GrapesItem.
     *
     * @param id       The id of the new item.
     * @param material The material of the new item.
     * @param name     The name of the new item. Can be left empty in which case the name of the item will be set to it's material name in the players language.
     */
    public GrapesItem(int id, Material material, String name) {
        this(id, material, name, 1, Rarity.DEFAULT_RARITY, DEFAULT_STATS, ItemType.MELEE, new Group2<>(-1, -1), new HashMap<>());
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
     * @param maxDurability  The Maximum durability of the item. sIf the value is set to -1 the item is unbreakable.
     */
    public GrapesItem(int id, Material material, String name, Rarity rarity, int physicalDamage, int magicalDamage, int voidDamage, int maxDurability) {
        this(id, material, name, 1, rarity, physicalDamage, magicalDamage, voidDamage, ItemType.MELEE, maxDurability, new HashMap<>());
    }

    /**
     * A basic constructor to create a new GrapesItem.
     *
     * @param id            The id of the new item.
     * @param material      The material of the new item.
     * @param name          The name of the new item. Can be left empty in which case the name of the item will be set to it's material name in the players language.
     * @param rarity        The rarity of the new item.
     * @param nbtEntries    The NBT-Values, that will be set to the item.
     * @param maxDurability The Maximum durability of the item. sIf the value is set to -1 the item is unbreakable.
     */
    @SafeVarargs
    public GrapesItem(int id, Material material, String name, Rarity rarity, int maxDurability, Group2<String, Object>... nbtEntries) {
        this(id, material, name, 1, rarity, DEFAULT_STATS, ItemType.MELEE, new Group2<>(maxDurability, maxDurability), new HashMap<>());
        for (Group2<String, Object> entry : nbtEntries) this.nbt.put(entry.getX(), entry.getY());
    }

    /**
     * A constructor, which supports the items amount to be given.
     *
     * @param id            The items id.
     * @param material      The Material of the item.
     * @param name          The name of the item. If black, will be set to the materials name.
     * @param amount        The amount of the item. (e.g. 64 = a stack).
     * @param rarity        The rarity of the item.
     * @param maxDurability The Maximum durability of the item. sIf the value is set to -1 the item is unbreakable.
     */
    public GrapesItem(int id, Material material, String name, int amount, Rarity rarity, int maxDurability) {
        this(id, material, name, amount, rarity, DEFAULT_STATS, ItemType.MELEE, new Group2<>(maxDurability, maxDurability), new HashMap<>());
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
     * @param maxDurability This is the items maximum durability. If set to -1 the item is unbreakable.
     */
    public GrapesItem(int id, Material material, String name, int amount, Rarity rarity, int statsPhysical, int statsMagical, int statsVoid,
                      ItemType type, int maxDurability, Map<String, Object> nbt) {
        this(id, material, name, amount, rarity, new Group3<>(statsPhysical, statsMagical, statsVoid), type, new Group2<>(maxDurability, maxDurability), nbt);
    }

    /**
     * A constructor, which supports the items amount to be given.
     *
     * @param id         The items id.
     * @param material   The Material of the item.
     * @param name       The name of the item. If black, will be set to the materials name.
     * @param amount     The amount of the item. (e.g. 64 = a stack).
     * @param rarity     The rarity of the item.
     * @param nbt        The custom nbt-tags, you want to set.
     * @param stats      The  damage / protection, that the item deals on hit / protects against on impact.
     * @param type       This defines the type of the item.
     * @param durability The durability of the item. The first value in the group is the items current durability. The second value is the items maximum durability. If the maximum durability is -1 the item is unbreakable.
     */
    public GrapesItem(int id, Material material, String name, int amount, Rarity rarity, Group3<Integer, Integer, Integer> stats,
                      ItemType type, Group2<Integer, Integer> durability, Map<String, Object> nbt) {
        this.nbt = (nbt != null ? nbt : new HashMap<>());
        this.id = id;
        this.material = material;
        this.name = name;
        this.amount = amount;
        this.rarity = rarity;
        this.stats = stats;
        this.type = type;
        this.durability = durability;
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
                GrapesItem item = new GrapesItem();
                item.setID(id);
                item.setMaterial(is.getType());
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

                item.getNbt().remove("display.Name");
                item.getNbt().remove("display.Lore");

                if (item.getNbt().containsKey("grapes.name")) item.setName((String) item.getNbt().get("grapes.name"));
                else item.setName(null);
                if (item.getNbt().containsKey("grapes.rarity")) item.setRarity(Rarity.getById(((int) item.getNbt().get("grapes.rarity"))));
                else item.setRarity(Rarity.DEFAULT_RARITY);

                Group3<Integer, Integer, Integer> statsNew = new Group3<>(DEFAULT_STATS);
                if (item.getNbt().containsKey("grapes.stats.physical")) statsNew.setX((Integer) item.getNbt().get("grapes.stats.physical"));
                if (item.getNbt().containsKey("grapes.stats.magical")) statsNew.setY((Integer) item.getNbt().get("grapes.stats.magical"));
                if (item.getNbt().containsKey("grapes.stats.void")) statsNew.setZ((Integer) item.getNbt().get("grapes.stats.void"));
                item.setStats(statsNew);

                Group2<Integer, Integer> durability = new Group2<>(-1, -1);
                if (item.getNbt().containsKey("grapes.durability.current")) durability.setX(((Integer) item.getNbt().get("grapes.durability.current")));
                if (item.getNbt().containsKey("grapes.durability.max")) durability.setY(((Integer) item.getNbt().get("grapes.durability.max")));

                item.setDurability(durability);

                ItemType typeNew = ItemType.DEFAULT_TYPE;
                if (item.getNbt().containsKey("grapes.stats.type")) typeNew = ItemType.valueOf(((String) item.getNbt().get("grapes.stats.type")));
                item.setType(typeNew);

                if (item.getRarity() == null) item.setRarity(Rarity.DEFAULT_RARITY);

                // Removing all NBT-Values, which are stored in variables in an object of this class.
                /*Set<String> remove = new HashSet<>();
                for (String s : item.getNbt().keySet()) if (s.startsWith("grapes.")) remove.add(s);
                for (String s : remove) item.getNbt().remove(s);*/

                // Clone the click-action from the reference item.
                item.setClickAction(ClickAction.getClickAction(item.getID()));

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
     * This method updates an items lore, name and other stuff.
     *
     * @param is The item, which you want to update.
     * @return The Updated item.
     */
    public static ItemStack update(ItemStack is) {
        GrapesItem i = GrapesItem.fromItemStack(is);
        if (i != null) return i.build();
        else return is;
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
    public GrapesItem setRarity(Rarity rarity) {
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
    public GrapesItem addNBT(String path, Object value) {
        this.nbt.put(path, value);
        return this;
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
    public Map<String, Object> getNbt() {
        return nbt;
    }

    /**
     * Getter for the items id.
     *
     * @return The items id.
     */
    public int getID() {
        return id;
    }

    /**
     * Setter for the items id.
     *
     * @param id The new id.
     * @return The GrapesItem. Used for creating command chains.
     */
    public GrapesItem setID(int id) {
        this.id = id;
        return this;
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
     * @return The GrapesItem. Used for creating command chains.
     */
    public GrapesItem setMaterial(Material material) {
        this.material = material;
        return this;
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
     * This method returns a filled map with the data of this GrapesItem and the image from the url.
     *
     * @param uri The url, at which the image is located.
     * @return The finished map.
     */
    public ItemStack getAsMap(URI uri) {
        MapRendererImage renderer = MapRendererImage.getRenderer(uri);
        this.material = Material.FILLED_MAP;
        ItemStack build = this.build();
        MapMeta meta = (MapMeta) build.getItemMeta();
        MapView view = Bukkit.createMap(Bukkit.getWorlds().get(0));
        if (renderer != null) {
            view.getRenderers().clear();
            view.addRenderer(renderer);
        }

        if (meta != null) {
            meta.setMapView(view);
            build.setItemMeta(meta);
        }

        if (MapData.getInstance() == null) MapData.init();
        MapData.saveMapData(view.getId(), uri.toString());

        return build;
    }

    /**
     * This method returns a filled map with the data of this GrapesItem and the image from the url.
     *
     * @param file The file, where the image is located at the disk.
     * @return The finished map.
     */
    public ItemStack getAsMap(File file) {
        return this.getAsMap(file.toURI());
    }

    /**
     * This method returns a filled map with the data of this GrapesItem and the image from the url.
     *
     * @param url The url, at which the image is located.
     * @return The finished map.
     */
    public ItemStack getAsMap(String url) {
        try {
            return this.getAsMap(new URI(url));
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        return null;
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
     * If the item is unbreakable, this method will return true.
     *
     * @return true if the item is unbreakable.
     */
    public boolean isUnbreakable() {
        return this.durability.getY() <= -1;
    }

    /**
     * Getter for the items durability.
     *
     * @return The items durability.
     */
    public Group2<Integer, Integer> getDurability() {
        return durability;
    }

    /**
     * This sets the items maximum durability and set fills the items current durability to the maximum.
     *
     * @param maxDurability The new maximum durability of the item.
     * @return The GrapesItem. Used for creating command chains.
     */
    public GrapesItem setDurability(int maxDurability) {
        return this.setDurability(maxDurability, maxDurability);
    }

    /**
     * Setter for the items durability.
     *
     * @param durability The items new durability
     * @return The GrapesItem. Used for creating command chains.
     */
    public GrapesItem setDurability(Group2<Integer, Integer> durability) {
        Preconditions.checkArgument(durability.getX() <= durability.getY(), "An items current durability (" +
                durability.getX() + ") can't be bigger than the items maximum durability (" + durability.getY() + ").");
        this.durability = durability;
        return this;
    }

    /**
     * Getter for the items current durability.
     *
     * @return The items current durability.
     */
    public int getCurrentDurability() {
        return durability.getX();
    }

    /**
     * Setter for the current durability of the item.
     *
     * @param currentDurability The current durability of the item.
     * @return The GrapesItem. Used for creating command chains.
     */
    public GrapesItem setCurrentDurability(int currentDurability) {
        this.durability.setX(currentDurability);
        return this;
    }

    /**
     * Getter for the items maximum durability.
     *
     * @return The items maximum durability.
     */
    public int getMaxDurability() {
        return durability.getY();
    }

    /**
     * Setter for the items durability.
     *
     * @param currentDurability The items new max durability.
     * @param maxDurability     The items new maximum durability.
     * @return The GrapesItem. Used for creating command chains.
     */
    public GrapesItem setDurability(int currentDurability, int maxDurability) {
        return this.setDurability(new Group2<>(currentDurability, maxDurability));
    }

    /**
     * Getter for the click-action of the item.
     *
     * @return The click-action of the item.
     */
    public ClickAction getClickAction() {
        return clickAction;
    }

    /**
     * Setter for the click action of the item.
     * If set to null the default-action will be applied.
     *
     * @param clickAction The new click-action of the item.
     * @return The GrapesItem. Used for creating command chains.
     */
    public GrapesItem setClickAction(ClickAction clickAction) {
        if (clickAction != null) {
            this.clickAction = clickAction;
            ClickAction.addClickAction(this.id, clickAction, false);
        } else this.clickAction = ClickAction.DEFAULT;
        return this;
    }

    /**
     * This method simply reduces the items durability by one.
     *
     * @return The item itself. If you want to get an itemStack use {@code yourItem.use().build}!
     */
    public GrapesItem use() {
        if (this.getCurrentDurability() > 0 && this.getMaxDurability() != -1) this.setCurrentDurability(this.getCurrentDurability() - 1);
        return this;
    }

    /**
     * This method serializes the Object, from which it will be executed.
     *
     * @return The serialized object.
     */
    @Override
    public String serialize() {
        return Grapes.GSON.toJson(this);
    }

    /**
     * This method can build an object of the type T.
     * You have to create your own Class, which implements the Builder.
     *
     * @return The built object, which was created by the builder.
     */
    @Override
    public ItemStack build() {
        if (this.material == Material.AIR) return null;

        ItemStack is = new ItemStack(this.material);

        is.setAmount(this.amount);

        ItemMeta meta = is.getItemMeta();
        if (meta != null) {

            meta.addItemFlags(ItemFlag.values());

            if (this.name != null && !this.name.isBlank() && !this.name.isEmpty())
                meta.setDisplayName(Colors.translateColors("&" + this.rarity.getColor() + this.name));

            if (meta instanceof LeatherArmorMeta && color != null) ((LeatherArmorMeta) meta).setColor(org.bukkit.Color.fromRGB(color[0], color[1], color[2]));

            List<String> lore = new ArrayList<>();

            //Add item damage / protection
            if (!this.stats.equals(DEFAULT_STATS)) {
                String typeString = (this.type == ItemType.ARMOR) ? "Protection" : "Damage";
                String[] lines = new String[]{
                        "&#9d9fa3Physical " + typeString + ": " + this.stats.getX(),
                        "&#ae55d4Magical " + typeString + ": " + this.stats.getY(),
                        "&#441957Void " + typeString + ": " + this.stats.getZ(),
                };
                lore.addAll(Arrays.asList(lines));
            }

            if (durability != null) {
                if (durability.getY() > 0) {
                    char color = 'a';
                    if (((double) durability.getY()) / 100. * 25. > durability.getX()) color = 'e';
                    if (((double) durability.getY()) / 100. * 15. > durability.getX()) color = 'c';
                    if (((double) durability.getY()) / 100. * 5. > durability.getX()) color = '4';

                    String line;
                    if (durability.getX() != 0) line = "&" + color + "&lDurability: " + durability.getX() + " / " + durability.getY();
                    else line = "&c&lBROKEN";

                    lore.add("");
                    lore.add(line);
                }
            }

            //Add Rarity to item lore
            if (rarity != null) {
                lore.add("");
                lore.add(this.rarity.getFormattedName());
            }

            Colors.translateColors(lore);

            meta.setLore(lore);
            is.setItemMeta(meta);
        }

        for (String path : nbt.keySet()) is = NBTEditor.setNBTValue(is, path, nbt.get(path));

        is = NBTEditor.setNBTValue(is, "grapes.name", this.name);
        is = NBTEditor.setNBTValue(is, "grapes.id", this.id);
        is = NBTEditor.setNBTValue(is, "grapes.rarity", this.rarity != null ? this.rarity.getId() : Rarity.DEFAULT_RARITY.getId());
        is = NBTEditor.setNBTValue(is, "grapes.stats.type", this.type.name());
        is = NBTEditor.setNBTValue(is, "grapes.stats.physical", this.stats.getX());
        is = NBTEditor.setNBTValue(is, "grapes.stats.magical", this.stats.getY());
        is = NBTEditor.setNBTValue(is, "grapes.stats.void", this.stats.getZ());
        is = NBTEditor.setNBTValue(is, "grapes.durability.current", this.durability != null ? this.durability.getX() : 0);
        is = NBTEditor.setNBTValue(is, "grapes.durability.max", this.durability != null ? this.durability.getY() : 0);

        return is;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof GrapesItem)) return false;
        GrapesItem item = (GrapesItem) o;
        return id == item.id && amount == item.amount && Objects.equals(nbt, item.nbt) && Objects.equals(clickAction, item.clickAction) && Objects.equals(stats, item.stats) && Objects.equals(durability, item.durability) && type == item.type && rarity == item.rarity && material == item.material && Objects.equals(name, item.name) && Arrays.equals(color, item.color);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(nbt, clickAction, stats, durability, type, rarity, id, material, name, amount);
        result = 31 * result + Arrays.hashCode(color);
        return result;
    }
}
