package me.trqhxrd.grapesrpg.api.event;

import me.trqhxrd.grapesrpg.Grapes;
import me.trqhxrd.grapesrpg.api.common.GrapesPlayer;
import org.bukkit.event.HandlerList;

public class GrapesPlayerQuitEvent extends GrapesPlayerEvent{

    private static final HandlerList handlers = new HandlerList();

    public GrapesPlayerQuitEvent(Grapes grapes, GrapesPlayer player) {
        super(grapes, player);
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList(){
        return handlers;
    }
}
