package me.trqhxrd.grapesrpg.api.utils.clock;

/**
 * Any class, which implements this interface, can be used in a clock.
 *
 * @author Trqhxrd
 */
public interface ClockTask {

    /**
     * The method, that will be executed by the clock.
     *
     * @param operator The clock, which executes this method.
     */
    void execute(Clock operator);
}
