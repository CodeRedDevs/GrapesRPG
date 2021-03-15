package me.trqhxrd.grapesrpg.game.objects.item.util.alchemy;

import me.trqhxrd.grapesrpg.api.objects.block.GrapesBlock;
import me.trqhxrd.grapesrpg.api.objects.block.GrapesBlockType;
import me.trqhxrd.grapesrpg.api.objects.item.GrapesItem;
import me.trqhxrd.grapesrpg.api.objects.item.Rarity;
import me.trqhxrd.grapesrpg.api.utils.ClickType;
import me.trqhxrd.grapesrpg.game.objects.block.CrucibleBlock;
import org.bukkit.Material;
import org.bukkit.entity.Item;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.Objects;

public class ClearItem extends GrapesItem {

    public ClearItem() {
        super(101, Material.SPONGE, "Crucible Cleaner 9000", 1, Rarity.LEGENDARY, 50);
        super.setClickAction((player, item, block, face, type) -> {
            if (type == ClickType.RIGHT) {
                GrapesBlock gb = GrapesBlock.getBlock(block.getLocation());
                if (gb.getType() == GrapesBlockType.CRUCIBLE) {
                    CrucibleBlock crucible = (CrucibleBlock) gb.getState();
                    crucible.getItems().forEach(i -> {
                        Item dropped = Objects.requireNonNull(player.getLocation().getWorld()).dropItem(player.getLocation(), i);
                        dropped.setVelocity(new Vector(0., 0., 0.));
                        dropped.setGravity(false);
                        dropped.setPickupDelay(0);
                    });
                    crucible.setItems(new ArrayList<>());
                    crucible.setWaterLevel(0);
                    item.use();
                    gb.update();

                    // TODO: 15.03.2021 Add sound and particle effect!
                }
                return true;
            }
            return false;
        });
    }
}
