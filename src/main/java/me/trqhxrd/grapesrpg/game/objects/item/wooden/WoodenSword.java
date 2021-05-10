package me.trqhxrd.grapesrpg.game.objects.item.wooden;

import me.trqhxrd.grapesrpg.api.objects.item.GrapesItem;
import me.trqhxrd.grapesrpg.api.objects.item.ItemType;
import me.trqhxrd.grapesrpg.api.objects.item.Rarity;
import org.bukkit.Material;

public class WoodenSword extends GrapesItem {

    public WoodenSword() {
        super(1, Material.WOODEN_SWORD, "Wooden Sword", 1, Rarity.COMMON, 10, 0, 0, ItemType.MELEE, 100, null);
    }
}
