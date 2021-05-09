package me.trqhxrd.grapesrpg.anticheat.api.pass;

import me.trqhxrd.grapesrpg.anticheat.api.User;
import org.bukkit.GameMode;

/**
 * This check allows a player to bypass a check, if he is in a certain gamemode.
 *
 * @author Trqhxrd
 */
public class GameModePass extends Pass {

    /**
     * The gamemode in which the player is allowed to bypass the check.
     */
    private final GameMode gameMode;

    /**
     * This constructor creates a new pass.
     *
     * @param id       The ID of the check, that can be bypassed.
     * @param owner    The player, which can bypass the check.
     * @param gameMode The gamemode, in which the player is allowed to bypass the check.
     */
    public GameModePass(String id, User owner, GameMode gameMode) {
        super(id, owner);
        this.gameMode = gameMode;
    }

    /**
     * Returns true, if the player is in the gamemode specified in the constructor.
     *
     * @return Whether the player is allowed to bypass the check.
     */
    @Override
    public boolean bypass() {
        return owner.getGameMode().equals(this.gameMode);
    }

    /**
     * Getter for the gamemode, which allows you to bypass the check.
     *
     * @return The gamemode, which allows you to bypass the check.
     */
    public GameMode getGameMode() {
        return gameMode;
    }
}
