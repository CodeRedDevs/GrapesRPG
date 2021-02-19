package me.trqhxrd.grapesrpg.api.utils.items.map;

import org.bukkit.entity.Player;
import org.bukkit.map.MapCanvas;
import org.bukkit.map.MapPalette;
import org.bukkit.map.MapRenderer;
import org.bukkit.map.MapView;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URI;

/**
 * This child-class of a {@link MapRenderer} can load an image from the internet or from a file on a map.
 *
 * @author Trqhxrd
 */
public class MapRendererImage extends MapRenderer {

    /**
     * If done is true, the map won't be refreshed to save resources.
     */
    private boolean done;
    /**
     * The image, that will be rendered on the map.
     */
    private BufferedImage image;

    /**
     * This constructor creates a new MapRenderer for images and sets the url of the target.
     *
     * @param url The URL of the target image.
     * @throws IOException This throws an IOException if either the URI is malformed or the image does not exist.
     */
    private MapRendererImage(URI url) throws IOException {
        this.done = false;
        this.image = ImageIO.read(url.toURL());
        this.image = MapPalette.resizeImage(image);
    }

    /**
     * This constructor creates a new MapRenderer for images and sets the file of the target.
     *
     * @param file The file, which contains the image.
     * @throws IOException This throws an IOException if the image does not exist.
     */
    private MapRendererImage(File file) throws IOException {
        this(file.toURI());
    }

    /**
     * This method creates a new MapRendererImage and returns it.
     *
     * @param url The url of the image.
     * @return A new MapRenderer for the image.
     */
    public static MapRendererImage getRenderer(URI url) {
        try {
            return new MapRendererImage(url);
        } catch (IOException e) {
            return null;
        }
    }

    /**
     * This method creates a new MapRendererImage and returns it.
     *
     * @param file The file, which contains the image.
     * @return A new MapRenderer for the image.
     */
    public static MapRendererImage getRenderer(File file) {
        return MapRendererImage.getRenderer(file.toURI());
    }

    /**
     * This method paints the map everytime it gets refreshed.
     *
     * @param view   The MapView.
     * @param canvas The Canvas on which we paint.
     * @param p      The player who holds the map.
     */
    @Override
    public void render(MapView view, MapCanvas canvas, Player p) {
        if (done) return;
        canvas.drawImage(0, 0, image);
        view.setTrackingPosition(false);
        done = true;
    }
}
