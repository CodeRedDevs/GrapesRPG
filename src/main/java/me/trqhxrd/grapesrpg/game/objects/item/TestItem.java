package me.trqhxrd.grapesrpg.game.objects.item;

import me.trqhxrd.grapesrpg.api.objects.item.GrapesItem;
import me.trqhxrd.grapesrpg.api.objects.item.ItemType;
import me.trqhxrd.grapesrpg.api.objects.item.Rarity;
import org.bukkit.Material;

import java.util.HashMap;

public class TestItem extends GrapesItem {
    public TestItem() {
        super(10, Material.DIRT, "TestItem", 1, Rarity.HEAVENLY, 300, 200, 100, ItemType.MELEE, 100, new HashMap<>());
    }
}
