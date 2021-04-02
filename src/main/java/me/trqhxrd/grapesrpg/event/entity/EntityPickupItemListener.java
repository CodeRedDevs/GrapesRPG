package me.trqhxrd.grapesrpg.event.entity;

import me.trqhxrd.grapesrpg.api.attribute.Register;
import me.trqhxrd.grapesrpg.api.objects.item.GrapesItem;
import me.trqhxrd.grapesrpg.api.objects.item.artifact.Artifact;
import me.trqhxrd.grapesrpg.api.objects.item.artifact.ArtifactState;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityPickupItemEvent;

@Register
public class EntityPickupItemListener implements Listener {

    @EventHandler(ignoreCancelled = true)
    public void onEntityPickupItem(EntityPickupItemEvent e) {
        GrapesItem item = GrapesItem.fromItemStack(e.getItem().getItemStack());
        if (item != null) {
            Artifact a = Artifact.fromGrapesItem(item);
            if (a != null) {
                a.setState(ArtifactState.OWNED);
                a.setEntity(null);
            }
        }
    }
}
