package me.trqhxrd.grapesrpg.api.event;

import me.trqhxrd.grapesrpg.Grapes;
import me.trqhxrd.grapesrpg.api.common.GrapesPlayer;
import org.bukkit.event.Cancellable;
import org.bukkit.event.HandlerList;

/**
 * This Event gets called, everytime a player joins the server.
 * If you cancel the event, the player will be kicked.
 *
 * @author Trqhxrd
 */
public class GrapesPlayerJoinEvent extends GrapesPlayerEvent implements Cancellable {

    /**
     * Required by the Spigot API
     */
    private static final HandlerList handlers = new HandlerList();

    /**
     * The Message you want to display to the player, in case he gets kicked.
     */
    private String kickMessage;

    /**
     * The Message you want to broadcast, as soon as the player joins.
     */
    private String joinMessage;

    /**
     * If true, the event will be cancelled and the player gets kicked.
     */
    private boolean cancel;

    public GrapesPlayerJoinEvent(Grapes grapes, GrapesPlayer player) {
        super(grapes, player);
        this.cancel = false;
        this.kickMessage = "§cYou got kicked due to an issue with our server.";
        this.joinMessage = "§8[§a+§8] §7" + player.getName();
    }

    /**
     * Required by the Spigot API.
     *
     * @return A {@link HandlerList}.
     */
    public static HandlerList getHandlerList() {
        return handlers;
    }

    /**
     * Required by the Spigot API.
     *
     * @return A {@link HandlerList}.
     */
    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    /**
     * Returns if the event is cancelled.
     * true -> cancelled; false -> not cancelled.
     * If the event is cancelled, the player will be kicked and on his kick screen the kick-message will be displayed.
     *
     * @return true if the event is cancelled.
     */
    @Override
    public boolean isCancelled() {
        return cancel;
    }

    /**
     * Can set if the event is cancelled.
     * If set to true, the player will be kicked and he gets the kick-message displayed.
     *
     * @param cancel true -> cancel; false -> not cancel.
     */
    @Override
    public void setCancelled(boolean cancel) {
        this.cancel = cancel;
    }

    /**
     * Returns the kick-message, for which the player gets kicked.
     *
     * @return the message of the players kick-screen.
     */
    public String getKickMessage() {
        return kickMessage;
    }

    /**
     * This method is able to set the kick-message.
     *
     * @param kickMessage The new message.
     */
    public void setKickMessage(String kickMessage) {
        this.kickMessage = kickMessage;
    }

    /**
     * This method will return the message, which will be displayed as soon as a player joins.
     *
     * @return The message, which will be displayed after the player joins.
     */
    public String getJoinMessage() {
        return joinMessage;
    }

    /**
     * This method sets the message, which is displayed as soon as a player joins.
     *
     * @param joinMessage The new message you want to be send.
     */
    public void setJoinMessage(String joinMessage) {
        this.joinMessage = joinMessage;
    }
}
