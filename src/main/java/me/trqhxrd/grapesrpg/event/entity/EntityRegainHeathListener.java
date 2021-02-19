package me.trqhxrd.grapesrpg.event.entity;

import me.trqhxrd.grapesrpg.api.attribute.Register;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityRegainHealthEvent;

@Register
public class EntityRegainHeathListener implements Listener {
    @EventHandler(ignoreCancelled = true)
    public void onEntityRegainHealth(EntityRegainHealthEvent e) {
        e.setCancelled(true);
    }
}
