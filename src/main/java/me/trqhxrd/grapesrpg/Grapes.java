package me.trqhxrd.grapesrpg;

import com.github.lalyos.jfiglet.FigletFont;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import me.trqhxrd.grapesrpg.api.GrapesPlayer;
import me.trqhxrd.grapesrpg.api.attribute.Register;
import me.trqhxrd.grapesrpg.api.objects.recipe.GrapesRecipe;
import me.trqhxrd.grapesrpg.api.objects.recipe.GrapesShapedRecipe;
import me.trqhxrd.grapesrpg.api.utils.Prefix;
import me.trqhxrd.grapesrpg.api.utils.Utils;
import me.trqhxrd.grapesrpg.api.utils.reflection.Reflection;
import me.trqhxrd.grapesrpg.game.GameClock;
import me.trqhxrd.grapesrpg.game.objects.recipe.armor.crop.CropBootsRecipe;
import me.trqhxrd.grapesrpg.game.objects.recipe.armor.crop.CropChestplateRecipe;
import me.trqhxrd.grapesrpg.game.objects.recipe.armor.crop.CropHelmetRecipe;
import me.trqhxrd.grapesrpg.game.objects.recipe.armor.crop.CropLeggingsRecipe;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.java.JavaPluginLoader;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.Files;
import java.util.Objects;
import java.util.Scanner;

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
     * This clock executes all timed tasks of the GrapesRPG.
     */
    private GameClock clock;

    /**
     * Never use this constructor.
     * This is only used for testing.
     */
    public Grapes() {
        super();
    }

    /**
     * Never use this constructor.
     * This is only used for testing.
     *
     * @param loader      The loader used to load this plugin.
     * @param dataFolder  The Folder, which contains all the plugins configurations.
     * @param description The DescriptionFile of the Plugin.
     * @param file        The Jar-File, which contains all the plugins code.
     */
    public Grapes(JavaPluginLoader loader, PluginDescriptionFile description, File dataFolder, File file) {
        super(loader, description, dataFolder, file);
    }

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
        this.clock = new GameClock();
        this.clock.start();

        for (Player p : Bukkit.getOnlinePlayers()) new GrapesPlayer(p);

        this.registerListeners("me.trqhxrd.grapesrpg");
        this.registerCommands("me.trqhxrd.grapesrpg");

        //Registering Recipes
        this.addRecipe(new CropHelmetRecipe());
        this.addRecipe(new CropChestplateRecipe());
        this.addRecipe(new CropLeggingsRecipe());
        this.addRecipe(new CropBootsRecipe());
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
     * This method checks the package given and all sub-packages for classes, which are annotated with {@link Register} and registers an instance of the class as a listener.
     *
     * @param aPackage The package, which you want to scan.
     */
    public void registerListeners(String aPackage) {
        Reflection.executeIfClassIsAnnotated(aPackage, Register.class, (c -> {
            try {
                if (Listener.class.isAssignableFrom(c)) Bukkit.getPluginManager().registerEvents((Listener) c.getConstructor().newInstance(), this);
            } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                e.printStackTrace();
            }
        }));
    }

    /**
     * This method checks the package given and all sub-packages for classes, which are annotated with {@link Register} and registers an instance of the class as a command.
     * Every command needs to fill the parameter "command" in the instance of Register.
     *
     * @param aPackage The package, which you want to scan.
     */
    public void registerCommands(String aPackage) {
        Reflection.executeIfClassIsAnnotated(aPackage, Register.class, c -> {
            try {
                if (CommandExecutor.class.isAssignableFrom(c)) {
                    CommandExecutor ce = (CommandExecutor) c.getConstructor().newInstance();
                    Register r = c.getAnnotation(Register.class);
                    Objects.requireNonNull(this.getCommand(r.command())).setExecutor(ce);
                }
                if (TabCompleter.class.isAssignableFrom(c)) {
                    TabCompleter t = (TabCompleter) c.getConstructor().newInstance();
                    Register r = c.getAnnotation(Register.class);
                    Objects.requireNonNull(this.getCommand(r.command())).setTabCompleter(t);
                }
            } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                e.printStackTrace();
            }
        });
    }

    /**
     * This clock runs all plugin tasks repeatedly.
     *
     * @return The GrapesRPG-Main-Clock.
     */
    public GameClock getClock() {
        return clock;
    }

    /**
     * This method reloads all recipes, that are stored in a file in the recipe-folder.
     */
    public void reloadRecipes() {
        try {
            Files.list(new File(this.getDataFolder().getParentFile(), "recipes").toPath()).forEach(f -> {
                try {
                    Scanner s = new Scanner(f);
                    StringBuilder data = new StringBuilder();
                    while (s.hasNextLine()) data.append(s.nextLine());
                    this.addRecipe(GrapesRecipe.fromString(data.toString()));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}