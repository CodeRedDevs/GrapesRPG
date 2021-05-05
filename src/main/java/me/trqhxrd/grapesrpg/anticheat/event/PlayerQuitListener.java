package me.trqhxrd.grapesrpg.anticheat.event;

import me.trqhxrd.grapesrpg.anticheat.api.User;
import me.trqhxrd.grapesrpg.api.attribute.Register;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

@Register
public class PlayerQuitListener implements Listener {

    @EventHandler(ignoreCancelled = true)
    public void onPlayerQuit(PlayerQuitEvent e) {
        User.getUsers().remove(User.get(e.getPlayer()));
    }
}
