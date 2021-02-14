package me.trqhxrd.grapesrpg.game.objects.recipe.armor.crop;

import me.trqhxrd.grapesrpg.api.objects.recipe.GrapesShapedRecipe;
import me.trqhxrd.grapesrpg.game.objects.item.armor.crop.CropBoots;
import org.bukkit.Material;

public class CropBootsRecipe extends GrapesShapedRecipe {
    public CropBootsRecipe() {
        super(new CropBoots());
        super.setShape("a b", "c d", "e f");
        super.setIngredient('a', Material.MELON);
        super.setIngredient('b', Material.PUMPKIN);
        super.setIngredient('c', Material.WHEAT);
        super.setIngredient('d', Material.BEETROOT);
        super.setIngredient('e', Material.POTATO);
        super.setIngredient('f', Material.CARROT);
    }
}
