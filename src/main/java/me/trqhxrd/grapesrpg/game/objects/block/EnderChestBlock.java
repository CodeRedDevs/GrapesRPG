package me.trqhxrd.grapesrpg.game.objects.block;

import me.trqhxrd.grapesrpg.api.GrapesPlayer;
import me.trqhxrd.grapesrpg.api.objects.block.GrapesBlock;
import me.trqhxrd.grapesrpg.api.objects.block.predefined.DirectableBlock;
import me.trqhxrd.grapesrpg.api.utils.ClickType;
import org.bukkit.block.BlockFace;

/**
 * This is the Blockstate of an enderchest.
 *
 * @author Trqhxrd
 */
public class EnderChestBlock extends DirectableBlock {

    @Override
    public boolean onClick(GrapesPlayer player, GrapesBlock block, BlockFace face, ClickType type) {
        if (type == ClickType.RIGHT) {
            player.getEnderChest().open(player.getWrappedObject());
            return true;
        }
        return false;
    }
}
