package me.trqhxrd.grapesrpg.game.objects.item.tools.iron;

import me.trqhxrd.grapesrpg.api.objects.item.GrapesItem;
import me.trqhxrd.grapesrpg.api.objects.item.ItemType;
import me.trqhxrd.grapesrpg.api.objects.item.Rarity;
import org.bukkit.Material;

public class IronPickaxe extends GrapesItem {
    // TODO: 10.05.2021 UPDATE IDS OF IRON-ITEMS
    public IronPickaxe() {
        super(101, Material.IRON_PICKAXE, "Iron Pickaxe", 1, Rarity.COMMON, 10, 0, 0, ItemType.MELEE, 350, null);
    }
}
