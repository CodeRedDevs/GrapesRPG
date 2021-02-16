package me.trqhxrd.grapesrpg.api.event;

import me.trqhxrd.grapesrpg.Grapes;
import me.trqhxrd.grapesrpg.api.GrapesPlayer;
import me.trqhxrd.grapesrpg.api.objects.recipe.GrapesRecipe;
import me.trqhxrd.grapesrpg.game.inventories.CraftingMenu;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

/**
 * This Event gets called, if a Players takes out an item from the result-slot of the crafting table.
 *
 * @author Trqhxrd
 */
public class GrapesPlayerCraftEvent extends GrapesPlayerEvent implements Cancellable {

    /**
     * A HandlerList.
     * This is required by Bukkit.
     */
    private static final HandlerList handlerList = new HandlerList();
    /**
     * The recipe, that is currently in the crafting grid.
     */
    private final GrapesRecipe recipe;
    /**
     * The crafting menu, that is opened by the player.
     */
    private final CraftingMenu inventory;
    /**
     * This boolean can be set to ture.
     * In which case the event will be cancelled.
     */
    private boolean cancelled;

    /**
     * This constructor creates a new GrapesPlayerCraftEvent, that can be called using {@link org.bukkit.plugin.PluginManager#callEvent(Event)}.
     *
     * @param player The Player, who is crafting.
     * @param recipe The recipe, that is currently in the crafting GUI.
     * @param menu   The Crafting-Inventory.
     */
    public GrapesPlayerCraftEvent(GrapesPlayer player, GrapesRecipe recipe, CraftingMenu menu) {
        super(Grapes.getGrapes(), player);
        this.recipe = recipe;
        this.inventory = menu;
        this.cancelled = false;
    }

    /**
     * Getter for the Events HandlerList.
     * Required by Bukkit.
     *
     * @return A HandlerList.
     */
    public static HandlerList getHandlerList() {
        return handlerList;
    }

    /**
     * Getter for the recipe, that is currently crafted.
     *
     * @return The recipe, which is currently crafted.
     */
    public GrapesRecipe getRecipe() {
        return recipe;
    }

    /**
     * Getter for the crafting-grid, the Player has opened right now.
     *
     * @return The Crafting Inventory.
     */
    public CraftingMenu getInventory() {
        return inventory;
    }

    /**
     * Getter for the Events HandlerList.
     * Required by Bukkit.
     *
     * @return A HandlerList.
     */
    @SuppressWarnings("NullableProblems")
    @Override
    public HandlerList getHandlers() {
        return handlerList;
    }

    /**
     * Getter for the state of the Event.
     * If this returns true, the crafting won't work.
     *
     * @return The state of the event.
     */
    @Override
    public boolean isCancelled() {
        return cancelled;
    }

    /**
     * If set to true, the Event will be cancelled.
     *
     * @param b If set to true, the event will be cancelled.
     */
    @Override
    public void setCancelled(boolean b) {
        this.cancelled = b;
    }
}
