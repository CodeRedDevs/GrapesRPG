package me.trqhxrd.grapesrpg.api.event;

import me.trqhxrd.grapesrpg.Grapes;
import me.trqhxrd.grapesrpg.api.common.GrapesPlayer;
import org.bukkit.event.HandlerList;

/**
 * This event get called every time, a player, who is also a {@link GrapesPlayer}, leaves the server.
 *
 * @author Trqhxrd
 */
public class GrapesPlayerQuitEvent extends GrapesPlayerEvent {

    /**
     * Required by the Spigot API.
     */
    private static final HandlerList handlers = new HandlerList();
    private String quitMessage;

    public GrapesPlayerQuitEvent(Grapes grapes, GrapesPlayer player) {
        super(grapes, player);
        this.quitMessage = "§8[§c-§8] §7" + player.getName();
    }

    /**
     * Required by the Spigot API.
     *
     * @return A The {@link HandlerList} of this Event.
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
     * Returns the message, which you want to display as soon as the player leaves.
     *
     * @return The message, which will be displayed on the disconnect.
     */
    public String getQuitMessage() {
        return quitMessage;
    }

    /**
     * This Method will set the message, which will be displayed, after this event got called.
     *
     * @param quitMessage The new message you want to be displayed.
     */
    public void setQuitMessage(String quitMessage) {
        this.quitMessage = quitMessage;
    }
}
