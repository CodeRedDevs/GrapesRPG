package me.trqhxrd.grapesrpg.event.player;

import me.trqhxrd.grapesrpg.api.GrapesPlayer;
import me.trqhxrd.grapesrpg.api.attribute.Register;
import me.trqhxrd.grapesrpg.api.objects.blocks.GrapesBlock;
import me.trqhxrd.grapesrpg.api.objects.item.GrapesItem;
import me.trqhxrd.grapesrpg.api.utils.ClickType;
import me.trqhxrd.grapesrpg.game.inventories.CraftingMenu;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

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
        if (e.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
            if (!e.getPlayer().isSneaking()) {
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

        if (e.getAction() == Action.RIGHT_CLICK_BLOCK || e.getAction() == Action.LEFT_CLICK_BLOCK) {
            GrapesBlock b = GrapesBlock.getBlock(Objects.requireNonNull(e.getClickedBlock()).getLocation());
            boolean bool = b.getState().onClick(GrapesPlayer.getByUniqueId(e.getPlayer().getUniqueId()), b, (e.getAction() == Action.LEFT_CLICK_BLOCK ? ClickType.LEFT : ClickType.RIGHT));
            e.setCancelled(bool);
        }

        if (e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK) {
            GrapesItem i = GrapesItem.fromItemStack(e.getItem());
            GrapesPlayer p = GrapesPlayer.getByUniqueId(e.getPlayer().getUniqueId());
            if (i != null) e.setCancelled(i.getClickAction().onClick(p, i, e.getClickedBlock(), e.getBlockFace()));
        }
    }
}
