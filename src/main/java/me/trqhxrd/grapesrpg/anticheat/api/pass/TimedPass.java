package me.trqhxrd.grapesrpg.anticheat.api.pass;

import me.trqhxrd.grapesrpg.anticheat.api.User;
import me.trqhxrd.grapesrpg.api.attribute.Tickable;

import java.util.ArrayList;
import java.util.List;

/**
 * This type of pass will allow a player to bypass checks as long as the timer in the object is above 0.
 *
 * @author Trqhxrd
 */
public class TimedPass extends Pass implements Tickable {

    /**
     * This list contains all the time-passes.
     * It is used for automatically reducing the timer of the passes.
     */
    private static final List<TimedPass> passes = new ArrayList<>();
    /**
     * This field stores the amount of time left until the pass invalidates itself.
     * The time is measured in seconds.
     */
    private int timeLeft;

    /**
     * This constructor creates a new Pass.
     *
     * @param id       The ID of check, that can be bypassed.
     * @param owner    The Player, who can bypass the check.
     * @param timeLeft The amount of time left until the check invalidates itself. (seconds)
     */
    public TimedPass(String id, User owner, int timeLeft) {
        super(id, owner);
        this.timeLeft = timeLeft;
        passes.add(this);
    }

    /**
     * Getter for the list of all passes.
     *
     * @return A list containing all passes.
     */
    public static List<TimedPass> getPasses() {
        return passes;
    }

    /**
     * This method checks, if the pass is invalid or if there is still time left.
     *
     * @return true {@literal ->} The player can bypass the check.
     */
    @Override
    public boolean bypass() {
        return this.timeLeft > 0;
    }

    /**
     * This method reduces the timer by one.
     */
    @Override
    public void tick() {
        if (this.timeLeft > 0) this.timeLeft--;
    }

    /**
     * getter for the amount of time left.
     *
     * @return The amount of time left.
     */
    public int getTimeLeft() {
        return timeLeft;
    }

    /**
     * Setter for the amount of time left.
     *
     * @param timeLeft The amount of time left.
     */
    protected void setTimeLeft(int timeLeft) {
        this.timeLeft = timeLeft;
    }
}
