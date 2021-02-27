package me.trqhxrd.grapesrpg.api.objects.recipe;

import me.trqhxrd.grapesrpg.Grapes;
import me.trqhxrd.grapesrpg.api.attribute.Serializable;
import me.trqhxrd.grapesrpg.api.objects.item.GrapesItem;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * This class represents all possible items, which you can put in a slot for a recipe.
 * This class works as follows:
 * <p>
 * You can set a {@link Mode}, that says, which attributes should be tested.
 * If the Mode is {@link Mode#MATERIAL} it will only check for the items {@link Material} to be equal.
 * For example: You put a Diamond called "test" in the crafting grid. It accepts, because the criteria you set was DIAMOND.
 * <p>
 * The second Mode, which you can choose is {@link Mode#NATIVE}.
 * That Mode checks, if the {@link ItemStack}s are similar using the {@link ItemStack#isSimilar(ItemStack)}-method.
 * For example: You put a Diamond called "test" in the crafting grid. It declines, because you said it is only allowed to accept diamonds with no name.
 * <p>
 * The third and last Mode is {@link Mode#ITEM}, which first makes a GrapesItem from the ItemStack using the {@link GrapesItem#fromItemStack(ItemStack)}-method.
 * That GrapesItem will be compared to all items in the Set of possible matches. If one of them matches the item, the recipe is valid.
 *
 * @author Trqhxrd.
 */
public class GrapesRecipeChoice implements Serializable<GrapesRecipeChoice> {

    /**
     * The mode says, from which of these Sets should be tested.
     * If set to {@link Mode#MATERIAL} it will check from the Set {@link GrapesRecipeChoice#materials}.
     * If set to {@link Mode#NATIVE} it will check from the Set {@link GrapesRecipeChoice#itemStacks}.
     * If set to {@link Mode#ITEM} it will check from the Set {@link GrapesRecipeChoice#grapesItems}.
     */
    private final Mode mode;

    /**
     * A Set of materials.
     * This set contains all valid materials.
     * This Set will only be chosen for comparing items, if the Mode {@link Mode#MATERIAL} is active.
     */
    private final Set<Material> materials;

    /**
     * A Set of ItemStacks.
     * This set contains all matching ItemStacks.
     * This Set will only be chosen for comparing items, if the Mode {@link Mode#NATIVE} is active.
     */
    private final Set<ItemStack> itemStacks;

    /**
     * A Set of GrapesItems.
     * This set contains all matching GrapesItems.
     * This Set will only be chosen for comparing items, if the Mode {@link Mode#ITEM} is active.
     */
    private final Set<GrapesItem> grapesItems;

    /**
     * This constructor creates a new GrapesRecipeChoice with empty sets.
     * It only sets the {@link Mode}, that says, from which Set the ingredients should be checked.
     *
     * @param mode The Mode. Can Be {@link Mode#MATERIAL}, {@link Mode#NATIVE} or {@link Mode#ITEM}.
     */
    public GrapesRecipeChoice(Mode mode) {
        this.materials = new HashSet<>();
        this.itemStacks = new HashSet<>();
        this.grapesItems = new HashSet<>();
        this.mode = mode;
    }

    /**
     * Creates a new instance of the GrapesRecipeChoice-Class with the Mode set to {@link Mode#MATERIAL}.
     *
     * @param materials These are the materials, which can later be used for crafting the item.
     */
    public GrapesRecipeChoice(Material... materials) {
        this(Mode.MATERIAL);
        this.materials.addAll(Arrays.asList(materials));
    }

    /**
     * Creates a new instance of the GrapesRecipeChoice-Class with the Mode set to {@link Mode#NATIVE}.
     *
     * @param itemStacks These are the items, which can later be used for crafting the item.
     */
    public GrapesRecipeChoice(ItemStack... itemStacks) {
        this(Mode.NATIVE);
        this.itemStacks.addAll(Arrays.asList(itemStacks));
    }

    /**
     * Creates a new instance of the GrapesRecipeChoice-Class with the Mode set to {@link Mode#ITEM}.
     *
     * @param items These are the items, which can later be used for crafting the item.
     */
    public GrapesRecipeChoice(GrapesItem... items) {
        this(Mode.ITEM);
        this.grapesItems.addAll(Arrays.asList(items));
    }

    /**
     * This method is required by the interface {@link Serializable} but can't be forced, because it has to be static.
     * It is used for deserializing an Object from a String.
     *
     * @param serializedObject The Serialized Object, which you want to deserialize.
     * @return An Object of the Class, this method was written in. The serialized values got also copied into this object.
     */
    public static GrapesRecipeChoice deserialize(String serializedObject) {
        return Grapes.GSON.fromJson(serializedObject, GrapesRecipeChoice.class);
    }

    /**
     * This method returns true, if the mode is set to {@link Mode#MATERIAL} and the given Material matches one of the materials in the set.
     *
     * @param m The Material, for which you want to check, if it is valid.
     * @return true if the Material can be used for crafting.
     */
    public boolean checkInternal(Material m) {
        if (m == null) return false;
        if (mode == Mode.MATERIAL) return this.materials.contains(m);
        return false;
    }

    /**
     * This method returns true, if the mode is set to {@link Mode#NATIVE} and the given ItemStack matches one of the ItemStacks in the set.
     *
     * @param is The ItemStack, for which you want to check, if it is valid.
     * @return true if the ItemStack can be used for crafting.
     */
    public boolean checkInternal(ItemStack is) {
        if (is == null) return false;
        if (mode == Mode.NATIVE)
            for (ItemStack item : this.itemStacks)
                if (item.isSimilar(is) && item.getAmount() <= is.getAmount()) return true;
        return false;
    }

    /**
     * This method returns true, if the mode is set to {@link Mode#ITEM} and the given GrapesItem matches one of the GrapesItems in the set.
     *
     * @param item The GrapesItem, for which you want to check, if it is valid.
     * @return true if the GrapesItem can be used for crafting.
     */
    public boolean checkInternal(GrapesItem item) {
        if (mode == Mode.ITEM) {
            if (item == null) return false;
            for (GrapesItem grapesItem : this.grapesItems)
                if (grapesItem.getID() == item.getID())
                    if (grapesItem.getNbt().equals(item.getNbt()))
                        return true;
        }
        return false;
    }

    /**
     * This Method calls {@link GrapesRecipeChoice#checkInternal(Material)},
     * {@link GrapesRecipeChoice#checkInternal(ItemStack)} and
     * {@link GrapesRecipeChoice#checkInternal(GrapesItem)}.
     * If one of these methods returns true, this method will return true and the recipe is valid.
     *
     * @param item The item, for which you want to check, if it is valid.
     * @return true if the item is valid. Otherwise false.
     */
    public boolean check(ItemStack item) {
        if (item == null) return false;
        boolean valid = true;
        switch (mode) {
            case ITEM:
                if (!checkInternal(GrapesItem.fromItemStack(item.clone()))) valid = false;
                break;
            case NATIVE:
                if (!checkInternal(item.clone())) valid = false;
                break;
            case MATERIAL:
                if (!checkInternal(item.clone().getType())) valid = false;
                break;
        }
        return valid;
    }

    /**
     * Returns the set of valid Materials.
     * Can be used, if you want to check manually.
     *
     * @return A Set of Materials.
     */
    public Set<Material> getMaterials() {
        return materials;
    }

    /**
     * Returns the set of valid ItemStacks.
     * Can be used, if you want to check manually.
     *
     * @return A Set of ItemStacks.
     */
    public Set<ItemStack> getItemStacks() {
        return itemStacks;
    }

    /**
     * Returns the set of valid GrapesItems.
     * Can be used, if you want to check manually.
     *
     * @return A Set of GrapesItems.
     */
    public Set<GrapesItem> getGrapesItems() {
        return grapesItems;
    }

    /**
     * Returns the Ingredient's Mode.
     * Can be used, if you want to check manually.
     *
     * @return The Ingredient's Mode.
     */
    public Mode getMode() {
        return mode;
    }

    /**
     * This method serializes an Object (t) into a String.
     *
     * @param grapesRecipeChoice The Object, which you want to serialize.
     * @return The String containing all data about the object.
     */
    @Override
    public String serialize(GrapesRecipeChoice grapesRecipeChoice) {
        return Grapes.GSON.toJson(grapesRecipeChoice);
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
     * Basic toString method.
     *
     * @return The Object serialized in a String.
     */
    @Override
    public String toString() {
        return "GrapesRecipeChoice{" +
                "mode=" + mode +
                ", materials=" + materials +
                ", itemStacks=" + itemStacks +
                ", grapesItems=" + grapesItems +
                '}';
    }

    /**
     * This enum represents the different modes, a Ingredient can have.
     *
     * @author Trqhxrd.
     */
    public enum Mode {

        /**
         * This Mode makes the Ingredient only check for the items Material.
         */
        MATERIAL,

        /**
         * This Mode makes the Ingredient check, if the items are similar using {@link ItemStack#isSimilar(ItemStack)}.
         */
        NATIVE,

        /**
         * This Mode makes a GrapesItem from the ItemStack using {@link GrapesItem#fromItemStack(ItemStack)} and compares it to another GrapesItem.
         */
        ITEM
    }
}
