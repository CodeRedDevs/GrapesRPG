package me.trqhxrd.grapesrpg.event.player;

import me.trqhxrd.grapesrpg.Grapes;
import me.trqhxrd.grapesrpg.api.GrapesPlayer;
import me.trqhxrd.grapesrpg.api.attribute.Register;
import me.trqhxrd.grapesrpg.api.event.GrapesPlayerJoinEvent;
import me.trqhxrd.grapesrpg.api.objects.block.GrapesBlockState;
import me.trqhxrd.grapesrpg.api.objects.block.GrapesBlockType;
import me.trqhxrd.grapesrpg.api.objects.item.ClickActionPlaceBlock;
import me.trqhxrd.grapesrpg.api.objects.item.GrapesItem;
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
            /*e.getPlayer().getInventory().addItem(new GrapesItem(12, Material.DIAMOND_SWORD, "Swrod", 1, Rarity.EPIC, 120,
                    35, 12, ItemType.MELEE, 650, new HashMap<>()).setClickAction((player1, item, block, face, type) -> {
                player1.sendMessage(ChatColor.AQUA + "Working as intended!");
                return false;
            }).build());
            Firework fw = (Firework) e.getPlayer().getLocation().getWorld().spawnEntity(e.getPlayer().getLocation().add(0, 3, 0), EntityType.FIREWORK);
            FireworkMeta fm = fw.getFireworkMeta();
            fm.addEffect(FireworkEffect.builder()
                    .flicker(true)
                    .trail(false)
                    .withColor(Color.RED, Color.BLACK)
                    .with(FireworkEffect.Type.BALL_LARGE)
                    .build());*/
            e.getPlayer().getInventory().addItem(new GrapesItem(-1, Material.STONE).setClickAction(new ClickActionPlaceBlock(GrapesBlockType.STONE, new GrapesBlockState())).build());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
