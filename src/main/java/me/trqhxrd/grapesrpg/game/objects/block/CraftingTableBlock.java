package me.trqhxrd.grapesrpg.game.objects.block;

import me.trqhxrd.grapesrpg.api.GrapesPlayer;
import me.trqhxrd.grapesrpg.api.objects.block.GrapesBlock;
import me.trqhxrd.grapesrpg.api.objects.block.GrapesBlockState;
import me.trqhxrd.grapesrpg.api.utils.ClickType;
import me.trqhxrd.grapesrpg.game.inventories.CraftingMenu;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.BlockFace;

public class CraftingTableBlock extends GrapesBlockState {
    /**
     * This method will be executed, if the block gets clicked.
     *
     * @param player The Player, who clicked the block.
     * @param block  The block, which got clicked. (This Block stores this GrapesBlockState)
     * @param face   The clicked BlockFace.
     * @param type   The Type of the click. (If it was left or right-click)
     * @return If you return true, the default-action of the block will be cancelled. (example: If you click a chest and return true, the chest won't open)
     */
    @Override
    public boolean onClick(GrapesPlayer player, GrapesBlock block, BlockFace face, ClickType type) {
        if (block.getBukkitBlock().getType().equals(Material.CRAFTING_TABLE)) {
            CraftingMenu menu = new CraftingMenu();
            menu.openInventory(player.getWrappedObject());
        }
        return true;
    }

    /**
     * This will write the values in the state to the original Bukkit-Block ate the location of our GrapesBlock.
     *
     * @param location The location of the block.
     */
    @Override
    public void update(Location location) {
        super.update(location);
    }
}
