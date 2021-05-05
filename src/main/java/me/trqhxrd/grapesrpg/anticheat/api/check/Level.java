package me.trqhxrd.grapesrpg.anticheat.api.check;

/**
 * This Level describes the severity of the cheats a player used.
 * @author Trqhxrd
 */
public enum Level {
    /**
     * PASSED means, that the player was not cheating or has a {@link me.trqhxrd.grapesrpg.anticheat.api.pass.Pass} to bypass the check.
     */
    PASSED,
    /**
     * MAYBE means, that the player triggered the check but it could also be a mistake because of lag or some glitch.
     */
    MAYBE,
    /**
     * DEFINITELY means, that the player is 100% cheating.
     */
    DEFINITELY
}
