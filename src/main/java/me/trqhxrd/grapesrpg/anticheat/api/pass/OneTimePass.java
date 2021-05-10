package me.trqhxrd.grapesrpg.anticheat.api.pass;

import me.trqhxrd.grapesrpg.anticheat.api.User;

/**
 * This pass allows you to bypass a check one time.
 * After this one time the check will invalidate itself.
 *
 * @author Trqhxrd
 */
public class OneTimePass extends Pass {

    /**
     * This field contains the state of the check. (Whether the player can bypass a check one time or already bypassed a check)
     */
    private boolean used;

    /**
     * This constructor creates a new check.
     *
     * @param id    The ID of the check, that the player can bypass.
     * @param owner The player, which can bypass the check.
     */
    public OneTimePass(String id, User owner) {
        super(id, owner);
        this.used = false;
    }

    /**
     * This method resets the state of the pass.
     *
     * @return If the check was used, this will return true. Otherwise false.
     */
    public boolean reset() {
        if (this.used) {
            this.used = false;
            return true;
        } else return false;
    }

    /**
     * This method allows the player to bypass a check one time.
     *
     * @return Whether the player is allowed to bypass the check.
     */
    @Override
    public boolean bypass() {
        if (!this.used) {
            this.used = true;
            return true;
        } else return false;
    }
}
