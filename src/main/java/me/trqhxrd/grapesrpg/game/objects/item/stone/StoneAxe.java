package me.trqhxrd.grapesrpg.game.objects.item.stone;

import me.trqhxrd.grapesrpg.api.objects.item.GrapesItem;
import me.trqhxrd.grapesrpg.api.objects.item.ItemType;
import me.trqhxrd.grapesrpg.api.objects.item.Rarity;
import org.bukkit.Material;

public class StoneAxe extends GrapesItem {

    public StoneAxe() {
        super(43, Material.STONE_AXE, "Stone Axe", 1, Rarity.COMMON, 5, 0, 0, ItemType.MELEE, 180, null);
    }
}
