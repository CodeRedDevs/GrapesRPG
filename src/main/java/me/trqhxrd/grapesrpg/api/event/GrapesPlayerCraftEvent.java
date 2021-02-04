package me.trqhxrd.grapesrpg.api.event;

import me.trqhxrd.grapesrpg.Grapes;
import me.trqhxrd.grapesrpg.api.common.GrapesPlayer;
import me.trqhxrd.grapesrpg.api.objects.recipe.GrapesRecipe;
import me.trqhxrd.grapesrpg.game.inventories.CraftingMenu;
import org.bukkit.event.HandlerList;

public class GrapesPlayerCraftEvent extends GrapesPlayerEvent {

    private static final HandlerList handlerList = new HandlerList();
    private final GrapesRecipe recipe;
    private final CraftingMenu inventory;

    public GrapesPlayerCraftEvent(GrapesPlayer player, GrapesRecipe r, CraftingMenu menu) {
        super(Grapes.getGrapes(), player);
        this.recipe = r;
        this.inventory = menu;
    }

    public static HandlerList getHandlerList() {
        return handlerList;
    }

    @SuppressWarnings("NullableProblems")
    @Override
    public HandlerList getHandlers() {
        return handlerList;
    }
}
