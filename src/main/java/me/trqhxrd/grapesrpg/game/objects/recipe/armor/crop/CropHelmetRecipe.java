package me.trqhxrd.grapesrpg.game.objects.recipe.armor.crop;

import me.trqhxrd.grapesrpg.api.objects.recipe.GrapesShapedRecipe;
import me.trqhxrd.grapesrpg.game.objects.item.armor.crop.CropHelmet;
import org.bukkit.Material;

public class CropHelmetRecipe extends GrapesShapedRecipe {
    public CropHelmetRecipe() {
        super(new CropHelmet());
        super.setShape("abc", "def", "ghi");
        super.setIngredient('a', Material.POTATO);
        super.setIngredient('b', Material.MELON_SLICE);
        super.setIngredient('c', Material.BEETROOT);
        super.setIngredient('d', Material.CARROT);
        super.setIngredient('e', Material.CARVED_PUMPKIN);
        super.setIngredient('f', Material.WHEAT);
    }
}
