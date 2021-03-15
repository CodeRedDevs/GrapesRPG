package me.trqhxrd.grapesrpg.game.tasks.clock;

import me.trqhxrd.grapesrpg.api.GrapesPlayer;
import me.trqhxrd.grapesrpg.api.utils.clock.Clock;
import me.trqhxrd.grapesrpg.api.utils.clock.ClockTask;
import me.trqhxrd.grapesrpg.game.config.BlockData;

import java.util.UUID;

/**
 * This task just saves the config every 5 minutes
 */
public class SaveTask implements ClockTask {
    /**
     * The method, that will be executed by the clock.
     *
     * @param uuid     The task id of this task.
     * @param operator The clock, which executes this method.
     */
    @Override
    public void execute(UUID uuid, Clock operator) {
        if (operator.getIteration() % (20 * 60 * 5) == 0) {
            BlockData.getInstance().save();
            GrapesPlayer.saveAll();
        }
    }
}
