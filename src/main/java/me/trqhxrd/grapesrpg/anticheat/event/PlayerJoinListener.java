package me.trqhxrd.grapesrpg.anticheat.event;

import me.trqhxrd.grapesrpg.anticheat.api.User;
import me.trqhxrd.grapesrpg.anticheat.api.pass.EffectPass;
import me.trqhxrd.grapesrpg.api.attribute.Register;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.potion.PotionEffectType;

@Register
public class PlayerJoinListener implements Listener {
    @EventHandler(ignoreCancelled = true)
    public void onPlayerJoin(PlayerJoinEvent e) {
        User u = new User(e.getPlayer());
        u.addPass(new EffectPass("grapes_check_speed", u, PotionEffectType.SPEED));
    }
}
