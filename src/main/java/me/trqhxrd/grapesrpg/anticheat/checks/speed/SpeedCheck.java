package me.trqhxrd.grapesrpg.anticheat.checks.speed;

import me.trqhxrd.grapesrpg.anticheat.api.User;
import me.trqhxrd.grapesrpg.anticheat.api.check.Check;
import me.trqhxrd.grapesrpg.anticheat.api.check.Level;
import me.trqhxrd.grapesrpg.anticheat.api.utils.Vector2D;
import org.bukkit.GameMode;
import org.bukkit.Material;

/**
 * This check checks, if a player is to fast.
 *
 * @author Trqhxrd
 */
public class SpeedCheck extends Check<SpeedData> {

    /**
     * The maximum xz speed on normal ground
     */
    public static final double MAX_XZ_SPEED_DEFAULT = .66D;
    /**
     * The maximum xz speed on ice
     */
    public static final double MAX_XZ_SPEED_ICE = 1D;
    /**
     * The maximum xz speed in creative mode
     */
    public static final double MAX_XZ_SPEED_CREATIVE = 1.15D;
    /**
     * If the player fails the check with more speed than the max speed + this bonus the level is not maybe but definitely.
     */
    public static final double DEFINITELY_BONUS = .5D;

    /**
     * This constructor creates a new speed-check.
     */
    public SpeedCheck() {
        super("Speed-Check", "grapes_check_speed");
    }

    /**
     * The check-method.
     *
     * @param player The player, who should be checked.
     * @param data   A data-object, which can provide additional data.
     * @return The level of severity.
     */
    @Override
    public Level fire(User player, SpeedData data) {
        Vector2D vector2D = new Vector2D(Math.abs(data.getTo().getX()) - Math.abs(data.getFrom().getX()), Math.abs(data.getTo().getZ()) - Math.abs(data.getFrom().getZ()));
        if (player.getWrappedObject().getGameMode().equals(GameMode.CREATIVE)) {
            if (vector2D.length() > MAX_XZ_SPEED_CREATIVE + DEFINITELY_BONUS) return Level.DEFINITELY;
            else if (vector2D.length() > MAX_XZ_SPEED_CREATIVE) return Level.MAYBE;
        } else if (player.getWrappedObject().getLocation().add(0, -1, 0).getBlock().getType().equals(Material.ICE) ||
                player.getWrappedObject().getLocation().add(0, -1, 0).getBlock().getType().equals(Material.PACKED_ICE) ||
                player.getWrappedObject().getLocation().add(0, -1, 0).getBlock().getType().equals(Material.BLUE_ICE)) {
            if (vector2D.length() > MAX_XZ_SPEED_ICE + DEFINITELY_BONUS) return Level.DEFINITELY;
            else if (vector2D.length() > MAX_XZ_SPEED_ICE) return Level.MAYBE;
        } else if (player.getWrappedObject().getGameMode() != GameMode.SPECTATOR) {
            if (vector2D.length() > MAX_XZ_SPEED_DEFAULT + DEFINITELY_BONUS) return Level.DEFINITELY;
            else if (vector2D.length() > MAX_XZ_SPEED_DEFAULT) return Level.MAYBE;
        }
        return Level.PASSED;
    }
}
