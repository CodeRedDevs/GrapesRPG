package me.trqhxrd.grapesrpg.api.utils.config;

import org.bukkit.configuration.ConfigurationSection;

import java.util.List;

/**
 * This interface contains all operations, that a config needs to be able to do.
 *
 * @author Trqhxrd
 */
public interface ConfigurationOperations {

    /**
     * This method sets an object at a certain location.
     *
     * @param path The path, where you want to set the value.
     * @param t    The value, that should be set at the location.
     * @param <T>  The type of the object, that you want to store.
     */
    <T> void set(String path, T t);

    /**
     * This method gets any object, that is stored at the path.
     *
     * @param path The path, for which you want to get the object.
     * @return The object, that is stored at the path.
     */
    Object get(String path);

    /**
     * This method gets the integer, which is stored at the given path.
     *
     * @param path The path of the value.
     * @return The value, which is stored at that path.
     */
    int getInt(String path);

    /**
     * This method gets the boolean, which is stored at the given path.
     *
     * @param path The path of the value.
     * @return The value, which is stored at that path.
     */
    boolean getBoolean(String path);

    /**
     * This method gets the double, which is stored at the given path.
     *
     * @param path The path of the value.
     * @return The value, which is stored at that path.
     */
    double getDouble(String path);

    /**
     * This method gets the string, which is stored at the given path.
     *
     * @param path The path of the value.
     * @return The value, which is stored at that path.
     */
    String getString(String path);

    /**
     * This method gets the list of integers, which is stored at the given path.
     *
     * @param path The path of the value.
     * @return The value, which is stored at that path.
     */
    List<Integer> getIntList(String path);

    /**
     * This method returns true, if the path you gave contains a value.
     *
     * @param path The path, for which you want to check.
     * @return true if there is a value.
     */
    boolean contains(String path);

    /**
     * This method gets the configuration section, which is stored at the given path.
     *
     * @param path The path of the value.
     * @return The value, which is stored at that path.
     */
    ConfigurationSection getConfigurationSection(String path);

    /**
     * This method saves the config to a file.
     */
    void save();

    /**
     * This method removes all empty ConfigurationSections from the config.
     * @return Returns true, if empty entries were found. This value can be used to call this method recursively until no values will be found anymore.
     */
    boolean purge();
}
