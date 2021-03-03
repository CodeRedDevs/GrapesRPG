package me.trqhxrd.grapesrpg.game.skills;

import me.trqhxrd.grapesrpg.Grapes;
import me.trqhxrd.grapesrpg.api.GrapesPlayer;
import me.trqhxrd.grapesrpg.api.objects.block.GrapesBlock;
import me.trqhxrd.grapesrpg.api.objects.block.GrapesBlockType;
import me.trqhxrd.grapesrpg.api.skill.Skill;
import me.trqhxrd.grapesrpg.api.skill.Skills;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ThreadLocalRandom;

/**
 * This class represents a skill, that is being leveled by breaking blocks.
 */
public class GrapesSkillMining extends Skill implements Listener {

    /**
     * This map stores all whitelisted blocks and their xp-value.
     */
    private static final Map<GrapesBlockType, Integer> levelTypes = new HashMap<>();

    static {
        levelTypes.put(GrapesBlockType.STONE, 1);
        levelTypes.put(GrapesBlockType.COAL_ORE, 3);
        levelTypes.put(GrapesBlockType.IRON_ORE, 5);
        levelTypes.put(GrapesBlockType.GOLD_ORE, 8);

        Bukkit.getPluginManager().registerEvents(new GrapesSkillMining(null), Grapes.getGrapes());
    }

    /**
     * This creates a new object of this skill.
     *
     * @param owner The owner of this skill.
     */
    public GrapesSkillMining(Skills owner) {
        super(owner, 3, Material.IRON_PICKAXE, "&bMining", 1);
    }

    /**
     * The handler method for the block-break-event.
     *
     * @param e A BlockBreakEvent.
     */
    @EventHandler(ignoreCancelled = true)
    public static void onBlockBreak(BlockBreakEvent e) {
        GrapesBlock block = GrapesBlock.getBlock(e.getBlock().getLocation());
        if (levelTypes.containsKey(block.getType())) {
            int random = ThreadLocalRandom.current().nextInt(0, 500);
            if (random == 0) {
                GrapesSkillMining mining = (GrapesSkillMining) GrapesPlayer.getByUniqueId(e.getPlayer().getUniqueId()).getSkills().getSkill(Skills.MINING);
                int xp = levelTypes.get(block.getType());
                mining.addXP(xp);
                mining.getOwner().getOwner().sendMessage("&aYou got &b" + xp + " &aXP!");
                Objects.requireNonNull(mining.getOwner().getOwner().getLocation().getWorld())
                        .playSound(mining.getOwner().getOwner().getLocation(), Sound.BLOCK_NOTE_BLOCK_XYLOPHONE, 1, 1);
            }
        }
    }

    @Override
    public String getKey() {
        return Skills.MINING;
    }
}
