package me.trqhxrd.grapesrpg.game.config;

import me.trqhxrd.grapesrpg.Grapes;
import me.trqhxrd.grapesrpg.api.utils.Prefix;
import me.trqhxrd.grapesrpg.api.utils.config.Config;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * This represents the main-config.
 *
 * @author Trqhxrd
 */
public class GrapesConfig extends Config {

    /**
     * This field stores the config's instance.
     */
    private static GrapesConfig instance;

    private List<String> ecoTabCompletions = null;

    /**
     * This constructor creates a new instance of the GrapesConfig and loads all the values directly from the file.
     */
    public GrapesConfig() {
        super(new File(Grapes.getGrapes().getDataFolder(), "config.yml"));
        this.getWrappedObject().addDefault("economy.defaultcash", 1000);
        this.getWrappedObject().addDefault("prefix", "&c[&aGra&bpes&c] &7");
        this.getWrappedObject().addDefault("economy.tabcompletions", Arrays.asList(1, 10, 100, 1000, 10000, 100000));
        this.getWrappedObject().options().copyDefaults(true);
        this.save();
    }

    /**
     * This method returns the main-instance of this config.
     *
     * @return The Main-Instance of this config.
     */
    public static GrapesConfig getInstance() {
        if (instance == null) instance = new GrapesConfig();
        return instance;
    }

    /**
     * This method returns the amount of cash a player gets after he joined for the first time.
     *
     * @return The amount of cash a player gets after he joined for the first time.
     */
    public long getStarterCash() {
        return this.getLong("economy.defaultcash");
    }

    /**
     * This method returns the grapes-prefix.
     * Also this method does not translate color-codes.
     *
     * @return The grapes-prefix.
     */
    public String getPrefixRaw() {
        return this.getString("prefix");
    }

    /**
     * This method returns the prefix in his default-form.
     *
     * @return The Prefix for Grapes-RPG.
     */
    public Prefix getPrefix() {
        return Prefix.of(this.getPrefixRaw());
    }

    /**
     * This method returns a list of tab-completions, that will be presented while typing in the pay-command.
     *
     * @return The presets for the amount of money while using the pay-command.
     */
    public List<String> getEcoCommandTabCompletions() {
        if (ecoTabCompletions == null) {
            ecoTabCompletions = new ArrayList<>();
            this.getIntList("economy.tabcompletions").forEach(i -> ecoTabCompletions.add(String.valueOf(i)));
        }
        return ecoTabCompletions;
    }
}
