package me.trqhxrd.grapesrpg.api.objects.entity.behavior;

import net.citizensnpcs.api.ai.event.CancelReason;
import net.citizensnpcs.api.ai.tree.BehaviorGoalAdapter;
import net.citizensnpcs.api.ai.tree.BehaviorStatus;
import net.citizensnpcs.api.npc.NPC;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class NPCAttackEntityGoal extends BehaviorGoalAdapter {

    private final NPC npc;
    private final Set<EntityType> targetTypes;
    private final boolean aggressive;
    private final int range;
    private Entity target;
    private boolean finished;
    private boolean killedTarget;

    public NPCAttackEntityGoal(NPC npc, Set<EntityType> targetTypes, boolean aggressive, int range) {
        this.npc = npc;
        this.targetTypes = targetTypes;
        this.aggressive = aggressive;
        this.range = range;
        this.target = null;
        this.finished = false;
        this.killedTarget = false;
    }

    @Override
    public void reset() {
        this.npc.getNavigator().cancelNavigation();
        this.target = null;
        this.finished = false;
        this.killedTarget = false;
    }

    @Override
    public BehaviorStatus run() {
        if (finished) {
            if (killedTarget) return BehaviorStatus.SUCCESS;
            else return BehaviorStatus.FAILURE;
        } else {
            if (this.npc.getEntity().getLocation().distance(this.target.getLocation()) >= this.range) {
                this.reset();
                return BehaviorStatus.FAILURE;
            } else return BehaviorStatus.RUNNING;
        }
    }

    @Override
    public boolean shouldExecute() {
        if (this.npc.isSpawned()) {
            if (target == null) {
                List<Entity> nearbyEntities = this.npc.getEntity().getNearbyEntities(this.range, this.range, this.range);
                List<Entity> possibleTargets = nearbyEntities.stream()
                        .filter(e -> e instanceof LivingEntity)
                        .filter(e -> this.targetTypes.contains(e.getType()))
                        .filter(e -> !e.hasMetadata("NPC"))
                        .collect(Collectors.toList());

                if (!possibleTargets.isEmpty()) {
                    this.target = possibleTargets.get(0);
                    this.npc.getNavigator().setTarget(this.target, this.aggressive);
                    this.npc.getNavigator().getLocalParameters().addSingleUseCallback(cancelReason -> {
                        if (cancelReason == CancelReason.TARGET_DIED) NPCAttackEntityGoal.this.killedTarget = true;
                        NPCAttackEntityGoal.this.reset();
                    });
                }
            } else if (this.target.getLocation().distance(this.npc.getEntity().getLocation()) >= this.range) this.reset();
        }
        return false;
    }
}
