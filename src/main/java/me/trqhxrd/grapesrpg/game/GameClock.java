package me.trqhxrd.grapesrpg.game;

import me.trqhxrd.grapesrpg.Grapes;
import me.trqhxrd.grapesrpg.api.utils.clock.Clock;
import me.trqhxrd.grapesrpg.game.mechanics.alchemy.AlchemyTask;
import me.trqhxrd.grapesrpg.game.tasks.clock.ArtifactTask;
import me.trqhxrd.grapesrpg.game.tasks.clock.RegenerationTask;
import me.trqhxrd.grapesrpg.game.tasks.clock.SaveTask;

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
        this.addTask(new RegenerationTask());
        this.addTask(new SaveTask());
        this.addTask(new AlchemyTask());
        this.addTask(new ArtifactTask());
    }
}
