package me.trqhxrd.grapesrpg.anticheat.api.pass;

import me.trqhxrd.grapesrpg.anticheat.api.User;

/**
 * This pass allows a player to bypass a check if he has a certain permission.
 *
 * @author Trqhxrd
 */
public class PermissionPass extends Pass {

    /**
     * The permission, that the player is required to have.
     */
    private final String permission;

    /**
     * This constructor creates a new pass.
     *
     * @param id         The ID of the check, that can be bypassed.
     * @param owner      The player, who can bypass the check specified above.
     * @param permission The permission, that the player requires to bypass the check.
     */
    public PermissionPass(String id, User owner, String permission) {
        super(id, owner);
        this.permission = permission;
    }

    /**
     * This method checks, if the player has the permission to bypass the check.
     *
     * @return Whether the player is allowed to bypass the check or not.
     */
    @Override
    public boolean bypass() {
        return this.owner.hasPermission(permission);
    }
}
