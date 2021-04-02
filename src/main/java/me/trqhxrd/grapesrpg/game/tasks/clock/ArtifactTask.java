package me.trqhxrd.grapesrpg.game.tasks.clock;

import me.trqhxrd.grapesrpg.api.objects.item.artifact.Artifact;
import me.trqhxrd.grapesrpg.api.utils.clock.Clock;
import me.trqhxrd.grapesrpg.api.utils.clock.ClockTask;
import me.trqhxrd.grapesrpg.game.config.ArtifactConfig;

import java.util.UUID;

/**
 * This task saves all of the artifacts and also updates their locations.
 *
 * @author Trqhxrd
 */
public class ArtifactTask implements ClockTask {
    /**
     * The method, that will be executed by the clock.
     *
     * @param uuid     The task id of this task.
     * @param operator The clock, which executes this method.
     */
    @Override
    public void execute(UUID uuid, Clock operator) {
        if (operator.getIteration() % (20 * 5) == 0) Artifact.updateLocations();
        if (operator.getIteration() % (20 * 60) == 0) ArtifactConfig.saveArtifacts(false);
    }
}
