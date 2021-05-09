package me.trqhxrd.grapesrpg.anticheat.api.check;

import me.trqhxrd.grapesrpg.anticheat.api.User;
import me.trqhxrd.grapesrpg.anticheat.api.data.ACData;

import java.util.HashMap;
import java.util.Map;

/**
 * This class represents checks, which can be executed by the anticheat.
 *
 * @param <D> The type of data, that this check will receive.
 * @author Trqhxrd
 */
public abstract class Check<D extends ACData> {

    /**
     * This map contains an instance of every check.
     */
    private static final Map<String, Check<?>> checks = new HashMap<>();
    /**
     * This field contains the name of the check.
     */
    private final String name;
    /**
     * This field contains the id of the check.
     */
    private final String id;

    /**
     * This constructor creates a new check.
     *
     * @param name The name of the check. This will be logged if a player fails this check.
     * @param id   The id of the check. This is required, if you want to call the check.
     */
    public Check(String name, String id) {
        this.name = name;
        this.id = id;

        checks.put(this.id, this);
    }

    /**
     * This is the getter for the map, which contains all the checks.
     *
     * @return A Map containing all the different checks.
     */
    public static Map<String, Check<?>> getChecks() {
        return checks;
    }

    /**
     * This method has to be customized for each check because this will check, if a player is cheating.
     * This will be changed in the future... The goal is a system, which automatically triggers countermeasures if a player triggers the same test multiple times in the same moment.
     *
     * @param player The player, who should be checked.
     * @param data   A data-object, which can provide additional data.
     * @return The severity of the cheats. There a different levels. ({@link Level})
     */
    public abstract Level fire(User player, D data);

    /**
     * Getter for the Name of the Check.
     *
     * @return The name of the Check.
     */
    public String getName() {
        return name;
    }

    /**
     * Getter for the ID of the Check.
     *
     * @return The ID of the Check.
     */
    public String getID() {
        return id;
    }
}
