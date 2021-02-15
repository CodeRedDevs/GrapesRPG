package me.trqhxrd.grapesrpg.game.objects.recipe.armor.crop;

import me.trqhxrd.grapesrpg.api.objects.recipe.GrapesShapedRecipe;
import me.trqhxrd.grapesrpg.game.objects.item.armor.crop.CropChestplate;
import org.bukkit.Material;

public class CropChestplateRecipe extends GrapesShapedRecipe {
    public CropChestplateRecipe() {
        super(new CropChestplate());
        super.setShape("aba", "cde", "fgh");
        super.setIngredient('a', Material.WHEAT);
        super.setIngredient('c', Material.SUGAR_CANE);
        super.setIngredient('d', Material.PUMPKIN);
        super.setIngredient('e', Material.BEETROOT);
        super.setIngredient('f', Material.POTATO);
        super.setIngredient('g', Material.MELON);
        super.setIngredient('h', Material.CARROT);
    }
}
