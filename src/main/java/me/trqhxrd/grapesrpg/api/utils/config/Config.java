package me.trqhxrd.grapesrpg.api.utils.config;

import me.trqhxrd.grapesrpg.api.utils.Wrapper;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * This class is a blueprint for all other configs.
 *
 * @author Trqhxrd
 */
public class Config extends Wrapper<FileConfiguration> implements ConfigurationOperations {

    /**
     * The file, which contains all the data that is stored in the config.
     */
    private final File file;
    /**
     * The folder, which contains the config-file.
     */
    private final File folder;

    /**
     * This constructor creates a new Configuration in a certain file.
     * If the file does not exist, it will be created.
     *
     * @param file The file, which should store the config data.
     */
    @SuppressWarnings("ResultOfMethodCallIgnored")
    public Config(File file) {
        super(new YamlConfiguration());
        this.file = file;
        this.folder = file.getParentFile();

        try {
            if (!folder.exists()) folder.mkdirs();
            if (!file.exists()) file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            this.getWrappedObject().load(file);
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }
    }

    /**
     * This method returns true, if the path you gave contains a value.
     *
     * @param path The path, for which you want to check.
     * @return true if there is a value.
     */
    @Override
    public boolean contains(String path) {
        return this.getWrappedObject().contains(path);
    }

    /**
     * This method gets the configuration section, which is stored at the given path.
     *
     * @param path The path of the value.
     * @return The value, which is stored at that path.
     */
    @Override
    public ConfigurationSection getConfigurationSection(String path) {
        return this.getWrappedObject().getConfigurationSection(path);
    }

    /**
     * Getter for the configs file.
     *
     * @return The configs file.
     */
    public File getFile() {
        return file;
    }

    /**
     * Getter for the folder containing the configs file.
     *
     * @return The folder containing the configs file.
     */
    public File getFolder() {
        return folder;
    }

    public FileConfiguration getData() {
        return getWrappedObject();
    }

    /**
     * This method gets the integer, which is stored at the given path.
     *
     * @param path The path of the value.
     * @return The value, which is stored at that path.
     */
    @Override
    public int getInt(String path) {
        return getWrappedObject().getInt(path);
    }

    /**
     * This method gets the boolean, which is stored at the given path.
     *
     * @param path The path of the value.
     * @return The value, which is stored at that path.
     */
    @Override
    public boolean getBoolean(String path) {
        return getWrappedObject().getBoolean(path);
    }

    /**
     * This method gets the double, which is stored at the given path.
     *
     * @param path The path of the value.
     * @return The value, which is stored at that path.
     */
    @Override
    public double getDouble(String path) {
        return getWrappedObject().getDouble(path);
    }

    /**
     * This method gets the string, which is stored at the given path.
     *
     * @param path The path of the value.
     * @return The value, which is stored at that path.
     */
    @Override
    public String getString(String path) {
        return getWrappedObject().getString(path);
    }

    /**
     * This method gets any object, that is stored at the path.
     *
     * @param path The path, for which you want to get the object.
     * @return The object, that is stored at the path.
     */
    @Override
    public Object get(String path) {
        return getWrappedObject().get(path);
    }

    /**
     * This method sets an object at a certain location.
     *
     * @param path The path, where you want to set the value.
     * @param t    The value, that should be set at the location.
     */
    @Override
    public <T> void set(String path, T t) {
        getWrappedObject().set(path, t);
    }

    /**
     * This method gets the list of integers, which is stored at the given path.
     *
     * @param path The path of the value.
     * @return The value, which is stored at that path.
     */
    @Override
    public List<Integer> getIntList(String path) {
        return getWrappedObject().getIntegerList(path);
    }

    /**
     * This method saves the config to a file.
     */
    @Override
    public void save() {
        try {
            this.purge();
            this.getWrappedObject().save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * This method removes all empty ConfigurationSections from the config.
     */
    @Override
    public void purge() {
        AtomicBoolean b = new AtomicBoolean(false);
        Set<String> keys = new HashSet<>(this.getWrappedObject().getKeys(true));
        keys.forEach(k -> {
            if (this.getWrappedObject().get(k) instanceof ConfigurationSection) {
                ConfigurationSection section = this.getWrappedObject().getConfigurationSection(k);
                if (section != null && section.getKeys(false).size() == 0) {
                    this.getWrappedObject().set(k, null);
                    b.set(true);
                }
            }
        });

        if (b.get()) this.purge();
    }
}
