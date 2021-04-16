package me.trqhxrd.grapesrpg.api.objects.entity.npc.actions;

import me.trqhxrd.grapesrpg.api.GrapesPlayer;
import me.trqhxrd.grapesrpg.api.objects.entity.npc.GrapesNPC;
import me.trqhxrd.grapesrpg.api.objects.entity.npc.GrapesStoryNPC;
import me.trqhxrd.grapesrpg.api.objects.entity.npc.NPCClickAction;
import me.trqhxrd.grapesrpg.api.utils.ClickType;

/**
 * This ClickAction is used by GrapesStoryNPCs to send a message on right-click.
 *
 * @author Trqhxrd
 */
public class NPCClickActionTalk implements NPCClickAction {

    /**
     * This method will be executed, if a player clicks on a NPC.
     *
     * @param player The Player, who clicked the NPC.
     * @param npc    The NPC, which got clicked.
     * @param type   The type of the click. This can either be LEFT or RIGHT.
     */
    @Override
    public void onClick(GrapesPlayer player, GrapesNPC npc, ClickType type) {
        if (type == ClickType.RIGHT) {
            if (npc instanceof GrapesStoryNPC) {
                GrapesStoryNPC talking = (GrapesStoryNPC) npc;
                talking.getMessageEngine().sendRandomMessageBiased(player);
            }
        }
    }
}
