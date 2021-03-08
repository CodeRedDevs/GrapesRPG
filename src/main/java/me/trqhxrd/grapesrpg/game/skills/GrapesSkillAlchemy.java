package me.trqhxrd.grapesrpg.game.skills;

import me.trqhxrd.grapesrpg.api.skill.Skill;
import me.trqhxrd.grapesrpg.api.skill.Skills;
import me.trqhxrd.grapesrpg.api.utils.items.ItemBuilder;
import org.bukkit.Material;

public class GrapesSkillAlchemy extends Skill {
    /**
     * This constructor will create a new skill with an owner, a level, an amount of xp and a display-item.
     *
     * @param owner The owner of this skill
     */
    public GrapesSkillAlchemy(Skills owner) {
        super(owner, 2, new ItemBuilder(Material.CAULDRON)
                .setName("&5Alchemy")
                .build());
    }

    /**
     * This method returns the key.
     *
     * @return The key of this value.
     */
    @Override
    public String getKey() {
        return SkillTypes.ALCHEMY.getKey();
    }
}
