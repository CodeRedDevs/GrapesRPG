package me.trqhxrd.grapesrpg.game.objects.item.wooden;

import me.trqhxrd.grapesrpg.api.objects.item.GrapesItem;
import me.trqhxrd.grapesrpg.api.objects.item.ItemType;
import me.trqhxrd.grapesrpg.api.objects.item.Rarity;
import org.bukkit.Material;

public class WoodenPickaxe extends GrapesItem {

    public WoodenPickaxe() {
        super(36, Material.WOODEN_PICKAXE, "Wooden Pickaxe", 1, Rarity.COMMON, 5, 0, 0, ItemType.MELEE, 100, null);
    }
}
