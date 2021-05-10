package me.trqhxrd.grapesrpg.game.objects.item.bindings;

import me.trqhxrd.grapesrpg.api.objects.item.GrapesItem;
import me.trqhxrd.grapesrpg.api.objects.item.Rarity;
import org.bukkit.Material;

public class IronToolBinding extends GrapesItem {
    public IronToolBinding() {
        super(100, Material.IRON_NUGGET, "Iron Tool Binding", Rarity.COMMON);
        // TODO: 10.05.2021 Change GrapesID of IronToolBinding
    }
}
