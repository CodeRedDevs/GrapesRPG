package me.trqhxrd.grapesrpg.event;

import me.trqhxrd.grapesrpg.Grapes;
import me.trqhxrd.grapesrpg.api.common.GrapesPlayer;
import me.trqhxrd.grapesrpg.api.event.GrapesPlayerJoinEvent;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerLoginEvent;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * This Class manages Player joins.
 *
 * @author Trqhxrd
 * @see org.bukkit.event.player.PlayerJoinEvent
 */
public class PlayerJoinListener implements Listener {

    /**
     * This HashMap stores all information about the joining Players.
     * Entries will be added in the PlayerLoginEvent and deleted in the PlayerJoinEvent.
     */
    private final Map<UUID, String> joins = new HashMap<>();

    /**
     * This Constructor registers the Listener to the Plugin.
     * WARNING: Only execute one time.
     */
    public PlayerJoinListener() {
        Bukkit.getPluginManager().registerEvents(this, Grapes.getGrapes());
    }

    /**
     * This Method handles PlayerJoins using the {@link PlayerJoinEvent}
     *
     * @param e A PlayerJoinEvent
     */
    @EventHandler(ignoreCancelled = true)
    public void onPlayerLogin(PlayerLoginEvent e) {
        if (!GrapesPlayer.exists(e.getPlayer().getUniqueId())) {
            GrapesPlayer player = new GrapesPlayer(e.getPlayer());
            GrapesPlayerJoinEvent event = new GrapesPlayerJoinEvent(Grapes.getGrapes(), player);
            Bukkit.getPluginManager().callEvent(event);
            if (!event.isCancelled()) {
                e.allow();
                joins.put(player.getUniqueId(), event.getJoinMessage());
            } else {
                e.disallow(PlayerLoginEvent.Result.KICK_OTHER, event.getKickMessage());
                GrapesPlayer.getPlayers().remove(player);
            }
        }
    }

    /**
     * If the PlayerLoginEvent doesn't cancel the join, the join-message will be set.
     *
     * @param e A PlayerJoinEvent.
     */
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {
        try {
            GrapesPlayer player = GrapesPlayer.getByUniqueId(e.getPlayer().getUniqueId());
            String message = joins.get(player.getUniqueId());
            e.setJoinMessage(message);
            joins.remove(e.getPlayer().getUniqueId());
        } catch (Exception ignored) {
        }
    }
}
