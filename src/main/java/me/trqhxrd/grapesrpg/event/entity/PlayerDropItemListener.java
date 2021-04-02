package me.trqhxrd.grapesrpg.event.entity;

import me.trqhxrd.grapesrpg.api.attribute.Register;
import me.trqhxrd.grapesrpg.api.objects.item.GrapesItem;
import me.trqhxrd.grapesrpg.api.objects.item.artifact.Artifact;
import me.trqhxrd.grapesrpg.api.objects.item.artifact.ArtifactState;
import org.bukkit.entity.Item;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.util.Vector;

@Register
public class PlayerDropItemListener implements Listener {

    @EventHandler(ignoreCancelled = true)
    public void onPlayerDropItem(PlayerDropItemEvent e) {
        GrapesItem item = GrapesItem.fromItemStack(e.getItemDrop().getItemStack());
        if (item != null) {
            Artifact a = Artifact.fromGrapesItem(item);
            if (a != null) {
                e.getItemDrop().remove();
                a.setState(ArtifactState.DESPAWNED);
                Vector velocity = e.getItemDrop().getVelocity();
                Item i = a.spawn(e.getPlayer().getEyeLocation().add(0., -.15, 0.));
                if (i != null) {
                    i.setVelocity(velocity);
                    i.setGravity(true);
                    i.setPickupDelay(20);

                    a.setLocation(i.getLocation());
                }
            }
        }
    }
}
