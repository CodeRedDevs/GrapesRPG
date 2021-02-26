package me.trqhxrd.grapesrpg.game.objects.block;

import me.trqhxrd.grapesrpg.api.GrapesPlayer;
import me.trqhxrd.grapesrpg.api.objects.blocks.GrapesBlock;
import me.trqhxrd.grapesrpg.api.objects.blocks.GrapesBlockState;
import me.trqhxrd.grapesrpg.api.utils.ClickType;

/**
 * This is the BlockState for AscensionShrines.
 *
 * @author Trqhxrd
 */
public class AscensionShrineBlock extends GrapesBlockState {

    /**
     * This method will be executed, if the block gets clicked.
     *
     * @param player The Player, who clicked the block.
     * @param block  The block, which got clicked. (This Block stores this GrapesBlockState)
     * @param type   The Type of the click. (If it was left or right-click)
     * @return If you return true, the default-action of the block will be cancelled. (example: If you click a chest and return true, the chest won't open)
     */
    @Override
    public boolean onClick(GrapesPlayer player, GrapesBlock block, ClickType type) {
        // TODO: 26.02.2021 HANDLE CLICK
        return false;
    }
}
