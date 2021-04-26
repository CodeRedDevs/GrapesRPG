package me.trqhxrd.grapesrpg.api.objects.entity;

/**
 * This enum contains the different behaviors, which can be applied to NPC-Mobs.
 * @author Trqhxrd
 */
public enum Behavior {
    /**
     * Passive Behavior.
     * The NPC won't attack anybody and will just wander around aimlessly.
     */
    PASSIVE,
    /**
     * Aggressive Behavior:
     * The NPC will attack any entity with a specific type.
     * Also the NPC won't attack other NPCs.
     */
    AGGRESSIVE
}
