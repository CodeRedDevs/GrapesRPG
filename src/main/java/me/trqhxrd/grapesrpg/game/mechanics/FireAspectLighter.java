package me.trqhxrd.grapesrpg.game.mechanics;

import me.trqhxrd.grapesrpg.Grapes;
import me.trqhxrd.grapesrpg.api.attribute.Register;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.TNTPrimed;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;

/**
 * If a player clicks a block using an item, which is enchanted with fire-aspect, the block should be lighten up.
 * If the block it tnt, the tnt should be fused.
 *
 * @author Trqhxrd
 */
@Register
public class FireAspectLighter implements Listener {

    /**
     * This is a list of reactions to different materials.
     * If the material is TNT, the TNT block will be fused.
     * If the clicked material is not in the set of keys of this HashMap, the {@link FireAspectLighter#defaultOperation} will be used.
     */
    private static final HashMap<Material, Operation> clickActions = new HashMap<>();
    /**
     * This is the default reaction to a block being clicked by a Player with a fire-aspect item.
     */
    private static Operation defaultOperation = (p, m, b, bf) -> {
        b.getRelative(bf).setType(Material.FIRE);
    };

    static {
        clickActions.put(Material.TNT, (p, m, b, bf) -> {
            if (m == Material.TNT) {
                b.setType(Material.AIR);
                TNTPrimed tnt = (TNTPrimed) b.getWorld().spawnEntity(b.getLocation().add(.5, .5, .5), EntityType.PRIMED_TNT);
                tnt.setFuseTicks(80);
                tnt.setSource(p);
            }
        });
    }

    /**
     * Getter for the list of operations.
     *
     * @return A Map of operations and their material.
     */
    public static HashMap<Material, Operation> getClickActions() {
        return clickActions;
    }

    /**
     * Getter for the default operation.
     * This operation will be executed, if the {@link FireAspectLighter#clickActions} does not contain the material.
     *
     * @return The default operation.
     */
    public static Operation getDefaultOperation() {
        return defaultOperation;
    }

    /**
     * Setter for the default-operation for when a block gets clicked.
     *
     * @param defaultOperation The new default operation.
     */
    public static void setDefaultOperation(Operation defaultOperation) {
        FireAspectLighter.defaultOperation = defaultOperation;
    }

    /**
     * The Handler method for the PlayerInteractEvent.
     *
     * @param e The Event, that has to be handled.
     */
    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent e) {
        if (e.getAction() == Action.RIGHT_CLICK_BLOCK) {
            ItemStack main = e.getPlayer().getInventory().getItem(EquipmentSlot.HAND);
            ItemStack off = e.getPlayer().getInventory().getItem(EquipmentSlot.OFF_HAND);
            boolean mainB = main != null && main.containsEnchantment(Enchantment.FIRE_ASPECT);
            boolean offB = off != null && off.containsEnchantment(Enchantment.FIRE_ASPECT);
            if (mainB || offB) {
                if (e.getHand() == EquipmentSlot.HAND) {
                    Block b = e.getClickedBlock();
                    if (b == null) return;
                    new BukkitRunnable() {
                        @Override
                        public void run() {
                            if (clickActions.containsKey(b.getType())) clickActions.get(b.getType()).lightUp(e.getPlayer(), b.getType(), b, e.getBlockFace());
                            else defaultOperation.lightUp(e.getPlayer(), b.getType(), b, e.getBlockFace());
                        }
                    }.runTaskLater(Grapes.getGrapes(), 1);
                }
            }
        }
    }

    /**
     * This interface is used for setting the operation of clocking a block.
     *
     * @author Trqhxrd
     */
    public interface Operation {

        /**
         * The default method.
         *
         * @param p    The Player who clicked a block.
         * @param m    The Material of that Block.
         * @param b    The Block itself.
         * @param face The BlockFace, which got clicked.
         */
        void lightUp(Player p, Material m, Block b, BlockFace face);
    }
}
