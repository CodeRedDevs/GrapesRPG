package me.trqhxrd.grapesrpg.api.objects.recipe;

import me.trqhxrd.grapesrpg.Grapes;
import me.trqhxrd.grapesrpg.api.attribute.Serializable;
import me.trqhxrd.grapesrpg.api.objects.item.GrapesItem;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

/**
 * This class represents a shapeless recipe.
 * Shapeless means, that, as long as all required items are in the grid, the recipe is valid.
 * No special shape needed.
 *
 * @author Trqhxrd
 */
public class GrapesShapelessRecipe extends GrapesRecipe implements Serializable<GrapesShapelessRecipe> {

    /**
     * This field stores the amount of crafting slots. (3 * 3 = 9)
     */
    public static final int SLOT_AMOUNT = 9;

    /**
     * This List contains all valid ingredients.
     * WARNING: DO NOT ADD MORE THAN 9 ENTRIES IN THIS LIST.
     */
    private final List<GrapesRecipeChoice> ingredients;

    /**
     * This constructor creates a new shapeless recipe, which has a certain item as it's result.
     *
     * @param result The result for the recipe.
     */
    public GrapesShapelessRecipe(GrapesItem result) {
        super(Type.SHAPELESS, result);
        this.ingredients = new ArrayList<>();
    }

    /**
     * This method is required by the interface {@link Serializable} but can't be forced, because it has to be static.
     * It is used for deserializing an Object from a String.
     *
     * @param serializedObject The Serialized Object, which you want to deserialize.
     * @return An Object of the Class, this method was written in. The serialized values got also copied into this object.
     */
    public static GrapesShapelessRecipe deserialize(String serializedObject) {
        return Grapes.GSON.fromJson(serializedObject, GrapesShapelessRecipe.class);
    }

    /**
     * This method adds an ingredient in a certain amount.
     *
     * @param choice The ingredient, which you want to add.
     * @param amount How often the ingredient should be added.
     * @return The recipe itself. This can be used for creating command-chains.
     */
    public GrapesShapelessRecipe addIngredient(GrapesRecipeChoice choice, int amount) {
        for (int i = 0; i < amount; i++) if (this.ingredients.size() < SLOT_AMOUNT) this.ingredients.add(choice);
        return this;
    }

    /**
     * This method adds the given choice one time to the ingredients.
     *
     * @param choice The ingredient, which you want to add.
     * @return The recipe itself. This can be used for creating command-chains.
     */
    public GrapesShapelessRecipe addIngredient(GrapesRecipeChoice choice) {
        return this.addIngredient(choice, 1);
    }

    /**
     * This method adds {@literal <amount>} RecipeChoices, which accept all the given materials.
     *
     * @param amount    The amount of items, that have to equal one of the materials.
     * @param materials The materials, which will be accepted.
     * @return The recipe itself. This can be used for creating command-chains.
     */
    public GrapesShapelessRecipe addIngredient(int amount, Material... materials) {
        return this.addIngredient(new GrapesRecipeChoice(materials), amount);
    }

    /**
     * This method adds {@literal <amount>} RecipeChoices, which accept all the given items.
     *
     * @param amount The amount of items, that have to equal one of the materials.
     * @param items  The items, which will be accepted.
     * @return The recipe itself. This can be used for creating command-chains.
     */
    public GrapesShapelessRecipe addIngredient(int amount, ItemStack... items) {
        return this.addIngredient(new GrapesRecipeChoice(items), amount);
    }

    /**
     * This method adds {@literal <amount>} RecipeChoices, which accept all the given items.
     *
     * @param amount The amount of items, that have to equal one of the materials.
     * @param items  The items, which will be accepted.
     * @return The recipe itself. This can be used for creating command-chains.
     */
    public GrapesShapelessRecipe addIngredient(int amount, GrapesItem... items) {
        return this.addIngredient(new GrapesRecipeChoice(items), amount);
    }

    /**
     * This method adds one RecipeChoice, which accept all the given materials.
     *
     * @param materials The materials, which will be accepted.
     * @return The recipe itself. This can be used for creating command-chains.
     */
    public GrapesShapelessRecipe addIngredient(Material... materials) {
        return this.addIngredient(1, materials);
    }

    /**
     * This method adds one RecipeChoice, which accept all the given items.
     *
     * @param items The items, which will be accepted.
     * @return The recipe itself. This can be used for creating command-chains.
     */
    public GrapesShapelessRecipe addIngredient(ItemStack... items) {
        return this.addIngredient(1, items);
    }

    /**
     * This method adds one RecipeChoice, which accept all the given items.
     *
     * @param items The items, which will be accepted.
     * @return The recipe itself. This can be used for creating command-chains.
     */
    public GrapesShapelessRecipe addIngredient(GrapesItem... items) {
        return this.addIngredient(1, items);
    }

    /**
     * This method checks, if the matrix, which is given in the parameters, is valid for the recipe.
     * Most commonly you get the matrix from <code>((CraftingInventory) inv).getMatrix()</code>
     *
     * @param matrix An array of items with the length of 9. Index 0 is the top left corner. Index 1 is the middle of the top row and so on.
     * @return A Boolean. If it's true, the matrix is valid.
     */
    @Override
    public boolean check(ItemStack[] matrix, ItemStack[] bindings) {
        for (ItemStack is : bindings) if (is != null) return false;

        this.ingredients.removeIf(test -> this.ingredients.indexOf(test) >= 9);
        List<GrapesRecipeChoice> clone = new ArrayList<>(ingredients);
        for (ItemStack itemStack : matrix) {
            for (GrapesRecipeChoice ingredient : ingredients) {
                if (itemStack != null && ingredient.check(itemStack)) {
                    clone.remove(ingredient);
                    break;
                }
            }
        }
        return clone.isEmpty();
    }

    /**
     * This method serializes an Object (t) into a String.
     *
     * @param grapesShapelessRecipe The Object, which you want to serialize.
     * @return The String containing all data about the object.
     */
    @Override
    public String serialize(GrapesShapelessRecipe grapesShapelessRecipe) {
        return Grapes.GSON.toJson(grapesShapelessRecipe);
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
     * A getter for the ingredients list.
     * WARNING: If you add RecipeChoices manually, don't add more than 9.
     *
     * @return A List of all ingredients.
     */
    public List<GrapesRecipeChoice> getIngredients() {
        return ingredients;
    }
}
