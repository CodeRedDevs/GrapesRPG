package me.trqhxrd.grapesrpg.api.objects.recipe;

import com.google.gson.JsonObject;
import me.trqhxrd.grapesrpg.Grapes;
import me.trqhxrd.grapesrpg.game.inventories.CraftingMenu;
import me.trqhxrd.grapesrpg.api.objects.item.GrapesItem;
import org.bukkit.inventory.ItemStack;

import java.util.HashSet;
import java.util.Set;

/**
 * This class represents a crafting-recipe.
 * It can be used by creating an object of this class.
 * The recipe works similar to a Builder. You can do <code>new GrapesRecipe("YourItem").setShape("aaa", "aba", "aaa").setIngredient('a', Material.DIRT)</code>
 *
 * @author Trqhxrd
 */
public abstract class GrapesRecipe {

    /**
     * A Set of all available recipes.
     */
    private static final Set<GrapesRecipe> recipes = new HashSet<>();
    private final Type type;

    /**
     * The result, which will be placed in the output slot as soon as the recipes shape.
     *
     * @see GrapesItem for more info.
     */
    private final GrapesItem result;

    /**
     * This constructor creates a new GrapesRecipe with the GrapesItem as its result.
     *
     * @param result The result for the crafting-recipe, that you're creating right now.
     * @param type   The Type of the Recipe. Can be shaped or shapeless. This needed for serialisation.
     */
    public GrapesRecipe(Type type, GrapesItem result) {
        this.type = type;
        this.result = result;
    }

    /**
     * Getter for a set of all available recipes.
     *
     * @return A set of all available recipes.
     */
    public static Set<GrapesRecipe> getRecipes() {
        return recipes;
    }

    public static GrapesRecipe fromString(String s) {
        GrapesRecipe r;
        if (Grapes.GSON.fromJson(s, JsonObject.class).get("type").getAsString().equalsIgnoreCase("SHAPED"))
            r = Grapes.GSON.fromJson(s, GrapesShapedRecipe.class);
        else r = Grapes.GSON.fromJson(s, GrapesShapelessRecipe.class);
        return r;
    }

    /**
     * This method checks, if the matrix, which is given in the parameters, is valid for the recipe.
     * Most commonly you get the matrix from <code>((CraftingInventory) inv).getMatrix()</code>
     *
     * @param matrix An array of items with the length of 9. Index 0 is the top left corner. Index 1 is the middle of the top row and so on.
     * @return A Boolean. If it's true, the matrix is valid.
     */
    public abstract boolean check(ItemStack[] matrix, ItemStack[] bindings);

    /**
     * The inventory is the inventory, which contains the crafting ingredients.
     *
     * @param inv The inventory, in which you want to check for the recipe.
     * @return A Boolean. If it's true, the matrix is valid.
     */
    public boolean check(CraftingMenu inv) {
        return this.check(inv.getMatrix(), inv.getBindings());
    }

    /**
     * A getter for the type of this recipe.
     *
     * @return The type of the recipe.
     */
    public Type getType() {
        return type;
    }

    /**
     * This method returns the result of the recipe.
     *
     * @return The result of the recipe.
     */
    public GrapesItem getResult() {
        return result;
    }

    @Override
    public String toString() {
        return "GrapesRecipe{" +
                "type=" + type +
                ", result=" + result +
                '}';
    }

    /**
     * The type of the recipe.
     * Can be SHAPED or SHAPELESS.
     * This is only used for deserialization.
     *
     * @author Trqhxrd
     */
    public enum Type {
        /**
         * This Type means, that the recipe will be shaped.
         */
        SHAPED,
        /**
         * This type means, that the recipe will be shapeless.
         */
        SHAPELESS
    }
}
