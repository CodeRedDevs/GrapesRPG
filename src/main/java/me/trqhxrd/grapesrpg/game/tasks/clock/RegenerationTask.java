package me.trqhxrd.grapesrpg.game.tasks.clock;

import me.trqhxrd.grapesrpg.api.common.GrapesPlayer;
import me.trqhxrd.grapesrpg.api.utils.clock.Clock;
import me.trqhxrd.grapesrpg.api.utils.clock.ClockTask;
import org.bukkit.attribute.Attribute;

import java.util.Objects;

/**
 * This implementation of a {@link ClockTask} adds custom health regeneration.
 *
 * @author Trqhxrd
 */
public class RegenerationTask implements ClockTask {

    /**
     * The method, that will be executed by the clock.
     *
     * @param operator The clock, which executes this method.
     */
    @Override
    public void execute(Clock operator) {
        if (operator.getIteration() % 20 == 0) {
            GrapesPlayer.getPlayers().forEach(p -> {
                double maxHealth = Objects.requireNonNull(p.getWrappedObject().getAttribute(Attribute.GENERIC_MAX_HEALTH)).getValue();
                if (p.getWrappedObject().getHealth() < maxHealth) {
                    int foodLevel = p.getWrappedObject().getFoodLevel();
                    float saturation = p.getWrappedObject().getSaturation();
                    if (foodLevel >= 18) {
                        p.getWrappedObject().setHealth(Math.min(maxHealth, p.getWrappedObject().getHealth() + 1));
                        if (!(saturation - .5 >= 0)) p.getWrappedObject().setFoodLevel(foodLevel - 1);
                        p.getWrappedObject().setSaturation((float) Math.max(0, saturation - .5));
                    }
                }
            });
        }
    }
}
