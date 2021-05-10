package me.trqhxrd.grapesrpg.game.objects.item.tools.wooden;

import me.trqhxrd.grapesrpg.api.objects.item.GrapesItem;
import me.trqhxrd.grapesrpg.api.objects.item.ItemType;
import me.trqhxrd.grapesrpg.api.objects.item.Rarity;
import org.bukkit.Material;

public class WoodenShovel extends GrapesItem {

    public WoodenShovel() {
        super(36, Material.WOODEN_SHOVEL,"Wooden Shovel", 1, Rarity.COMMON, 5,0,0, ItemType.MELEE, 100, null);
    }
}
