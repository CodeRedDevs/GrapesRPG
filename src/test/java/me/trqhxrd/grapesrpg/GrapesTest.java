package me.trqhxrd.grapesrpg;

import be.seeseemelk.mockbukkit.MockBukkit;
import be.seeseemelk.mockbukkit.ServerMock;
import me.trqhxrd.grapesrpg.api.objects.item.GrapesItem;
import me.trqhxrd.grapesrpg.api.objects.recipe.GrapesRecipe;
import me.trqhxrd.grapesrpg.api.objects.recipe.GrapesShapelessRecipe;
import org.bukkit.Material;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

class GrapesTest {

    private static ServerMock server;
    private static Grapes plugin;

    @BeforeAll
    public static void setup() {
        server = MockBukkit.mock();
        plugin = MockBukkit.load(Grapes.class);
    }

    @AfterAll
    public static void deconstruct() {
        MockBukkit.unmock();
    }

    @Test
    public void checkEnabled() {
        Assertions.assertTrue(plugin.isEnabled());
    }

    @Test
    public void getGrapes() {
        Assertions.assertNotNull(Grapes.getGrapes());
    }

    @Test
    public void addRecipe() {
        GrapesShapelessRecipe recipe = new GrapesShapelessRecipe(new GrapesItem(3, Material.DIAMOND_PICKAXE))
                .addIngredient(3, Material.DIAMOND)
                .addIngredient(2, Material.STICK);
        Grapes.getGrapes().addRecipe(recipe);

        Set<GrapesRecipe> expected = new HashSet<>(GrapesRecipe.getRecipes());
        expected.add(recipe);

        Assertions.assertEquals(GrapesRecipe.getRecipes(), expected);
    }
}