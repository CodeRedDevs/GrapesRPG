package me.trqhxrd.grapesrpg.api.objects.entity;

import me.trqhxrd.grapesrpg.api.objects.entity.behavior.NPCAttackEntityGoal;
import me.trqhxrd.grapesrpg.api.utils.Utils;
import net.citizensnpcs.api.ai.goals.WanderGoal;
import net.citizensnpcs.trait.GameModeTrait;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;

import java.util.*;

/**
 * This class is a special implementation of the GrapesEntity.
 * It represents all NPCs, that move and attack players.
 *
 * @author Trqhxrd
 */
// TODO: 26.04.2021 Add damage for npcs.
public class GrapesMob extends GrapesEntity {

    /**
     * A list of all mobs.
     */
    private static final List<GrapesMob> mobs = new ArrayList<>();
    /**
     * if this is set to true, the NPC will get a NameTag displayed over its head.
     */
    private final boolean nameTag;
    /**
     * The Name of the NPC.
     */
    private final String name;
    /**
     * Whether the NPC attacks other entities or if it just wanders around aimlessly.
     */
    private final Behavior behavior;
    /**
     * A Set of entityTypes, which will be attacked, if this NPCs Behavior is set to aggressive.
     *
     * @see Behavior
     * @see GrapesMob#behavior
     */
    private final Set<EntityType> targetEntityTypes;

    /**
     * This creates a new GrapesMob.
     *
     * @param entityType The Type of Mob, that should be created.
     * @param name       The name of the Mob.
     */
    public GrapesMob(EntityType entityType, String name) {
        this(entityType, name, Behavior.PASSIVE, new HashSet<>(), true);
    }

    /**
     * This creates a new GrapesMob.
     *
     * @param entityType    The Type of Mob, that should be created.
     * @param name          The name of the Mob.
     * @param behavior      The behavior of the NPC.
     * @param attackedTypes The types of entities, that should be attacked.
     * @param nameTag       Whether the NPC should have a NameTag or not.
     */
    public GrapesMob(EntityType entityType, String name, Behavior behavior, Set<EntityType> attackedTypes, boolean nameTag) {
        super(entityType, name);
        this.behavior = behavior;
        this.targetEntityTypes = (attackedTypes == null ? new HashSet<>() : attackedTypes);
        this.nameTag = nameTag;
        this.name = Utils.translateColorCodes(name);
        this.setup();
    }

    /**
     * This creates a new GrapesMob.
     *
     * @param entityType    The Type of Mob, that should be created.
     * @param name          The name of the Mob.
     * @param behavior      The behavior of the NPC.
     * @param attackedTypes The types of entities, that should be attacked.
     * @param location      The spot, where the NPC should be spawned.
     */
    public GrapesMob(EntityType entityType, String name, Location location, Behavior behavior, Set<EntityType> attackedTypes) {
        this(entityType, name, location, behavior, attackedTypes, true);
    }

    /**
     * This creates a new GrapesMob.
     *
     * @param entityType    The Type of Mob, that should be created.
     * @param name          The name of the Mob.
     * @param behavior      The behavior of the NPC.
     * @param attackedTypes The types of entities, that should be attacked.
     * @param nameTag       Whether the NPC should have a NameTag or not.
     * @param location      The spot, where the NPC should be spawned.
     */
    public GrapesMob(EntityType entityType, String name, Location location, Behavior behavior, Set<EntityType> attackedTypes, boolean nameTag) {
        super(entityType, name, location);
        this.behavior = behavior;
        this.targetEntityTypes = (attackedTypes == null ? new HashSet<>() : attackedTypes);
        this.nameTag = nameTag;
        this.name = Utils.translateColorCodes(name);
        this.setup();
    }

    /**
     * getter for a list of all mobs.
     * @return A list of all mobs.
     */
    public static List<GrapesMob> getMobs() {
        return mobs;
    }

    private void setup() {
        WanderGoal goal = WanderGoal.createWithNPCAndRange(this.getNPC(), 10, 10);
        goal.setDelay(100);
        switch (behavior) {
            case AGGRESSIVE:
                this.addGoal(new NPCAttackEntityGoal(this.getNPC(), this.targetEntityTypes, true, 16), 2);
            case PASSIVE:
                this.addGoal(goal, 1);
                break;
        }

        this.getNPC().getOrAddTrait(GameModeTrait.class).setGameMode(GameMode.SURVIVAL);
        this.getNPC().setName(this.name + " &7| &c❤️ " + ((LivingEntity) this.getNPC().getEntity()).getHealth() + " / " +
                Objects.requireNonNull(((LivingEntity) this.getNPC().getEntity()).getAttribute(Attribute.GENERIC_MAX_HEALTH)).getBaseValue());

        mobs.add(this);
    }

    /**
     * Getter for the NPCs behavior.
     * @return The NPCs behavior.
     */
    public Behavior getBehavior() {
        return behavior;
    }

    /**
     * Whether the NPC will display itÄs name using a nameTag.
     * @return Whether the NPC will display itÄs name using a nameTag.
     */
    public boolean hasNameTag() {
        return nameTag;
    }

    /**
     * Getter for the NPCs Name.
     * @return The NPCs Name.
     */
    public String getName() {
        return name;
    }

    /**
     * Getter for a Set of all targeted entityTypes.
     * @return A Set of all targeted entityTypes.
     */
    public Set<EntityType> getTargetEntityTypes() {
        return targetEntityTypes;
    }
}
