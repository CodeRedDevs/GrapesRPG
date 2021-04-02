package me.trqhxrd.grapesrpg.event.entity;

import me.trqhxrd.grapesrpg.api.attribute.Register;
import me.trqhxrd.grapesrpg.api.objects.item.GrapesItem;
import me.trqhxrd.grapesrpg.api.objects.item.artifact.Artifact;
import me.trqhxrd.grapesrpg.api.objects.item.artifact.ArtifactState;
import org.bukkit.entity.Item;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.inventory.ItemStack;

@Register
public class EntityDamageListener implements Listener {

    @EventHandler(ignoreCancelled = true)
    public void onEntityDamage(EntityDamageEvent e) {
        if (e.getEntity() instanceof Item) {
            Item i = (Item) e.getEntity();
            ItemStack is = i.getItemStack();
            GrapesItem item = GrapesItem.fromItemStack(is);

            if (item != null) {
                Artifact a = Artifact.fromGrapesItem(item);
                if (a != null) {
                    i.remove();
                    a.setState(ArtifactState.DESPAWNED);
                    a.setEntity(null);
                }
            }
        }
    }
}
