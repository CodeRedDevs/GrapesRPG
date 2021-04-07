package me.trqhxrd.grapesrpg.game.objects.entity.npc;

import me.trqhxrd.grapesrpg.api.objects.entity.npc.GrapesNPC;
import org.bukkit.Location;

import java.util.UUID;

public class TestNPC extends GrapesNPC {

    public TestNPC(Location location) {
        super(UUID.randomUUID(), "&cTest", location);
        this.setClickAction(((player, npc, type) -> System.out.println(player.getName() + " : " + npc.getUUID() + " : " + type.name())));
        this.spawn();
    }
}
