package me.trqhxrd.grapesrpg.anticheat.api.data;

import me.trqhxrd.grapesrpg.anticheat.api.User;
import me.trqhxrd.grapesrpg.anticheat.api.check.Check;

/**
 * ACData and it's extensions are used for giving additional information into a check.
 * Just make a class extend ACData and add some fields.
 *
 * @author Trqhxrd
 */
public abstract class ACData {

    /**
     * The Check, which uses this type of data.
     */
    private final Class<? extends Check<? extends ACData>> checkClass;
    /**
     * The player, who gets checked. This field is in every type of ACData.
     */
    private final User player;

    /**
     * This constructor creates a new instance of ACData.
     *
     * @param checkClass The Class of the Check to which this data corresponds to.
     * @param player     The player, who wil get checked.
     */
    public ACData(Class<? extends Check<? extends ACData>> checkClass, User player) {
        this.checkClass = checkClass;
        this.player = player;
    }

    /**
     * This method returns true, if the class of the check given is assignable from the check-class, that is stored in this object.
     *
     * @param check The check, which should be checked.
     * @return true, if this data corresponds to the check given. otherwise false.
     */
    public boolean matchCheck(Check<? extends ACData> check) {
        return checkClass.isAssignableFrom(check.getClass());
    }

    /**
     * Getter for the check-class.
     *
     * @return The class of the check to which this data-class corresponds to.
     */
    public Class<? extends Check<? extends ACData>> getCheckClass() {
        return checkClass;
    }

    /**
     * Getter for the player, that was checked.
     *
     * @return The player, that was checked.
     */
    public User getPlayer() {
        return player;
    }
}
