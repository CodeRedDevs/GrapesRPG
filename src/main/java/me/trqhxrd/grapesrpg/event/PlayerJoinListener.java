package me.trqhxrd.grapesrpg.event;

import me.trqhxrd.grapesrpg.Grapes;
import me.trqhxrd.grapesrpg.api.common.GrapesPlayer;
import me.trqhxrd.grapesrpg.api.objects.GrapesItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;

/**
 * This Class manages Player joins.
 *
 * @author Trqhxrd
 * @see org.bukkit.event.player.PlayerJoinEvent
 */
public class PlayerJoinListener implements Listener {

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
    public void onPlayerJoin(PlayerJoinEvent e) {
        if (!GrapesPlayer.exists(e.getPlayer().getUniqueId())) {
            new GrapesPlayer(e.getPlayer());
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
}
