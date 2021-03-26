package me.trqhxrd.grapesrpg.event.other;

import me.trqhxrd.grapesrpg.api.attribute.Register;
import me.trqhxrd.grapesrpg.game.objects.block.CrucibleBlock;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ItemDespawnEvent;
import org.bukkit.persistence.PersistentDataType;

/**
 * This listener disables item despawning for display-items.
 *
 * @author Trqhxrd
 */
@Register
public class ItemDespawnListener implements Listener {

    /**
     * The handler method.
     *
     * @param e An ItemDespawnEvent
     */
    @EventHandler
    public void onItemDespawn(ItemDespawnEvent e) {
        e.setCancelled(e.getEntity().getPersistentDataContainer().has(CrucibleBlock.CRUCIBLE_ITEM_KEY, PersistentDataType.STRING));
    }
}
