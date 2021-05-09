package me.trqhxrd.grapesrpg.anticheat.api.pass;

import me.trqhxrd.grapesrpg.anticheat.api.User;
import me.trqhxrd.grapesrpg.anticheat.api.check.Check;
import me.trqhxrd.grapesrpg.api.attribute.Owneable;

/**
 * A Pass allows you to bypass a certain check.
 * You can also define a requirement, which has to be met for the player to be able to bypass the check.
 *
 * @author Trqhxrd
 */
public abstract class Pass implements Owneable<User> {
    /**
     * The ID of the check, that the player can bypass.
     */
    protected final String check;
    /**
     * The player, who can bypass the check.
     */
    protected final User owner;

    /**
     * This constructor creates a new Pass.
     *
     * @param id    The ID of the check, that the player can bypass.
     * @param owner The player who can bypass the check.
     */
    public Pass(String id, User owner) {
        this.check = id;
        this.owner = owner;
    }

    /**
     * This constructor creates a new Pass.
     *
     * @param check The check, that the player can bypass.
     * @param owner The player who can bypass the check.
     */
    public Pass(Check<?> check, User owner) {
        this(check.getID(), owner);
    }

    /**
     * This method wil check, if the player meets all the requirements to bypass the check.
     *
     * @return True, if all the requirements have been met. Otherwise false.
     */
    public abstract boolean bypass();

    /**
     * Getter for the player, who can bypass the check.
     *
     * @return The player, who owns this pass.
     */
    @Override
    public User getOwner() {
        return owner;
    }

    /**
     * Getter for the ID of the Check, that can be bypassed.
     *
     * @return The ID of the check, that can by bypassed.
     */
    public String getCheck() {
        return check;
    }
}
