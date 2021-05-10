package me.trqhxrd.grapesrpg.game.objects.recipe.tools.iron;

import me.trqhxrd.grapesrpg.api.objects.recipe.GrapesShapedRecipe;
import me.trqhxrd.grapesrpg.game.objects.item.bindings.IronToolBinding;
import me.trqhxrd.grapesrpg.game.objects.item.tools.iron.IronPickaxe;
import org.bukkit.Material;

public class IronPickaxeRecipe extends GrapesShapedRecipe {
    public IronPickaxeRecipe() {
        super(new IronPickaxe());
        super.addBinding(1, new IronToolBinding());
        super.setShape("aaa", "bcb", "bcb");
        super.setIngredient('a', Material.IRON_INGOT);
        super.setIngredient('c', Material.STICK);
    }
}
