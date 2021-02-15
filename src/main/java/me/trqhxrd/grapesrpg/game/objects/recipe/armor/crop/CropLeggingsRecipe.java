package me.trqhxrd.grapesrpg.game.objects.recipe.armor.crop;

import me.trqhxrd.grapesrpg.api.objects.recipe.GrapesShapedRecipe;
import me.trqhxrd.grapesrpg.game.objects.item.armor.crop.CropLeggings;
import org.bukkit.Material;

public class CropLeggingsRecipe extends GrapesShapedRecipe {
    public CropLeggingsRecipe() {
        super(new CropLeggings());
        super.setShape("abc", "d e", "f g");
        super.setIngredient('a', Material.MELON_SLICE);
        super.setIngredient('b', Material.PUMPKIN);
        super.setIngredient('c', Material.CARROT);
        super.setIngredient('d', Material.BEETROOT);
        super.setIngredient('e', Material.POTATO);
        super.setIngredient('f', Material.SUGAR_CANE);
        super.setIngredient('g', Material.WHEAT);
    }
}
