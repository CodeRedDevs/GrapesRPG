package me.trqhxrd.grapesrpg.game.skills;

import me.trqhxrd.grapesrpg.api.skill.Skill;
import me.trqhxrd.grapesrpg.api.skill.Skills;
import me.trqhxrd.grapesrpg.api.utils.items.ItemBuilder;
import org.bukkit.Material;

/**
 * This is a skill, that gets leveled by crafting stuff in the crafting-grid.
 *
 * @author Trqhxrd
 */
public class GrapesSkillCreation extends Skill {

    /**
     * This creates a new object of this skill.
     *
     * @param owner The owner of this skill.
     */
    public GrapesSkillCreation(Skills owner) {
        super(owner, 6, new ItemBuilder(Material.CRAFTING_TABLE)
                .setName("&cCreation")
                .build());
    }

    @Override
    public String getKey() {
        return SkillTypes.CREATION.getKey();
    }
}
