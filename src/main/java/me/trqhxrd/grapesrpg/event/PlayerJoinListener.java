package me.trqhxrd.grapesrpg.event;

import me.trqhxrd.grapesrpg.Grapes;
import me.trqhxrd.grapesrpg.api.common.GrapesPlayer;
import me.trqhxrd.grapesrpg.api.event.GrapesPlayerJoinEvent;
import me.trqhxrd.grapesrpg.api.objects.item.GrapesItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.inventory.ItemStack;

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
     * @param e A PlayerLoginEvent
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

        ItemStack itemStack = new GrapesItemBuilder(Material.DIRT)
                .setID(3)
                .setName("&cItem")
                .setLore("Lore line 1", "Lore line 2", "Lore line 3")
                .setNBTValue("grapes.test.xyz", true)
                .setDurability(1000, 1000)
                .addEnchantment(Enchantment.DAMAGE_ALL, 5)
                .addEnchantment(Enchantment.DURABILITY, 3)
                .build();
        e.getPlayer().getInventory().addItem(itemStack);
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
            //Place Chest for extra-inventory in players inventory.
            int slot = 35;
            if (Grapes.getGrapes().getConfig().contains("settings.inventory.extra-slots-icon.slot"))
                slot = Grapes.getGrapes().getConfig().getInt("settings.inventory.extra-slots-icon.slot");
            Grapes.getGrapes().getConfig().set("settings.inventory.extra-slots-icon.slot", slot);
            Grapes.getGrapes().saveConfig();
            ItemStack item = new GrapesItemBuilder(Material.ENDER_CHEST)
                    .setName("&bYour Equipment")
                    .setLore(
                            "&7Click this Item to open the",
                            "&7inventory, which contains",
                            "&7your artifacts, stats",
                            "&7and much more!"
                    )
                    .build();
            player.getSpigotPlayer().getInventory().setItem(slot, item);

            String message = joins.get(player.getUniqueId());
            e.setJoinMessage(message);
            joins.remove(e.getPlayer().getUniqueId());
        } catch (Exception ignored) {
        }
    }
}
