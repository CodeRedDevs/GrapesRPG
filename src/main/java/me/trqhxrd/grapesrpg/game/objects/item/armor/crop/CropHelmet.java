package me.trqhxrd.grapesrpg.game.objects.item.armor.crop;

import me.trqhxrd.grapesrpg.api.objects.item.GrapesItem;
import me.trqhxrd.grapesrpg.api.objects.item.ItemType;
import me.trqhxrd.grapesrpg.api.objects.item.Rarity;
import org.bukkit.Material;

import java.util.HashMap;

/**
 * This class defines the Crop-Helmet.
 * It just sets some values specific for this item.
 * For example it's id.
 *
 * @author Trqhxrd
 */
public class CropHelmet extends GrapesItem {
    /**
     * This constructor can be used, if you need another instance of this item.
     */
    public CropHelmet() {
        super(103, Material.LEATHER_HELMET, "Crop Helmet", 1, Rarity.COMMON, 10, 0, 0, ItemType.ARMOR, new HashMap<>());
        super.setColor(254, 215, 61);
    }
}
