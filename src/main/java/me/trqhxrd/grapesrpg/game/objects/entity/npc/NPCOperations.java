package me.trqhxrd.grapesrpg.game.objects.entity.npc;

import me.trqhxrd.grapesrpg.api.utils.reflection.Reflection;
import net.citizensnpcs.Settings;

public class NPCOperations {

    public static void setupNPCs() {
        Reflection.setValue(Settings.Setting.DEFAULT_PATHFINDING_RANGE, "value", Integer.MAX_VALUE);
        new IntroductionNPC("intro");
    }
}
