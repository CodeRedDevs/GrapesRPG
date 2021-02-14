package me.trqhxrd.grapesrpg.game.objects.item.armor.crop;

import me.trqhxrd.grapesrpg.api.objects.item.GrapesItem;
import me.trqhxrd.grapesrpg.api.objects.item.ItemType;
import me.trqhxrd.grapesrpg.api.objects.item.Rarity;
import org.bukkit.Material;

import java.util.HashMap;

public class CropBoots extends GrapesItem {
    public CropBoots() {
        super(106, Material.LEATHER_BOOTS, "Crop Boots", 1, Rarity.COMMON, 10, 0, 0, ItemType.ARMOR, new HashMap<>());
        super.setColor(254, 215, 61);
    }
}
