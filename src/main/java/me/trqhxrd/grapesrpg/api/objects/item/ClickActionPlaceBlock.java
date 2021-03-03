package me.trqhxrd.grapesrpg.api.objects.item;

import me.trqhxrd.grapesrpg.api.GrapesPlayer;
import me.trqhxrd.grapesrpg.api.objects.block.GrapesBlock;
import me.trqhxrd.grapesrpg.api.objects.block.GrapesBlockState;
import me.trqhxrd.grapesrpg.api.objects.block.GrapesBlockType;
import me.trqhxrd.grapesrpg.api.utils.ClickType;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.craftbukkit.v1_16_R3.entity.CraftPlayer;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.BoundingBox;

import java.lang.reflect.InvocationTargetException;
import java.util.HashSet;
import java.util.Set;

public class ClickActionPlaceBlock implements ClickAction {

    private static final Set<Material> replaceableBlocks = new HashSet<>();

    static {
        replaceableBlocks.add(Material.AIR);
        replaceableBlocks.add(Material.GRASS);
        replaceableBlocks.add(Material.TALL_SEAGRASS);
        replaceableBlocks.add(Material.SEAGRASS);
        replaceableBlocks.add(Material.WATER);
        replaceableBlocks.add(Material.LAVA);
        replaceableBlocks.add(Material.TALL_GRASS);
        replaceableBlocks.add(Material.DEAD_BUSH);
    }

    private final GrapesBlockType type;
    private final GrapesBlockState state;

    public ClickActionPlaceBlock(GrapesBlockType type, GrapesBlockState state) {
        this.type = type;
        this.state = state;
    }

    public ClickActionPlaceBlock(GrapesBlockType type) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        this(type, type.getBlockStateType().getConstructor().newInstance());
    }

    /**
     * This is the method, that will be executed, if the item gets clicked.
     *
     * @param player The player who clicks the item.
     * @param item   The item, that he clicks.
     * @param block  The block, that is clicked. If this is null, the player did not aim at a block.
     * @param face   The BlockFace, that got clicked by the player. If this is null, the player did not aim at a block.
     * @return Returns whether the default-action if the item is clicked should be cancelled. If set to true, the default action will be suppressed.
     */
    @Override
    public boolean onClick(GrapesPlayer player, GrapesItem item, Block block, BlockFace face, ClickType clickType) {
        if (clickType == ClickType.RIGHT) {
            Location loc = block.getRelative(face).getLocation();
            int x = loc.getBlockX();
            int y = loc.getBlockY();
            int z = loc.getBlockZ();

            CraftPlayer p = ((CraftPlayer) player.getWrappedObject());
            BoundingBox playerBox = p.getBoundingBox();
            if (x < playerBox.getMaxX() && x + 1 > playerBox.getMinX() &&
                    y < playerBox.getMaxY() && y + 1 > playerBox.getMinY() &&
                    z < playerBox.getMaxZ() && z + 1 > playerBox.getMinZ()) return true;

            if (player.getWrappedObject().getGameMode() != GameMode.CREATIVE) {
                ItemStack itemInHand = player.getWrappedObject().getInventory().getItemInMainHand();
                int amount = itemInHand.getAmount() - 1;
                if (amount <= 0) itemInHand = null;
                else itemInHand.setAmount(amount);
                player.getWrappedObject().getInventory().setItemInMainHand(itemInHand);
            }
            Location location = block.getRelative(face).getLocation();
            Block bl = location.getBlock();
            if (replaceableBlocks.contains(bl.getType())) {
                GrapesBlock b = GrapesBlock.getBlock(location);
                b.setType(type);
                b.setState(state);
                b.update();
            }
            return true;
        }
        return false;
    }
}
