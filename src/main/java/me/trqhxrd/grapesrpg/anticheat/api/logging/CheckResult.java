package me.trqhxrd.grapesrpg.anticheat.api.logging;

import me.trqhxrd.grapesrpg.anticheat.api.User;
import me.trqhxrd.grapesrpg.anticheat.api.check.Check;
import me.trqhxrd.grapesrpg.anticheat.api.check.Level;
import me.trqhxrd.grapesrpg.anticheat.api.data.ACData;
import me.trqhxrd.grapesrpg.api.attribute.Owneable;

/**
 * A CheckResult contains a player, a check and the level of severity of the Check.
 *
 * @author Trqhxrd
 */
public class CheckResult implements Owneable<User> {

    /**
     * This field contains the player, who got checked.
     */
    private final User owner;
    /**
     * This field contains the class of the check, that was fired.
     */
    private final Class<? extends Check<? extends ACData>> check;
    /**
     * This field contains the severity of the check.
     */
    private final Level level;

    /**
     * This constructor creates a new object of this class.
     *
     * @param owner The player, who got checked.
     * @param check The check, that was fired.
     * @param level The result of the check.
     */
    public CheckResult(User owner, Class<? extends Check<? extends ACData>> check, Level level) {
        this.owner = owner;
        this.check = check;
        this.level = level;
    }

    /**
     * Getter for the check-class.
     *
     * @return The check-class.
     */
    public Class<? extends Check<? extends ACData>> getCheck() {
        return check;
    }

    /**
     * Getter for the level.
     *
     * @return The level.
     */
    public Level getLevel() {
        return level;
    }

    /**
     * This method returns the owner of this object.
     *
     * @return The owner of this object.
     */
    @Override
    public User getOwner() {
        return owner;
    }
}
