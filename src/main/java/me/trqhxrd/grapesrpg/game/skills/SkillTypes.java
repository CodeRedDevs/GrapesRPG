package me.trqhxrd.grapesrpg.game.skills;

import me.trqhxrd.grapesrpg.api.attribute.key.StringKeyable;

/**
 * This enum stores all the different keys for all the skills.
 */
public enum SkillTypes implements StringKeyable {

    SLAYER("slayer"),
    MINING("mining"),
    CREATION("creation"),
    FARMING("farming"),
    FISHING("fishing"),
    ALCHEMY("alchemy"),
    WOODCUTTING("woodcutting"),
    CONJURATION("conjuration"),
    INFUSION("infusion"),
    EXPLORATION("exploration");

    private final String key;

    SkillTypes(String key) {
        this.key = key;
    }

    /**
     * This method returns the key.
     *
     * @return The key of this value.
     */
    @Override
    public String getKey() {
        return key;
    }
}
