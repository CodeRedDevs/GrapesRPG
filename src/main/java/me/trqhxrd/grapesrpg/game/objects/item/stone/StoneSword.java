package me.trqhxrd.grapesrpg.game.objects.item.stone;

import me.trqhxrd.grapesrpg.api.objects.item.GrapesItem;
import me.trqhxrd.grapesrpg.api.objects.item.ItemType;
import me.trqhxrd.grapesrpg.api.objects.item.Rarity;
import org.bukkit.Material;

public class StoneSword extends GrapesItem {

    public StoneSword() {
        super(2, Material.STONE_SWORD, "Stone Sword", 1, Rarity.COMMON, 15, 0, 0, ItemType.MELEE, 180, null);
    }
}
