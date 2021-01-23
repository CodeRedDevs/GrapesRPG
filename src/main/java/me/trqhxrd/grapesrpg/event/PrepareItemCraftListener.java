package me.trqhxrd.grapesrpg.event;

import me.trqhxrd.grapesrpg.Grapes;
import me.trqhxrd.grapesrpg.api.objects.recipe.GrapesRecipe;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.PrepareItemCraftEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * This Listener checks for all recipes.
 * @author Trqhxrd
 */
public class PrepareItemCraftListener implements Listener {

    /**
     * This constructor registers the listener.
     * Only execute once in the {@link JavaPlugin#onEnable()}-method.
     */
    public PrepareItemCraftListener() {
        Bukkit.getPluginManager().registerEvents(this, Grapes.getGrapes());
    }

    /**
     * The code that will be run as soon as the event e gets called.
     * @param e A PrepareItemCraftEvent.
     */
    @EventHandler(ignoreCancelled = true)
    public void onPrepareItemCraft(PrepareItemCraftEvent e) {
        ItemStack[] matrix = e.getInventory().getMatrix();
        for (GrapesRecipe r : GrapesRecipe.getRecipes()) {
            boolean valid = r.check(matrix);
            if (valid) {
                e.getInventory().setResult(r.getResult().build());
                break;
            }
        }
    }
}