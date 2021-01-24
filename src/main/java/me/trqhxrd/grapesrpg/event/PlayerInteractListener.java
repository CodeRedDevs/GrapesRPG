package me.trqhxrd.grapesrpg.event;

import me.trqhxrd.grapesrpg.Grapes;
import me.trqhxrd.grapesrpg.api.inventories.CraftingInventory;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

/**
 * This listener handles PlayerInteractions with Blocks or the air.
 * @author Trqhxrd
 */
public class PlayerInteractListener implements Listener {

    /**
     * This constructor registers the listener.
     */
    public PlayerInteractListener() {
        Bukkit.getPluginManager().registerEvents(this, Grapes.getGrapes());
    }

    /**
     * The handler-method.
     * @param e The PlayerInteractEvent.
     */
    @EventHandler(ignoreCancelled = true)
    public void onPlayerInteract(PlayerInteractEvent e) {
        if (e.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
            if (e.getClickedBlock() != null) {
                Block b = e.getClickedBlock();
                if (b.getType().equals(Material.CRAFTING_TABLE)) {
                    e.getPlayer().openInventory(new CraftingInventory().getInventory());
                    e.setCancelled(true);
                }
            }
        }
    }
}
