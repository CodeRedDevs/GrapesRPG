package me.trqhxrd.grapesrpg.game.objects.recipe.tools.wooden;

import me.trqhxrd.grapesrpg.api.objects.recipe.GrapesShapedRecipe;
import me.trqhxrd.grapesrpg.game.objects.item.PlantFiber;
import me.trqhxrd.grapesrpg.game.objects.item.tools.wooden.WoodenSword;
import org.bukkit.Material;

public class WoodenSwordRecipe extends GrapesShapedRecipe {
    public WoodenSwordRecipe() {
        super(new WoodenSword());
        super.setShape("cac", "cac", "cbc");
        super.setIngredient('a', Material.ACACIA_PLANKS, Material.BIRCH_PLANKS, Material.DARK_OAK_PLANKS,
                Material.JUNGLE_PLANKS, Material.OAK_PLANKS, Material.SPRUCE_PLANKS);
        super.setIngredient('b', Material.STICK);
        super.addBinding(8, new PlantFiber());
    }
}
