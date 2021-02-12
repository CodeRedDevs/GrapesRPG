package me.trqhxrd.grapesrpg.game;

import me.trqhxrd.grapesrpg.Grapes;
import me.trqhxrd.grapesrpg.api.utils.clock.Clock;
import me.trqhxrd.grapesrpg.game.tasks.clock.RegenerationTask;

/**
 * This is the main-clock for the GrapesRPG-Plugin.
 *
 * @author Trqhxrd
 */
public class GameClock extends Clock {

    /**
     * This constructor sets some initial values in the super-class and adds all the tasks.
     */
    public GameClock() {
        super(Grapes.getGrapes(), 0, 1);
        addTask(new RegenerationTask());
    }
}
