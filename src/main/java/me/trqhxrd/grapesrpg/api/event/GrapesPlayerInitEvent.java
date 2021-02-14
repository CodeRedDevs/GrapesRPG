package me.trqhxrd.grapesrpg.api.event;

import me.trqhxrd.grapesrpg.Grapes;
import me.trqhxrd.grapesrpg.api.GrapesPlayer;
import org.bukkit.event.Cancellable;
import org.bukkit.event.HandlerList;

/**
 * This event gets called every time, a new {@link GrapesPlayer}-Object gets created.
 * You can also cancel this Event.
 * If it is cancelled, the Player can join but won't be detected by the GrapesRPG-Plugin.
 *
 * @author Trqhxrd
 */
public class GrapesPlayerInitEvent extends GrapesPlayerEvent implements Cancellable {

    /**
     * Required by the Spigot API.
     */
    private static final HandlerList handlers = new HandlerList();
    /**
     * If true, the Player can join but will not be detected by the GrapesRPG Plugin.
     */
    private boolean cancelled;

    public GrapesPlayerInitEvent(Grapes grapes, GrapesPlayer player) {
        super(grapes, player);
        this.cancelled = false;
    }

    /**
     * Required by the Spigot API.
     *
     * @return A HandlerList
     */
    public static HandlerList getHandlerList() {
        return handlers;
    }

    /**
     * Required by the Spigot API.
     */
    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    /**
     * Returns true if the event is cancelled.
     *
     * @return {@literal true -> cancelled; false -> event not cancelled.}
     */
    @Override
    public boolean isCancelled() {
        return cancelled;
    }

    /**
     * This method is able to cancel the event.
     * If set to true, the player won't be detected by GrapesRPG.
     *
     * @param cancelled {@literal true -> cancelled; false -> not cancelled.}
     */
    @Override
    public void setCancelled(boolean cancelled) {
        this.cancelled = cancelled;
    }
}
