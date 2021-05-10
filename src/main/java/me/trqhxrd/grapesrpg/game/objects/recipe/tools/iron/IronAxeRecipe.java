package me.trqhxrd.grapesrpg.game.objects.recipe.tools.iron;

import me.trqhxrd.grapesrpg.api.objects.recipe.GrapesShapedRecipe;
import me.trqhxrd.grapesrpg.game.objects.item.bindings.IronToolBinding;
import me.trqhxrd.grapesrpg.game.objects.item.tools.iron.IronAxe;
import org.bukkit.Material;

public class IronAxeRecipe {
    public static class First extends GrapesShapedRecipe{
        public First() {
            super(new IronAxe());
            super.addBinding(1, new IronToolBinding());
            super.setShape("aab", "acb", "bcb");
            super.setIngredient('a', Material.IRON_INGOT);
            super.setIngredient('c', Material.STICK);
        }
    }public static class Second extends GrapesShapedRecipe{
        public Second() {
            super(new IronAxe());
            super.addBinding(1, new IronToolBinding());
            super.setShape("baa", "bca", "bcb");
            super.setIngredient('a', Material.IRON_INGOT);
            super.setIngredient('c', Material.STICK);
        }
    }
}
