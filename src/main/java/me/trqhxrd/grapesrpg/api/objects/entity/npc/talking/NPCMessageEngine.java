package me.trqhxrd.grapesrpg.api.objects.entity.npc.talking;

import me.trqhxrd.grapesrpg.api.GrapesPlayer;
import me.trqhxrd.grapesrpg.api.attribute.Owneable;
import me.trqhxrd.grapesrpg.api.objects.entity.npc.GrapesStoryNPC;
import org.bukkit.command.CommandSender;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Consumer;
import java.util.stream.Collectors;

/**
 * This class manages messages of an NPC.
 * It can send messages. add messages and store messages.
 *
 * @author Trqhxrd
 */
public class NPCMessageEngine implements Owneable<GrapesStoryNPC> {

    /**
     * This field contains the owning npc.
     */
    private final GrapesStoryNPC owner;
    /**
     * This set contains all the different messages.
     */
    private final Set<NPCMessage> messages;
    /**
     * This boolean locks the interface from sending another message.
     * It is used to stop the NPC from saying multiple things at the same time.
     */
    private boolean inUse;

    /**
     * This method creates a new NPCMessageEngine for the owning NPC.
     *
     * @param owner The owner of this message engine.
     */
    public NPCMessageEngine(GrapesStoryNPC owner) {
        this(owner, new HashSet<>());
    }

    /**
     * This constructor creates a new message engine with an owner and a set of initial messages.
     *
     * @param owner    The owner of the set.
     * @param messages The initial messages for this NPCMessageEngine.
     */
    public NPCMessageEngine(GrapesStoryNPC owner, NPCMessage... messages) {
        this(owner, Arrays.stream(messages).collect(Collectors.toSet()));
    }

    /**
     * This method creates a new NPCMessageEngine for the owning NPC and also adds the messages from the set given.
     *
     * @param owner    The owner of this message engine.
     * @param messages A set of messages, that should be added. Please note, that the set will be copied to extract side-effects.
     */
    public NPCMessageEngine(GrapesStoryNPC owner, Set<NPCMessage> messages) {
        this.owner = owner;
        this.messages = new HashSet<>(messages);
        this.inUse = false;
    }

    /**
     * This method adds another message to the set of messages.
     *
     * @param message The message, that should be added.
     */
    public void addMessage(NPCMessage message) {
        this.messages.add(message);
    }

    /**
     * This method removes a message from the Set of messages.
     *
     * @param message The message, that should be removed.
     */
    public void removeMessage(NPCMessage message) {
        this.messages.remove(message);
    }

    /**
     * This method runs something for each message int eh set.
     *
     * @param consumer The method, that should be run.
     */
    public void forEach(Consumer<NPCMessage> consumer) {
        this.messages.forEach(consumer);
    }

    /**
     * This method returns a random message from the set and uses the different chances of the messages to give some messages a higher chance to be send.
     *
     * @return A random message from the set.
     */
    public NPCMessage getRandomMessageBiased() {
        int total = 0;
        for (NPCMessage message : this.messages) total += message.getChance();

        int random = ThreadLocalRandom.current().nextInt(total);
        for (NPCMessage message : this.messages) {
            total -= message.getChance();
            if (total <= random) return message;
        }
        try {
            throw new Exception("Something went wrong while picking a random message!");
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * This method sends a random message to the target given.
     * This method uses a random message, which was returned from {@link NPCMessageEngine#getRandomMessageBiased()}.
     *
     * @param target The target, who should receive the message.
     */
    public void sendRandomMessageBiased(CommandSender target) {
        this.getRandomMessageBiased().sendLines(this.owner, target);
    }

    /**
     * This method sends a random message to the target given.
     * This method uses a random message, which was returned from {@link NPCMessageEngine#getRandomMessageBiased()}.
     *
     * @param target The target, who should receive the message.
     */
    public void sendRandomMessageBiased(GrapesPlayer target) {
        this.getRandomMessageBiased().sendLines(this.owner, target);
    }

    /**
     * This method returns a random message from the set but all the messages have the same chance.
     *
     * @return A random message from the set.
     */
    public NPCMessage getRandomMessageEqualChance() {
        int random = ThreadLocalRandom.current().nextInt(this.messages.size());
        int index = 0;
        for (NPCMessage message : this.messages) {
            if (random == index++) return message;
        }
        try {
            throw new Exception("Something went wrong while picking a random message!");
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * This method sends a random message to the target given.
     * This method uses a random message, which was returned from {@link NPCMessageEngine#getRandomMessageEqualChance()} ()}.
     *
     * @param target The target, who should receive the message.
     */
    public void sendRandomMessageEqualChance(CommandSender target) {
        this.getRandomMessageEqualChance().sendLines(this.owner, target);
    }

    /**
     * This method sends a random message to the target given.
     * This method uses a random message, which was returned from {@link NPCMessageEngine#getRandomMessageEqualChance()} ()}.
     *
     * @param target The target, who should receive the message.
     */
    public void sendRandomMessageEqualChance(GrapesPlayer target) {
        this.getRandomMessageEqualChance().sendLines(this.owner, target);
    }

    /**
     * Getter for the set of messages.
     *
     * @return The set, which contains all the messages.
     */
    public Set<NPCMessage> getMessages() {
        return messages;
    }

    /**
     * Returns true, if the method.engine is currently in use.
     *
     * @return true, if the engine is currently used. Otherwise false.
     */
    public boolean isInUse() {
        return inUse;
    }

    /**
     * If this is set to true, the engine can't be used.
     *
     * @param inUse Whether the engine is used or not.
     */
    public void setInUse(boolean inUse) {
        this.inUse = inUse;
    }

    /**
     * This method returns the owner of this object.
     *
     * @return The owner of this object.
     */
    @Override
    public GrapesStoryNPC getOwner() {
        return owner;
    }
}
