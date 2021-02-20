package me.trqhxrd.grapesrpg.event.player;

import me.trqhxrd.grapesrpg.Grapes;
import me.trqhxrd.grapesrpg.api.GrapesPlayer;
import me.trqhxrd.grapesrpg.api.attribute.Register;
import me.trqhxrd.grapesrpg.api.event.GrapesPlayerJoinEvent;
import me.trqhxrd.grapesrpg.api.objects.item.GrapesItem;
import me.trqhxrd.grapesrpg.api.objects.item.ItemType;
import me.trqhxrd.grapesrpg.api.objects.item.Rarity;
import org.bukkit.Bukkit;
import org.bukkit.Material;
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
@Register
public class PlayerJoinListener implements Listener {

    /**
     * This HashMap stores all information about the joining Players.
     * Entries will be added in the PlayerLoginEvent and deleted in the PlayerJoinEvent.
     */
    private final Map<UUID, String> joins = new HashMap<>();

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
            player.getPacketReader().inject();
            String message = joins.get(player.getUniqueId());
            e.setJoinMessage(message);
            joins.remove(e.getPlayer().getUniqueId());

            e.getPlayer().getInventory().addItem(new GrapesItem(0, Material.FILLED_MAP).getAsMap("https://cdn.pixabay.com/photo/2015/04/23/22/00/tree-736885__340.jpg"));
            e.getPlayer().getInventory().addItem(new GrapesItem(1, Material.DIAMOND_CHESTPLATE, "Test", 1, Rarity.LEGENDARY, 500, 0, 0, ItemType.ARMOR, new HashMap<>()).build());
            e.getPlayer().getInventory().addItem(new GrapesItem(1, Material.DIAMOND_SWORD, "Test", 1, Rarity.LEGENDARY, 10, 0, 0, ItemType.MELEE, new HashMap<>()).build());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
