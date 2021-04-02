package me.trqhxrd.grapesrpg.api.objects.item.artifact;

/**
 * This enum represents the state of an artifact.
 * It can either be despawned, spawned or owned.
 * If it is despawned no player currently has it and you first have to defeat a boss or something to spawn it.
 * If it is spawned the item exists somewhere on the map and it can be picked up.
 * If it is owned someone found the artifact and owns it until he dies or loses it.
 */
public enum ArtifactState {
    DESPAWNED,
    SPAWNED,
    OWNED
}
