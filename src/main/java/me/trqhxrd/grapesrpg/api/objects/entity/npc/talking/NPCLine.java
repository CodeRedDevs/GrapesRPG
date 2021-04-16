package me.trqhxrd.grapesrpg.api.objects.entity.npc.talking;

/**
 * This class contains a String, that will be sent and a delay.
 * The the next message will be send by "delay" ticks later.
 *
 * @author Trqhxrd
 */
public class NPCLine {

    /**
     * This is the raw message, which will be sent to the player.
     */
    private final String text;
    /**
     * The delay in ticks between this message and the next message.
     * If this line is the last one of the message, the delay will be ignored.
     */
    private final int delay;

    /**
     * This constructor creates a new NPCLine with a text and a delay.
     *
     * @param text  The text, that should be sent.
     * @param delay The delay between this line and the next line.
     */
    public NPCLine(String text, int delay) {
        this.text = text;
        this.delay = delay;
    }

    /**
     * Getter for the raw text of this line.
     *
     * @return The raw text of this line.
     */
    public String getText() {
        return text;
    }

    /**
     * Getter for the delay between this line and the next one.
     *
     * @return The delay between this line and the next one.
     */
    public int getDelay() {
        return delay;
    }
}
