package me.trqhxrd.grapesrpg.game.skills;

import me.trqhxrd.grapesrpg.api.skill.Skill;
import me.trqhxrd.grapesrpg.api.skill.SkillTypes;
import me.trqhxrd.grapesrpg.api.skill.Skills;
import org.bukkit.Material;

/**
 * This is a skill, that gets leveled by crafting stuff in the crafting-grid.
 *
 * @author Trqhxrd
 */
public class GrapesSkillCrafting extends Skill {

    /**
     * This creates a new object of this skill.
     *
     * @param owner The owner of this skill.
     */
    public GrapesSkillCrafting(Skills owner) {
        super(owner, 5, Material.CRAFTING_TABLE, "&cCrafting", 1);
    }

    @Override
    public String getKey() {
        return SkillTypes.CRAFTING.getKey();
    }
}
