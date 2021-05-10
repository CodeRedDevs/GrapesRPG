package me.trqhxrd.grapesrpg.game.objects.recipe.stone;

import me.trqhxrd.grapesrpg.api.objects.recipe.GrapesShapedRecipe;
import me.trqhxrd.grapesrpg.game.objects.item.PlantFiber;
import me.trqhxrd.grapesrpg.game.objects.item.stone.StoneAxe;
import org.bukkit.Material;

public class StoneAxeRecipe extends GrapesShapedRecipe {
    public StoneAxeRecipe() {
        super(new StoneAxe());
        super.setShape("aac", "abc", "cbc");
        super.setIngredient('a', Material.COBBLESTONE);
        super.setIngredient('b',Material.STICK);
        super.addBinding(12,new PlantFiber());
    }
}
