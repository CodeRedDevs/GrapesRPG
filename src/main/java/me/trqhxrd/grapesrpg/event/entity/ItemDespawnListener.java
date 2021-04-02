package me.trqhxrd.grapesrpg.event.entity;

import me.trqhxrd.grapesrpg.api.attribute.Register;
import me.trqhxrd.grapesrpg.api.objects.item.GrapesItem;
import me.trqhxrd.grapesrpg.api.objects.item.artifact.Artifact;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ItemDespawnEvent;

@Register
public class ItemDespawnListener implements Listener {

    @EventHandler(ignoreCancelled = true)
    public void onItemDespawn(ItemDespawnEvent e) {
        GrapesItem item = GrapesItem.fromItemStack(e.getEntity().getItemStack());
        if (item != null) {
            Artifact a = Artifact.fromGrapesItem(item);
            e.setCancelled(a != null);
        }
    }
}
