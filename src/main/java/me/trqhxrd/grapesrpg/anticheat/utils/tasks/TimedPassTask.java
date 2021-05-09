package me.trqhxrd.grapesrpg.anticheat.utils.tasks;

import me.trqhxrd.grapesrpg.anticheat.api.pass.TimedPass;
import me.trqhxrd.grapesrpg.api.utils.clock.Clock;
import me.trqhxrd.grapesrpg.api.utils.clock.ClockTask;

import java.util.UUID;

/**
 * This task reduces the time left in timed passes.
 * @author Trqhxrd
 */
public class TimedPassTask implements ClockTask {
    /**
     * The method, that will be executed by the clock.
     *
     * @param uuid     The task id of this task.
     * @param operator The clock, which executes this method.
     */
    @Override
    public void execute(UUID uuid, Clock operator) {
        synchronized (TimedPass.getPasses()) {
            for (TimedPass pass : TimedPass.getPasses()) pass.tick();
        }
    }
}
