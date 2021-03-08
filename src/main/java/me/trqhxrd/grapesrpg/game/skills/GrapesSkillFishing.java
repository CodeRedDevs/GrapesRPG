package me.trqhxrd.grapesrpg.game.skills;

import me.trqhxrd.grapesrpg.api.skill.Skill;
import me.trqhxrd.grapesrpg.api.skill.Skills;
import me.trqhxrd.grapesrpg.api.utils.items.ItemBuilder;
import org.bukkit.Material;

public class GrapesSkillFishing extends Skill {

    /**
     * This constructor will create a new skill with an owner, a level, an amount of xp and a display-item.
     *
     * @param owner The owner of this skill
     */
    public GrapesSkillFishing(Skills owner) {
        super(owner, 23, new ItemBuilder(Material.FISHING_ROD)
                .setName("&9Fishing")
                .build());
    }

    /**
     * This method returns the key.
     *
     * @return The key of this value.
     */
    @Override
    public String getKey() {
        return SkillTypes.FISHING.getKey();
    }
}
