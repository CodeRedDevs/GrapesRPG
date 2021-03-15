package me.trqhxrd.grapesrpg.event.player;

import me.trqhxrd.grapesrpg.api.GrapesPlayer;
import me.trqhxrd.grapesrpg.api.attribute.Register;
import me.trqhxrd.grapesrpg.api.objects.block.GrapesBlock;
import me.trqhxrd.grapesrpg.api.objects.block.GrapesBlockType;
import me.trqhxrd.grapesrpg.api.objects.item.GrapesItem;
import me.trqhxrd.grapesrpg.api.utils.ClickType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;

import java.util.Objects;

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

        boolean cancel = false;

        if (!e.getPlayer().isSneaking()) {
            if (e.getAction() == Action.RIGHT_CLICK_BLOCK || e.getAction() == Action.LEFT_CLICK_BLOCK) {
                GrapesBlock b = GrapesBlock.getBlock(Objects.requireNonNull(e.getClickedBlock()).getLocation());
                if (b.getType() != GrapesBlockType.UNDEFINED) cancel = b.getState().onClick(GrapesPlayer.getByUniqueId(e.getPlayer().getUniqueId()), b, e.getBlockFace(),
                        (e.getAction() == Action.LEFT_CLICK_BLOCK ? ClickType.LEFT : ClickType.RIGHT));
            }
        }

        if (!cancel) {
            if (e.getHand() == EquipmentSlot.HAND) {
                GrapesItem i = GrapesItem.fromItemStack(e.getItem());
                GrapesPlayer p = GrapesPlayer.getByUniqueId(e.getPlayer().getUniqueId());
                if (i != null) {
                    if (i.getCurrentDurability() != 0) {
                        if (i.getClickAction() != null) {
                            cancel = i.getClickAction()
                                    .onClick(p, i, e.getClickedBlock(), e.getBlockFace(),
                                            (e.getAction() == Action.RIGHT_CLICK_BLOCK || e.getAction() == Action.RIGHT_CLICK_AIR ? ClickType.RIGHT : ClickType.LEFT));
                            e.getPlayer().getInventory().setItemInMainHand(i.build());
                        }
                    } else cancel = true;
                }
            }
        }

        e.setCancelled(cancel);
    }
}

