package me.trqhxrd.grapesrpg.game.objects.recipe.bindings;

import me.trqhxrd.grapesrpg.api.objects.recipe.GrapesShapedRecipe;
import me.trqhxrd.grapesrpg.game.objects.item.bindings.IronToolBinding;
import org.bukkit.Material;

public class IronToolBindingRecipe extends GrapesShapedRecipe {
    public IronToolBindingRecipe(){
        super(new IronToolBinding());
        super.setShape("aca", "cbc","aca");
        super.setIngredient('a', Material.IRON_NUGGET);
        super.setIngredient('b', Material.IRON_INGOT);
    }
}
