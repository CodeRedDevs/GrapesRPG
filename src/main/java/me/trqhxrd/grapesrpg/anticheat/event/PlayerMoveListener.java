package me.trqhxrd.grapesrpg.anticheat.event;

import me.trqhxrd.grapesrpg.anticheat.api.User;
import me.trqhxrd.grapesrpg.anticheat.api.check.Level;
import me.trqhxrd.grapesrpg.anticheat.api.exception.InvalidDataException;
import me.trqhxrd.grapesrpg.anticheat.api.logging.CheckResult;
import me.trqhxrd.grapesrpg.anticheat.checks.speed.SpeedCheck;
import me.trqhxrd.grapesrpg.anticheat.checks.speed.SpeedData;
import me.trqhxrd.grapesrpg.api.attribute.Register;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

@Register
public class PlayerMoveListener implements Listener {

    @EventHandler(ignoreCancelled = true)
    public void onPlayerMove(PlayerMoveEvent e) throws InvalidDataException {
        CheckResult result = User.get(e.getPlayer()).fire(new SpeedCheck(), new SpeedData(User.get(e.getPlayer()), e.getFrom(), e.getTo()), true, true);
        // In case the player failed the speed-check he will be lagged back.
        if (result.getLevel() != Level.PASSED) e.setTo(e.getFrom());
    }
}
