package me.trqhxrd.grapesrpg.game.objects.recipe.tools.iron;

import me.trqhxrd.grapesrpg.api.objects.recipe.GrapesShapedRecipe;
import me.trqhxrd.grapesrpg.game.objects.item.bindings.IronToolBinding;
import me.trqhxrd.grapesrpg.game.objects.item.tools.iron.IronShovel;
import org.bukkit.Material;

public class IronShovelRecipe extends GrapesShapedRecipe {
    public IronShovelRecipe() {
        super(new IronShovel());
        super.addBinding(1, new IronToolBinding());
        super.setShape("bab", "bcb", "bcb");
        super.setIngredient('a', Material.IRON_INGOT);
        super.setIngredient('c', Material.STICK);
    }
}
