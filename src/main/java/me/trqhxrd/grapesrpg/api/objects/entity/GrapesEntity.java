package me.trqhxrd.grapesrpg.api.objects.entity;

import me.trqhxrd.grapesrpg.api.objects.entity.traits.GrapesTrait;
import me.trqhxrd.grapesrpg.api.utils.Wrapper;
import net.citizensnpcs.api.CitizensAPI;
import net.citizensnpcs.api.ai.Goal;
import net.citizensnpcs.api.ai.GoalController;
import net.citizensnpcs.api.ai.tree.Behavior;
import net.citizensnpcs.api.npc.NPC;
import net.citizensnpcs.api.trait.Trait;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Stream;

/**
 * This class is the parent of all Entities created by Grapes.
 * There are some extensions of this, which are special classes for concrete cases.
 *
 * @author Trqhxrd
 */
public class GrapesEntity extends Wrapper<NPC> {

    /**
     * This List contains all entities.
     */
    private static final List<GrapesEntity> entities = new ArrayList<>();

    /**
     * This constructor creates a new entity with predefined type and name and spawns it at {@code location}.
     *
     * @param type     The Type of the Entity. Example: {@code EntityType.ZOMBIE}.
     * @param name     The Name of the NPC. Color-Codes are supported.
     * @param location The location, where the NPC should be spawned.
     */
    public GrapesEntity(EntityType type, String name, Location location) {
        this(type, name);
        this.spawn(location);
    }

    /**
     * This constructor creates a new NPC without spawning it.
     *
     * @param entityType The type of the NPC.
     * @param name       The name of the NPC.
     */
    public GrapesEntity(EntityType entityType, String name) {
        super(CitizensAPI.getNPCRegistry().createNPC(entityType, name));
        this.getNPC().addTrait(GrapesTrait.class);
        // TODO: 18.04.2021 Move Zombies onto the target location instead of just close to it.
        entities.add(this);
    }

    /**
     * This method runs a certain method for every NPC.
     *
     * @param action The consumer, that should be applied to every NPC.
     */
    public static void forEach(Consumer<GrapesEntity> action) {
        entities.forEach(action);
    }

    /**
     * This method returns a stream, which contains all NPCs.
     *
     * @return A stream, which contains all NPCs.
     */
    public static Stream<GrapesEntity> stream() {
        return entities.stream();
    }

    /**
     * This method checks, if a NPC is modified by Grapes.
     *
     * @param npc The NPC, that will be checked.
     * @return true, if the NPC is modified by grapes.
     */
    public static boolean isGrapesNPC(NPC npc) {
        return npc.hasTrait(GrapesTrait.class);
    }

    /**
     * This method adds a goal to the NPC.
     * The NPC will always run one goal at a time.
     * It will run the one with the highest priority, that is possible to run.
     * If there is already a goal running, no new goal will be started.
     *
     * @param goal     The Goal, that should be added.
     * @param priority The priority of the goal. Must be larger than 0.
     */
    public void addGoal(Goal goal, int priority) {
        this.getGoalController().addGoal(goal, Math.max(1, priority));
    }

    /**
     * This method adds a goal to the NPC.
     * The NPC will always run one goal at a time.
     * It will run the one with the highest priority, that is possible to run.
     * If there is already a goal running, no new goal will be started.
     *
     * @param behavior The Goal, that should be added.
     * @param priority The priority of the goal. Must be larger than 0.
     */
    public void addBehavior(Behavior behavior, int priority) {
        this.getGoalController().addBehavior(behavior, priority);
    }

    /**
     * Getter for the NPCs GoalController.
     *
     * @return The NPCs GoalController.
     */
    public GoalController getGoalController() {
        return super.getWrappedObject().getDefaultGoalController();
    }

    /**
     * Returns true, if the NPC is spawned.
     *
     * @return true -> NPC is spawned. false -> NPC is not spawned.
     */
    public boolean isSpawned() {
        return super.getWrappedObject().isSpawned();
    }

    /**
     * This method spawns the NPC.
     * If the NPC is already spawned, nothing happens.
     *
     * @param location The location, where the NPC should be spawned.
     * @return The return value tells you if the NPC was spawned successfully. True means the NPC was spawned successfully.
     */
    public boolean spawn(Location location) {
        if (!this.isSpawned()) {
            super.getWrappedObject().spawn(location);
            return true;
        }
        return false;
    }

    /**
     * This method despawns the NPC.
     * If the NPC is already despawned, nothing happens.
     *
     * @return The return value tells you if the NPC was despawned successfully. True means the NPC was despawned successfully.
     */
    public boolean despawn() {
        if (this.isSpawned()) {
            super.getWrappedObject().despawn();
            return true;
        }
        return false;
    }

    /**
     * This method removes a trait from the NPC.
     *
     * @param traitClass The class of the trait, that should be removed.
     */
    public void removeTrait(Class<? extends Trait> traitClass) {
        this.getWrappedObject().removeTrait(traitClass);
    }

    /**
     * This method gets a certain trait from the NPC.
     * If the trait does not exist, there will be a new one created for this NPC.
     *
     * @param traitClass The class of the trait, that you want to get.
     * @return The trait, which corresponds to the class given.
     */
    public Trait getTrait(Class<? extends Trait> traitClass) {
        return super.getWrappedObject().getOrAddTrait(traitClass);
    }

    /**
     * Getter for the entity, which is controlled by the GoalController, etc.
     *
     * @return The Bukkit-Entity, which corresponds to this NPC.
     */
    public Entity getEntity() {
        return this.getWrappedObject().getEntity();
    }

    /**
     * The actual CitizensNPC.
     *
     * @return The actual CitizensNPC.
     */
    public NPC getNPC() {
        return this.getWrappedObject();
    }
}
