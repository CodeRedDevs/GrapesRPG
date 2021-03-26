package me.trqhxrd.grapesrpg.game.mechanics.alchemy;

import me.trqhxrd.grapesrpg.game.objects.block.CrucibleBlock;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * This class represents an alchemy recipe.
 *
 * @author Trqhxrd
 */
public class AlchemyRecipe {

    /**
     * This HashSet contains all recipes and is used for checking if a recipe is valid.
     */
    private static final Set<AlchemyRecipe> recipes = new HashSet<>();
    /**
     * This List contains all required items for this recipe.
     */
    private final List<ItemStack> ingredients;
    /**
     * This ItemStack is the result of the recipe.
     */
    private final ItemStack result;
    /**
     * This ItemStack triggers the recipe to be crafted.
     */
    private final ItemStack catalyst;
    /**
     * This integer contains the amount of water, that is required to craft the item.
     */
    private final int waterLevelRequired;

    /**
     * This constructor creates a new recipe and registers it.
     *
     * @param catalyst           This item is required to finish the recipe.
     * @param result             The item is the result of the recipe. It will be dropped once a catalyst got added.
     * @param waterLevelRequired This integer sets the amount of water, that is required to finish the recipe.
     * @param ingredients        This list contains all items, that are required for this recipe. Every item has to have the amount of 1.
     */
    public AlchemyRecipe(ItemStack catalyst, ItemStack result, int waterLevelRequired, List<ItemStack> ingredients) {
        this.ingredients = ingredients;
        this.result = result;
        this.catalyst = catalyst;
        this.waterLevelRequired = waterLevelRequired;

        recipes.add(this);
    }

    /**
     * This constructor creates a new recipe and registers it.
     *
     * @param catalyst           This item is required to finish the recipe.
     * @param result             The item is the result of the recipe. It will be dropped once a catalyst got added.
     * @param waterLevelRequired This integer sets the amount of water, that is required to finish the recipe.
     */
    public AlchemyRecipe(ItemStack catalyst, ItemStack result, int waterLevelRequired) {
        this(catalyst, result, waterLevelRequired, new ArrayList<>());
    }

    /**
     * This constructor creates a new recipe and registers it.
     *
     * @param catalyst This item is required to finish the recipe.
     * @param result   The item is the result of the recipe. It will be dropped once a catalyst got added.
     */
    public AlchemyRecipe(ItemStack catalyst, ItemStack result) {
        this(catalyst, result, 1);
    }

    /**
     * Getter for a set of all recipes.
     *
     * @return A list of all recipes.
     */
    public static Set<AlchemyRecipe> getRecipes() {
        return recipes;
    }

    /**
     * Getter for the amount of water, that is required to finish this recipe.
     *
     * @return The amount of water required for this recipe.
     */
    public int getWaterLevelRequired() {
        return waterLevelRequired;
    }

    /**
     * This method adds another item to the ingredient list.
     *
     * @param is     The item, that should be added.
     * @param amount The amount of that item.
     */
    public void addIngredient(ItemStack is, int amount) {
        is.setAmount(1);
        for (int i = 0; i < amount; i++) this.ingredients.add(is);
    }

    /**
     * This method checks, if the crucible given meets all the requirements to complete this recipe.
     *
     * @param crucible The crucible, that should be checked.
     * @return true, if the crucible meets all the requirements. Otherwise false.
     */
    public boolean checkItems(CrucibleBlock crucible) {
        return this.checkItems(crucible.getItems());
    }

    /**
     * This method checks, if the crucible given meets all the requirements to complete this recipe.
     *
     * @param items The list of items, that should be checked.
     * @return true, if the crucible meets all the requirements. Otherwise false.
     */
    public boolean checkItems(List<ItemStack> items) {
        boolean[] correct = new boolean[this.ingredients.size()];

        List<ItemStack> copy = new ArrayList<>(items);

        for (int i = 0; i < this.ingredients.size(); i++) {
            ItemStack ingredient = this.ingredients.get(i);
            boolean found = false;

            for (ItemStack itemStack : copy) {
                if (ingredient.isSimilar(itemStack)) {
                    found = true;
                    correct[i] = true;
                    break;
                }
            }

            if (found) copy.remove(ingredient);
        }

        boolean done = true;
        for (boolean b : correct) {
            if (!b) {
                done = false;
                break;
            }
        }
        return done;
    }

    /**
     * This method removes all the items of this recipe from the crucible in the method arguments.
     *
     * @param crucible The crucible, that should get the items removed.
     */
    public void removeItems(CrucibleBlock crucible) {
        List<ItemStack> copy = new ArrayList<>(crucible.getItems());

        for (ItemStack ingredient : this.ingredients) {
            int removeIndex = -1;

            for (int i = 0; i < copy.size(); i++) {
                ItemStack itemStack = copy.get(i);
                if (ingredient.isSimilar(itemStack)) {
                    removeIndex = i;
                    break;
                }
            }

            if (removeIndex >= 0) copy.remove(removeIndex);
        }
        crucible.setItems(copy);
    }

    /**
     * Getter for the list of ingredients.
     *
     * @return The list of ingredients.
     */
    public List<ItemStack> getIngredients() {
        return ingredients;
    }

    /**
     * Getter for the recipes result.
     *
     * @return The recipes result.
     */
    public ItemStack getResult() {
        return result;
    }

    /**
     * Getter for the recipes catalyst item.
     *
     * @return The recipes catalyst item.
     */
    public ItemStack getCatalyst() {
        return catalyst;
    }
}
