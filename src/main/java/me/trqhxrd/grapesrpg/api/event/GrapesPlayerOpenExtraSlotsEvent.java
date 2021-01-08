package me.trqhxrd.grapesrpg.api.event;

import me.trqhxrd.grapesrpg.Grapes;
import me.trqhxrd.grapesrpg.api.common.GrapesPlayer;
import me.trqhxrd.grapesrpg.api.objects.item.artifact.GrapesArtifactSet;
import org.bukkit.event.Cancellable;
import org.bukkit.event.HandlerList;

public class GrapesPlayerOpenExtraSlotsEvent extends GrapesPlayerEvent implements Cancellable {

    private static final HandlerList handlers = new HandlerList();
    private boolean cancelled;

    public GrapesPlayerOpenExtraSlotsEvent(Grapes grapes, GrapesPlayer player) {
        super(grapes, player);
        this.cancelled = false;
    }

    public GrapesArtifactSet getArtifacts(){
        return this.getPlayer().getArtifacts();
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

    @Override
    public boolean isCancelled() {
        return cancelled;
    }

    @Override
    public void setCancelled(boolean cancelled) {
        this.cancelled = cancelled;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }
}
