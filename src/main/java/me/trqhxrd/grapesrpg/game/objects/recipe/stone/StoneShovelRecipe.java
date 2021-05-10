package me.trqhxrd.grapesrpg.game.objects.recipe.stone;

import me.trqhxrd.grapesrpg.api.objects.recipe.GrapesShapedRecipe;
import me.trqhxrd.grapesrpg.game.objects.item.PlantFiber;
import me.trqhxrd.grapesrpg.game.objects.item.stone.StoneShovel;
import org.bukkit.Material;

public class StoneShovelRecipe extends GrapesShapedRecipe {
    public StoneShovelRecipe() {
        super(new StoneShovel());
        super.setShape("cac", "cbc", "cbc");
        super.setIngredient('a', Material.COBBLESTONE);
        super.setIngredient('b',Material.STICK);
        super.addBinding(12,new PlantFiber());
    }
}
