package me.trqhxrd.grapesrpg.game.mechanics.alchemy;

import me.trqhxrd.grapesrpg.api.attribute.Register;
import me.trqhxrd.grapesrpg.game.objects.block.CrucibleBlock;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ItemDespawnEvent;
import org.bukkit.event.inventory.InventoryPickupItemEvent;

@Register
public class CrucibleListener implements Listener {

    @EventHandler
    public void onItemDespawn(ItemDespawnEvent e) {
        e.setCancelled(CrucibleBlock.isCrucibleItem(e.getEntity()));
    }

    @EventHandler(ignoreCancelled = true)
    public void onInventoryPickupItem(InventoryPickupItemEvent e) {
        e.setCancelled(CrucibleBlock.isCrucibleItem(e.getItem()));
    }
}
