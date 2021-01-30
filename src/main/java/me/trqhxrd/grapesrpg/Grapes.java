package me.trqhxrd.grapesrpg;

import com.github.lalyos.jfiglet.FigletFont;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import me.trqhxrd.grapesrpg.api.common.GrapesPlayer;
import me.trqhxrd.grapesrpg.api.objects.item.GrapesItem;
import me.trqhxrd.grapesrpg.api.objects.recipe.GrapesRecipe;
import me.trqhxrd.grapesrpg.api.objects.recipe.GrapesShapedRecipe;
import me.trqhxrd.grapesrpg.api.utils.Prefix;
import me.trqhxrd.grapesrpg.api.utils.Utils;
import me.trqhxrd.grapesrpg.commands.GrapesCommand;
import me.trqhxrd.grapesrpg.event.*;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.*;
import java.net.URL;
import java.nio.file.Files;
import java.util.Scanner;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

/**
 * The GrapesRPG-Main-Class.
 * It extends from a {@link JavaPlugin}.
 *
 * @author Trqhxrd
 */
public class Grapes extends JavaPlugin {

    /**
     * This constant is the serialisation engine used for items, recipes (coming soon), etc.
     */
    public static final Gson GSON = new GsonBuilder()
            .setPrettyPrinting()
            .serializeNulls()
            .create();
    /**
     * This is the instance of the GrapesRPG plugin.
     * It is used to register Listeners and other things, which need a JavaPlugin.
     * Also if you want to use Plugin-Specific {@link Utils}, it is usable to get this Plugins Utils.
     */
    private static Grapes grapes;
    /**
     * This object is used for sending Messages to the Player.
     *
     * @see Utils
     */
    private Utils utils;

    /**
     * You need this method, if you want to do something with the Plugin instance.
     * (e.g. Registering a listener.)
     *
     * @return The Plugins instance.
     */
    public static Grapes getGrapes() {
        return grapes;
    }

    /**
     * The default onEnable method for Bukkit/Spigot-Plugins.
     * Overwritten from the {@link JavaPlugin}.
     *
     * @see JavaPlugin#onEnable()
     */
    @Override
    public void onEnable() {
        grapes = this;
        this.utils = new Utils(Prefix.of("&c[&aGra&bpes&c] &7"));
        for (Player p : Bukkit.getOnlinePlayers()) new GrapesPlayer(p);

        //LOAD RECIPES FROM FILES
        this.reloadRecipes(false);

        //Registering Listeners:
        new InventoryClickListener();
        new InventoryCloseListener();
        new PlayerJoinListener();
        new PlayerQuitListener();
        new PlayerInteractListener();

        //Registering Commands:
        new GrapesCommand();
        this.addRecipe(
                new GrapesShapedRecipe(new GrapesItem(1, Material.STONE_AXE))
                        .setShape("aa ", "ab ", " b ")
                        .setIngredient('a', Material.COBBLESTONE)
                        .setIngredient('b', Material.STICK)
                        .addBinding(16, Material.STRING)
        );
    }

    /**
     * The default onLoad method for Bukkit/Spigot-Plugins.
     * Overwritten from the {@link JavaPlugin}.
     * The only thing, which this method currently does is drawing the figlet-header during the loading of the plugin.
     *
     * @see JavaPlugin#onLoad()
     */
    @Override
    public void onLoad() {
        try {
            Bukkit.getConsoleSender().sendMessage("\n" + FigletFont.convertOneLine("GrapesRPG"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Getter for the {@link Utils}
     *
     * @return The Plugin-Specific Utils.
     */
    public Utils getUtils() {
        return utils;
    }

    /**
     * This method adds a new recipe to the crafting table.
     *
     * @param r The recipe, that you want to add.
     */
    public void addRecipe(GrapesRecipe r) {
        GrapesShapedRecipe.getRecipes().add(r);
    }

    /**
     * This method clears the Collection of recipes and loads all recipes new from files.
     * This method can be used to reload the recipes without restarting the server.
     */
    @SuppressWarnings("ResultOfMethodCallIgnored")
    public void reloadRecipes(boolean force) {
        try {
            GrapesRecipe.getRecipes().clear();

            File recipeFolder = new File(this.getDataFolder(), "recipes");
            boolean b = recipeFolder.mkdirs();
            if (b || force) {
                File zipFile = new File(this.getDataFolder(), "recipes\\recipes.zip");
                zipFile.delete();
                zipFile.createNewFile();
                URL url = new URL("https://github.com/CodeRedDevs/GrapesRPG/raw/feature-recipes/assets/recipes/recipes.zip");
                BufferedInputStream in = new BufferedInputStream(url.openStream());
                FileOutputStream out = new FileOutputStream(zipFile);
                byte[] dataBuffer = new byte[1024];
                int bytesRead;
                while ((bytesRead = in.read(dataBuffer, 0, 1024)) != -1) out.write(dataBuffer, 0, bytesRead);
                in.close();
                out.close();

                dataBuffer = new byte[1024];

                ZipInputStream zis = new ZipInputStream(new FileInputStream(zipFile));
                ZipEntry zipEntry;
                while ((zipEntry = zis.getNextEntry()) != null) {
                    if (!zipEntry.isDirectory()) {
                        File f = new File(recipeFolder, "grapes_" + zipEntry.getName());
                        if (f.createNewFile()) {
                            FileOutputStream fos = new FileOutputStream(f);
                            while ((bytesRead = zis.read(dataBuffer)) > 0) fos.write(dataBuffer, 0, bytesRead);
                            fos.close();
                        }
                    }
                }
                zis.close();
                zipFile.delete();
            }


            Files.list(recipeFolder.toPath()).filter(f -> f.toFile().getName().endsWith(".json")).forEach(f -> {
                try {
                    Scanner scanner = new Scanner(f);
                    StringBuilder s = new StringBuilder();
                    while (scanner.hasNextLine()) s.append(scanner.nextLine());
                    scanner.close();
                    GrapesRecipe r = GrapesRecipe.fromString(s.toString());
                    this.addRecipe(r);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        } catch (IOException ignored) { }
    }
}