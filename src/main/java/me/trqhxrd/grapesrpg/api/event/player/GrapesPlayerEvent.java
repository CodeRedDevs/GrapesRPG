package me.trqhxrd.grapesrpg.api.event.player;

import me.trqhxrd.grapesrpg.Grapes;
import me.trqhxrd.grapesrpg.api.GrapesPlayer;
import me.trqhxrd.grapesrpg.api.event.GrapesEvent;

/**
 * This class represents a {@link GrapesEvent}, which contains a player.
 *
 * @author Trqhxrd
 */
public abstract class GrapesPlayerEvent extends GrapesEvent {

    /**
     * The {@link GrapesPlayer}, for which this Event is called.
     */
    private final GrapesPlayer player;

    /**
     * This constructor creates a new GrapesEvent with the Plugins instance and the Player.
     *
     * @param grapes The Plugins instance.
     * @param player The player for which the event got called.
     * @see Grapes
     * @see GrapesPlayer
     */
    public GrapesPlayerEvent(Grapes grapes, GrapesPlayer player) {
        super(grapes);
        this.player = player;
    }

    /**
     * This method returns the player from the event.
     *
     * @return The player from this event.
     */
    public GrapesPlayer getPlayer() {
        return player;
    }
}
