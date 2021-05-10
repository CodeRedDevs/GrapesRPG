package me.trqhxrd.grapesrpg.game.objects.recipe.tools.iron;

import me.trqhxrd.grapesrpg.api.objects.recipe.GrapesShapedRecipe;
import me.trqhxrd.grapesrpg.game.objects.item.bindings.IronToolBinding;
import me.trqhxrd.grapesrpg.game.objects.item.tools.iron.IronSword;
import org.bukkit.Material;

public class IronSwordRecipe extends GrapesShapedRecipe {
    public IronSwordRecipe() {
        super(new IronSword());
        super.addBinding(1, new IronToolBinding());
        super.setShape("bab", "bab", "bcb");
        super.setIngredient('a', Material.IRON_INGOT);
        super.setIngredient('c', Material.STICK);
    }
}
