package me.trqhxrd.grapesrpg.api.objects.recipe;

import me.trqhxrd.grapesrpg.api.objects.item.GrapesItem;
import org.bukkit.inventory.CraftingInventory;
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

    /**
     * The result, which will be placed in the output slot as soon as the recipes shape.
     *
     * @see GrapesItem for more info.
     */
    private final ItemStack result;

    /**
     * This constructor creates a new GrapesRecipe with the ItemStack as its result.
     *
     * @param result The result for the crafting-recipe, that you're creating right now.
     */
    public GrapesRecipe(ItemStack result) {
        this.result = result;
    }

    /**
     * This constructor creates a new GrapesRecipe with the GrapesItem as its result.
     *
     * @param result The result for the crafting-recipe, that you're creating right now.
     */
    public GrapesRecipe(GrapesItem result) {
        this.result = result.build();
    }

    /**
     * Getter for a set of all available recipes.
     *
     * @return A set of all available recipes.
     */
    public static Set<GrapesRecipe> getRecipes() {
        return recipes;
    }

    /**
     * This method checks, if the matrix, which is given in the parameters, is valid for the recipe.
     * Most commonly you get the matrix from <code>((CraftingInventory) inv).getMatrix()</code>
     *
     * @param matrix An array of items with the length of 9. Index 0 is the top left corner. Index 1 is the middle of the top row and so on.
     * @return A Boolean. If it's true, the matrix is valid.
     */
    public abstract boolean check(ItemStack[] matrix);

    /**
     * The inventory is the inventory, which contains the crafting ingredients.
     *
     * @param inv The inventory, in which you want to check for the recipe.
     * @return A Boolean. If it's true, the matrix is valid.
     */
    public boolean check(CraftingInventory inv) {
        return this.check(inv.getMatrix());
    }

    /**
     * This method returns the result of the recipe.
     *
     * @return The result of the recipe.
     */
    public ItemStack getResult() {
        return result;
    }
}
