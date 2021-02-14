package me.trqhxrd.grapesrpg.game.objects.item.armor.crop;

import me.trqhxrd.grapesrpg.api.objects.item.GrapesItem;
import me.trqhxrd.grapesrpg.api.objects.item.ItemType;
import me.trqhxrd.grapesrpg.api.objects.item.Rarity;
import org.bukkit.Material;

import java.util.HashMap;

public class CropLeggings extends GrapesItem {
    public CropLeggings() {
        super(105, Material.LEATHER_LEGGINGS, "Crop Pants", 1, Rarity.COMMON, 10, 0, 0, ItemType.ARMOR, new HashMap<>());
        super.setColor(254, 215, 61);
    }
}
