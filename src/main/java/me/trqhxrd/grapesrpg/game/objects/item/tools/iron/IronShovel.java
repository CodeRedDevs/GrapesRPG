package me.trqhxrd.grapesrpg.game.objects.item.tools.iron;

import me.trqhxrd.grapesrpg.api.objects.item.GrapesItem;
import me.trqhxrd.grapesrpg.api.objects.item.ItemType;
import me.trqhxrd.grapesrpg.api.objects.item.Rarity;
import org.bukkit.Material;

public class IronShovel extends GrapesItem {

    public IronShovel() {
        super(103, Material.IRON_SHOVEL, "Iron Shovel", 1, Rarity.COMMON, 10, 0, 0, ItemType.MELEE, 350, null);
    }
}
