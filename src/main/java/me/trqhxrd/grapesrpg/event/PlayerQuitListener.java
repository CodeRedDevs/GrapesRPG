package me.trqhxrd.grapesrpg.event;

import me.trqhxrd.grapesrpg.Grapes;
import me.trqhxrd.grapesrpg.api.GrapesPlayer;
import me.trqhxrd.grapesrpg.api.event.GrapesPlayerQuitEvent;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

/**
 * @author Trqhxrd
 * This Class handles players, who are quitting using a {@link PlayerQuitEvent}.
 */
public class PlayerQuitListener implements Listener {

    /**
     * This Constructor registers the Listener to the Plugin.
     * WARNING: Only execute one time.
     */
    public PlayerQuitListener() {
        Bukkit.getPluginManager().registerEvents(this, Grapes.getGrapes());
    }

    /**
     * This Method will be executed, everytime, the a player quits.
     *
     * @param e The {@link PlayerQuitEvent} with all important data about the Player.
     */
    @EventHandler(ignoreCancelled = true)
    public void onPlayerQuit(PlayerQuitEvent e) {
        if (GrapesPlayer.exists(e.getPlayer().getUniqueId())) {
            GrapesPlayer p = GrapesPlayer.getByUniqueId(e.getPlayer().getUniqueId());

            GrapesPlayerQuitEvent event = new GrapesPlayerQuitEvent(Grapes.getGrapes(), p);
            Bukkit.getPluginManager().callEvent(event);
            e.setQuitMessage(event.getQuitMessage());

            //PLAYER DESTROY:
            p.getPacketReader().uninject();
            GrapesPlayer.getPlayers().remove(p);
        }
    }
}