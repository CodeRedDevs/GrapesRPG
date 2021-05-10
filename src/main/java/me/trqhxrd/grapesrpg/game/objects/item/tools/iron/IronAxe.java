package me.trqhxrd.grapesrpg.game.objects.item.tools.iron;

import me.trqhxrd.grapesrpg.api.objects.item.GrapesItem;
import me.trqhxrd.grapesrpg.api.objects.item.ItemType;
import me.trqhxrd.grapesrpg.api.objects.item.Rarity;
import org.bukkit.Material;

public class IronAxe extends GrapesItem {

    public IronAxe() {
        super(102, Material.IRON_AXE, "Iron Axe", 1, Rarity.COMMON, 10, 0, 0, ItemType.MELEE, 350, null);}
}
