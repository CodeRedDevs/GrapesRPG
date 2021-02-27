package me.trqhxrd.grapesrpg.event.other;

import me.trqhxrd.grapesrpg.api.attribute.Register;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.CraftItemEvent;

/**
 * This listener cancels the native crafting.
 * @author Trqhxrd
 */
@Register
public class CraftItemListener implements Listener {
    /**
     * The Handler-Method.
     * @param e A CraftItemEvent.
     */
    @EventHandler(ignoreCancelled = true)
    public void onPrepareItemCraft(CraftItemEvent e) {
        e.setCancelled(true);
    }
}
