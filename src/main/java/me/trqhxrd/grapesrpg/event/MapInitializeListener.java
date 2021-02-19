package me.trqhxrd.grapesrpg.event;

import me.trqhxrd.grapesrpg.api.attribute.Register;
import me.trqhxrd.grapesrpg.game.config.MapData;
import me.trqhxrd.grapesrpg.api.utils.items.map.MapRendererImage;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.MapInitializeEvent;
import org.bukkit.map.MapView;

import java.net.URI;
import java.util.Objects;

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
        MapView map = e.getMap();
        URI url = MapData.getMapData(map.getId());
        if (url != null) {
            e.getMap().getRenderers().clear();
            e.getMap().addRenderer(Objects.requireNonNull(MapRendererImage.getRenderer(url)));
        }
    }
}
