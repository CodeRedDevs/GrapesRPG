package me.trqhxrd.grapesrpg.api.utils.clock;

import java.util.UUID;

/**
 * This abstract class is a ClockTask, that will be run delayed.
 * After it got run, it will be removed from the clock.
 * @author Trqhxrd
 */
public abstract class OneTimeTask implements ClockTask {

    /**
     * The delay of the task.
     * The task will be run that many iterations after it got added.
     */
    private final int delay;
    /**
     * This field stores the delay, that is left.
     */
    private int delayLeft;

    /**
     * This constructor creates a new one-time-task.
     *
     * @param delay This is the amount of iterations, that this task will wait before it runs it's run method.
     */
    public OneTimeTask(int delay) {
        this.delay = delay;
        this.delayLeft = delay;
    }

    /**
     * This method returns how many iterations from the clock will be ignored.
     *
     * @return The amount of ignored iterations.
     */
    public int getDelay() {
        return delay;
    }

    /**
     * Getter for how much delay is left until the clock executes the run-method.
     *
     * @return The amount of iterations left before the {@link OneTimeTask#run(UUID, Clock)}-method will be executed.
     */
    public int getDelayLeft() {
        return delayLeft;
    }

    /**
     * The method, that will be executed by the clock.
     *
     * @param operator The clock, which executes this method.
     */
    @Override
    public void execute(UUID uuid, Clock operator) {
        if (delayLeft-- <= 0) {
            this.run(uuid, operator);
            operator.removeTask(uuid);
        }
    }

    /**
     * This abstract method will be run after the {@link OneTimeTask#delayLeft} is 0.
     *
     * @param uuid     The id of the task in the clock.
     * @param operator The Clock, that executes this task.
     */
    public abstract void run(UUID uuid, Clock operator);
}
