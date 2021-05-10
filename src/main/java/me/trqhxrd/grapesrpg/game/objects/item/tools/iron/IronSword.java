package me.trqhxrd.grapesrpg.game.objects.item.tools.iron;

import me.trqhxrd.grapesrpg.api.objects.item.GrapesItem;
import me.trqhxrd.grapesrpg.api.objects.item.ItemType;
import me.trqhxrd.grapesrpg.api.objects.item.Rarity;
import org.bukkit.Material;

public class IronSword extends GrapesItem {
    public IronSword() {
        super(100, Material.IRON_SWORD, "Iron Sword", 1, Rarity.COMMON, 30,0,0, ItemType.MELEE,350,null);
    }
}
