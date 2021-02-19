package me.trqhxrd.grapesrpg.game.config;

import me.trqhxrd.grapesrpg.Grapes;
import me.trqhxrd.grapesrpg.api.utils.config.Config;
import me.trqhxrd.grapesrpg.api.utils.items.map.MapRendererImage;
import org.bukkit.map.MapView;

import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Objects;

/**
 * This is an instance of a Config, that stores all data about maps.
 *
 * @author Trqhxrd
 */
public class MapData extends Config {

    /**
     * This is the instance of the Config.
     * If you want to get data from a config just get the data from this instance using {@link MapData#getMapData(int)}.
     */
    private static MapData instance = null;

    /**
     * This constructor creates a new instance of MapData.
     */
    private MapData() {
        super(new File(Grapes.getGrapes().getDataFolder(), "data/maps.yml"));
    }

    /**
     * The init method overwrites the old instance with a fresh new instance of map-data.
     */
    public static void init() {
        instance = new MapData();
    }

    /**
     * This method returns the current instance of the config.
     *
     * @return The current instance of the config.
     */
    public static MapData getInstance() {
        return instance;
    }

    /**
     * This method stores a URL for a map into the config.
     *
     * @param id  The id of the map, that is linked to the url.
     * @param url The url of the image of the map.
     */
    public static void saveMapData(int id, String url) {
        instance.set("maps." + id, url);
        instance.save();
    }

    /**
     * This method returns the url of a certain map.
     *
     * @param id The id of the map for which you want to get the url.
     * @return The Link, which stores the image, that you want to apply to the map.
     */
    public static URI getMapData(int id) {
        try {
            return new URI(instance.getString("maps." + id));
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * This method instantly adds the data to the Map.
     *
     * @param view The view, which should get the {@link MapRendererImage} applied.
     */
    public static void applyData(MapView view) {
        if (instance.contains("maps." + view.getId())) {
            URI link = getMapData(view.getId());
            view.getRenderers().clear();
            view.addRenderer(Objects.requireNonNull(MapRendererImage.getRenderer(link)));
        }
    }
}
