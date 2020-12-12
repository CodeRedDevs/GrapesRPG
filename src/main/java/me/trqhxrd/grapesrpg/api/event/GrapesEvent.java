package me.trqhxrd.grapesrpg.api.event;

import me.trqhxrd.grapesrpg.Grapes;
import org.bukkit.event.Event;

/**
 * This class is the standard class for all events, added by Grapes.
 *
 * @author Trqhxrd
 */
public abstract class GrapesEvent extends Event {

    /**
     * This field is the plugin instance.
     * It can also be queried by using {@link Grapes#getGrapes()}.
     */
    private final Grapes grapes;

    /**
     * This constructor creates a new GrapesEvent.
     *
     * @param grapes The instance for the grapes event.
     */
    public GrapesEvent(Grapes grapes) {
        this.grapes = grapes;
    }

    /**
     * This method returns the Plugins instance.
     * That instance can also be queried by using {@link Grapes#getGrapes()}.
     *
     * @return The Plugins may instance.
     */
    public Grapes getGrapes() {
        return grapes;
    }
}
