package me.trqhxrd.grapesrpg.event.player;

import me.trqhxrd.grapesrpg.api.attribute.Register;
import me.trqhxrd.grapesrpg.game.inventories.CraftingMenu;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

/**
 * This listener handles PlayerInteractions with Blocks or the air.
 *
 * @author Trqhxrd
 */
@Register
public class PlayerInteractListener implements Listener {

    /**
     * The handler-method.
     *
     * @param e The PlayerInteractEvent.
     */
    @EventHandler(ignoreCancelled = true)
    public void onPlayerInteract(PlayerInteractEvent e) {
        if (e.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
            if (e.getClickedBlock() != null) {
                Block b = e.getClickedBlock();
                if (b.getType().equals(Material.CRAFTING_TABLE)) {
                    CraftingMenu menu = new CraftingMenu();
                    menu.openInventory(e.getPlayer());
                    e.setCancelled(true);
                }
            }
        }
    }
}
