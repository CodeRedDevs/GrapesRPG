package me.trqhxrd.grapesrpg.event.citizens;

import me.trqhxrd.grapesrpg.api.GrapesPlayer;
import me.trqhxrd.grapesrpg.api.attribute.Register;
import me.trqhxrd.grapesrpg.api.objects.entity.npc.GrapesNPC;
import me.trqhxrd.grapesrpg.api.utils.ClickType;
import net.citizensnpcs.api.event.NPCLeftClickEvent;
import net.citizensnpcs.api.event.NPCRightClickEvent;
import net.citizensnpcs.api.npc.NPC;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

@Register
public class NPCClickListener implements Listener {

    @EventHandler(ignoreCancelled = true)
    public void onNPCClick(NPCLeftClickEvent e) {
        GrapesPlayer player = GrapesPlayer.getByPlayer(e.getClicker());
        NPC npc = e.getNPC();

        GrapesNPC grapesNPC = GrapesNPC.getByNative(npc);
        if (grapesNPC != null) grapesNPC.getClickAction().onClick(player, grapesNPC, ClickType.LEFT);
    }

    @EventHandler(ignoreCancelled = true)
    public void onNPCClick(NPCRightClickEvent e) {
        GrapesPlayer player = GrapesPlayer.getByPlayer(e.getClicker());
        NPC npc = e.getNPC();

        GrapesNPC grapesNPC = GrapesNPC.getByNative(npc);
        if (grapesNPC != null) grapesNPC.getClickAction().onClick(player, grapesNPC, ClickType.RIGHT);
    }
}
