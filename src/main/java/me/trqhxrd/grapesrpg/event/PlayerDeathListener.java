package me.trqhxrd.grapesrpg.event;

import me.trqhxrd.grapesrpg.Grapes;
import me.trqhxrd.grapesrpg.api.common.GrapesPlayer;
import me.trqhxrd.grapesrpg.api.mechanics.bounty.Bounty;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

import java.util.Set;

/**
 * This class handles Player-Deaths.
 * @author Trqhxrd
 */
public class PlayerDeathListener implements Listener {

    /**
     * The constructor registers the Listener to the server.
     * Only execute once.
     */
    public PlayerDeathListener() {
        Bukkit.getPluginManager().registerEvents(this, Grapes.getGrapes());
    }

    /**
     * The Handler-Method.
     * @param e A PlayerDeathEvent.
     */
    @EventHandler(ignoreCancelled = true)
    public void onPlayerDeath(PlayerDeathEvent e) {
        if (e.getEntity().getKiller() == null) return;
        Set<Bounty> bounties = Bounty.getBounties(e.getEntity().getUniqueId());
        bounties.forEach(b -> b.done(GrapesPlayer.getByUniqueId(e.getEntity().getKiller().getUniqueId())));
    }
}
