package me.trqhxrd.grapesrpg.api.skill;

import me.trqhxrd.grapesrpg.api.attribute.key.StringKeyable;

/**
 * This enum stores all the different keys for all the skills.
 */
public enum SkillTypes implements StringKeyable {

    MINING("grapes_mining"),
    CRAFTING("grapes_crafting");

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
