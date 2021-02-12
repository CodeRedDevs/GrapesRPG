package me.trqhxrd.grapesrpg.event;

import me.trqhxrd.grapesrpg.Grapes;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityRegainHealthEvent;

public class EntityRegainHeathListener implements Listener {

    public EntityRegainHeathListener() {
        Bukkit.getPluginManager().registerEvents(this, Grapes.getGrapes());
    }

    @EventHandler(ignoreCancelled = true)
    public void onEntityRegainHealth(EntityRegainHealthEvent e) {
        e.setCancelled(true);
    }
}
