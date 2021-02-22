package me.trqhxrd.grapesrpg.event.other;

import me.trqhxrd.grapesrpg.api.attribute.Register;
import me.trqhxrd.grapesrpg.game.config.MapData;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.MapInitializeEvent;

/**
 * If the id of a map has a link stored in a data file, the map should load the image from the link,
 * This listener automates the process.
 *
 * @author Trqhxrd
 */
@Register
public class MapInitializeListener implements Listener {

    /**
     * The handler method.
     *
     * @param e A MapInitializeEvent.
     */
    @EventHandler
    public void onMapInitialize(MapInitializeEvent e) {
        MapData.applyData(e.getMap());
    }
}
