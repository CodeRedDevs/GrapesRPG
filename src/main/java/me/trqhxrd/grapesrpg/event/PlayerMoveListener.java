package me.trqhxrd.grapesrpg.event;

import me.trqhxrd.grapesrpg.Grapes;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.SoundCategory;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.concurrent.ThreadLocalRandom;

/**
 * This class handles players tripping.
 *
 * @author Trqhxrd
 */
public class PlayerMoveListener implements Listener {

    /**
     * This constructor registers the listener
     */
    public PlayerMoveListener() {
        Bukkit.getPluginManager().registerEvents(this, Grapes.getGrapes());
    }

    /**
     * The Handler-Method.
     *
     * @param e A PlayerMoveEvent.
     */
    @EventHandler
    public void onMove(PlayerMoveEvent e) {
        Player p = e.getPlayer();

        if (!p.hasPermission("grapes.canceltripping")) {
            if (p.getInventory().getBoots() == null) {
                if (e.getFrom().getX() != e.getTo().getX() || e.getFrom().getZ() != e.getTo().getZ()) {
                    // Chance between 0 and 1,000,000
                    int random = ThreadLocalRandom.current().nextInt(0, 100000000);
                    if (random == 1) {
                        // Execute tripping
                        p.setHealth(Math.max(0, p.getHealth() - 4));
                        p.sendTitle("§c§lOOF", "§aYou tripped!", 0, 20, 20);
                        p.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 60, 10, true, false, false));
                        p.addPotionEffect(new PotionEffect(PotionEffectType.SLOW_DIGGING, 60, 10, true, false, false));
                        p.getWorld().playSound(p.getEyeLocation(), Sound.ENTITY_ELDER_GUARDIAN_CURSE, SoundCategory.MASTER, ((float) .1), 1);
                    }
                }
            }
        }
    }
}
