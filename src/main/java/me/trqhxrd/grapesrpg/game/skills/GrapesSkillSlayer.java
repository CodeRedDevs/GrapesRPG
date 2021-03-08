package me.trqhxrd.grapesrpg.game.skills;

import me.trqhxrd.grapesrpg.api.skill.Skill;
import me.trqhxrd.grapesrpg.api.skill.Skills;
import me.trqhxrd.grapesrpg.api.utils.items.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;

public class GrapesSkillSlayer extends Skill {

    /**
     * This constructor will create a new skill with an owner, a level, an amount of xp and a display-item.
     *
     * @param owner The owner of this skill
     */
    public GrapesSkillSlayer(Skills owner) {
        super(owner, 40, new ItemBuilder(Material.IRON_SWORD)
                .setName("&cSlayer")
                .addEnchant(Enchantment.DAMAGE_ALL, 3)
                .build());
    }

    /**
     * This method returns the key.
     *
     * @return The key of this value.
     */
    @Override
    public String getKey() {
        return SkillTypes.SLAYER.getKey();
    }
}
