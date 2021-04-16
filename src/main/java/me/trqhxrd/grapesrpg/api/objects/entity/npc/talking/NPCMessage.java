package me.trqhxrd.grapesrpg.api.objects.entity.npc.talking;

import me.trqhxrd.grapesrpg.api.GrapesPlayer;
import me.trqhxrd.grapesrpg.api.objects.entity.npc.GrapesStoryNPC;
import me.trqhxrd.grapesrpg.api.utils.Utils;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * This class represents a bundle of lines, that can be sent by a NPC.
 *
 * @author Trqhxrd
 */
public class NPCMessage {

    /**
     * This is the default delay, that will be used if there is no delay entered for a line.
     */
    public static final int DEFAULT_DELAY = 10;
    /**
     * This is the chance, that this message will be chosen.
     * The chance for a message to be chosen is (chance / sum-of-all-chances)
     */
    private final int chance;
    /**
     * This field contains a list of all lines, that should be sent.
     */
    private final List<NPCLine> lines;

    /**
     * This constructor creates a new message, which
     *
     * @param chance The chance for this message to be chosen.
     * @param lines  An array of strings, that will be mapped to NPCLines and will get the default-delay as a delay.
     */
    public NPCMessage(int chance, String... lines) {
        this(chance, Arrays.stream(lines)
                .map(line -> new NPCLine(line, DEFAULT_DELAY))
                .collect(Collectors.toList()));
    }

    /**
     * This constructor creates a new message with the chance given and an Array of NPCLines, that will be send once the message is send.
     *
     * @param chance The chance for this message to be called.
     * @param lines  An array of lines, that will be send.
     */
    public NPCMessage(int chance, NPCLine... lines) {
        this(chance, Arrays.asList(lines));
    }

    /**
     * This constructor creates a new NPCMessage with the chance given and with lines, which are generated from a hashmap, which contains Strings, which are the message itself and
     * integers, which are the delays behind the message.
     *
     * @param chance The chance for this message to be send.
     * @param lines  A map of Strings and integers, which represent the messages.
     * @deprecated This is deprecated, because a map is unsorted, which means, that the lines could be mixed up.
     */
    @Deprecated
    public NPCMessage(int chance, Map<String, Integer> lines) {
        this(chance, lines.keySet()
                .stream()
                .map(line -> new NPCLine(line, lines.get(line)))
                .collect(Collectors.toList()));
    }

    /**
     * This constructor creates a new NPCMessage with the chance given and a list of lines.
     * Please note, that the list of lines will be copied to extract side-effects.
     *
     * @param chance The chance for this message to be chosen.
     * @param lines  A list of lines, that should be send once the message is displayed.
     */
    public NPCMessage(int chance, List<NPCLine> lines) {
        this.chance = chance;
        this.lines = new ArrayList<>(lines);
    }

    /**
     * This method sends the message to a certain player.
     *
     * @param sender The GrapesStoryNPC, which sends the message to the player.
     * @param target This is the target, which should receive the message.
     */
    public void sendLines(GrapesStoryNPC sender, CommandSender target) {
        // TODO: 13.04.2021 Play sound while sending lines.
        NPCMessageEngine engine = sender.getMessageEngine();
        if (!engine.isInUse()) {
            new Thread(() -> {
                engine.setInUse(true);
                int index = 0;
                synchronized (this.lines) {
                    for (NPCLine line : this.lines) {
                        target.sendMessage(Utils.toChatMessage("&a", sender.getName(), line.getText()));
                        if (index + 1 != this.lines.size()) try {
                            Thread.sleep(line.getDelay() * 50L);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        index++;
                    }
                }
                engine.setInUse(false);
            }).start();
        }
    }

    /**
     * This method sends the message to a certain player.
     *
     * @param sender The GrapesStoryNPC, which sends the message to the player.
     * @param target This is the target, which should receive the message.
     */
    public void sendLines(GrapesStoryNPC sender, GrapesPlayer target) {
        this.sendLines(sender, target.getWrappedObject());
    }

    /**
     * This method adds a certain line to the message.
     *
     * @param line  The text, that should be added.
     * @param delay The delay after the text was sent.
     */
    public void addLine(String line, int delay) {
        this.addLine(new NPCLine(line, delay));
    }

    /**
     * This method adds a certain line to the message.
     *
     * @param line The NPCLine, that should be added..
     */
    public void addLine(NPCLine line) {
        synchronized (this.lines) {
            this.lines.add(line);
        }
    }

    /**
     * Getter for the chance of this message.
     *
     * @return The chance of this message.
     */
    public int getChance() {
        return chance;
    }
}
