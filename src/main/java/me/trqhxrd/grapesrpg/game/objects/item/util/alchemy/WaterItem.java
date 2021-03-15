package me.trqhxrd.grapesrpg.game.objects.item.util.alchemy;

import me.trqhxrd.grapesrpg.api.objects.block.GrapesBlock;
import me.trqhxrd.grapesrpg.api.objects.block.GrapesBlockType;
import me.trqhxrd.grapesrpg.api.objects.item.GrapesItem;
import me.trqhxrd.grapesrpg.api.objects.item.Rarity;
import me.trqhxrd.grapesrpg.api.utils.ClickType;
import me.trqhxrd.grapesrpg.game.objects.block.CrucibleBlock;
import org.bukkit.Material;

public class WaterItem extends GrapesItem {

    public WaterItem() {
        super(100, Material.WATER_BUCKET, "The Alchemist's Water", 1, Rarity.LEGENDARY, 4);
        super.setClickAction((player, item, block, face, type) -> {
            if (type == ClickType.RIGHT) {
                GrapesBlock gb = GrapesBlock.getBlock(block.getLocation());
                if (gb.getType() == GrapesBlockType.CRUCIBLE) {
                    CrucibleBlock crucible = (CrucibleBlock) gb.getState();
                    if (crucible.getWaterLevel() < 3) {
                        crucible.setWaterLevel(3);
                        item.use();
                        gb.update();
                    }
                }
                return true;
            }
            return false;
        });
    }
}
