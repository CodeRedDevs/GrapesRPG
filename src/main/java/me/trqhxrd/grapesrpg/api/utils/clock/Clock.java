package me.trqhxrd.grapesrpg.api.utils.clock;

import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * A clock is a {@link BukkitRunnable}, which will be run in a loop.
 * You can create a Clock using the {@link Clock#Clock(Plugin, int, int)}-constructor.
 * If you want to add a task you can either use the {@link Clock#Clock(Plugin, int, int, ClockTask...)}-constructor or you can use the {@link Clock#addTask(ClockTask)}-method.
 * The method can be run, while the clock is also running. In that case, the task will be added before the next iteration is started.
 *
 * @author Trqhxrd
 */
public class Clock extends BukkitRunnable {

    /**
     * The Plugin, which registers the {@link BukkitRunnable}.
     */
    private final Plugin plugin;
    /**
     * The delay before the first run of the clock.
     */
    private final int delay;
    /**
     * The delay between the iterations of the clock.
     */
    private final int period;
    /**
     * A list of all the tasks, that the clock will execute each iteration.
     * Tasks can be added using {@link Clock#addTask(ClockTask)}.
     */
    private final Map<UUID, ClockTask> tasks;
    /**
     * This long starts at 0.
     * After each iteration, this long will get bigger by 1.
     * In case it reaches {@link Long#MAX_VALUE}, it will be set to {@link Long#MIN_VALUE}.
     */
    private long iteration;
    /**
     * If the clock is running, this is set to true.
     */
    private boolean started;

    /**
     * This Constructor creates a simple clock, which can be started using {@link Clock#start()}.
     *
     * @param plugin The plugin, which "owns" the clock. This is needed for registering the clock.
     * @param delay  The delay before all of the clock-tasks will be executed for the first time.
     * @param period The delay between two iterations.
     */
    public Clock(Plugin plugin, int delay, int period) {
        this.plugin = plugin;
        this.delay = delay;
        this.period = period;
        this.started = false;
        this.tasks = new HashMap<>();
    }

    /**
     * This Constructor creates a simple clock, which can be started using {@link Clock#start()}.
     *
     * @param plugin       The plugin, which "owns" the clock. This is needed for registering the clock.
     * @param delay        The delay before all of the clock-tasks will be executed for the first time.
     * @param period       The delay between two iterations.
     * @param initialTasks All tasks given in this array will be added to the clock using {@link Clock#addTask(ClockTask)}.
     */
    public Clock(Plugin plugin, int delay, int period, ClockTask... initialTasks) {
        this(plugin, delay, period);
        for (ClockTask task : initialTasks) this.addTask(task);
    }

    /**
     * This method can be used to add tasks to the Clock while its running.
     *
     * @param task The task, which you want to add.
     * @return This returns the id of the task.
     */
    public UUID addTask(ClockTask task) {
        UUID uuid = UUID.randomUUID();
        synchronized (tasks) {
            tasks.put(uuid, task);
            return uuid;
        }
    }

    /**
     * This method removes the task with the task-id given.
     *
     * @param uuid The id of the task, that you want to remove.
     * @return Returns true if the task was removed successfully.
     */
    public boolean removeTask(UUID uuid) {
        synchronized (tasks) {
            boolean b = tasks.containsKey(uuid);
            tasks.remove(uuid);
            return b;
        }
    }

    /**
     * This method starts the clock.
     * It only starts, if the clock isn't already running.
     */
    public void start() {
        if (!this.started) {
            this.runTaskTimer(plugin, delay, period);
            this.started = true;
        }
    }

    /**
     * Getter for the clocks owning plugin.
     *
     * @return The clocks owning plugin.
     */
    public Plugin getPlugin() {
        return plugin;
    }

    /**
     * Getter for the clocks initial delay.
     *
     * @return The Clocks initial delay.
     */
    public int getDelay() {
        return delay;
    }

    /**
     * Getter for the clocks repeating delay.
     *
     * @return The clocks repeating delay.
     */
    public int getPeriod() {
        return period;
    }

    /**
     * Getter for the clocks iteration.
     *
     * @return Teh Clocks iteration.
     */
    public long getIteration() {
        return iteration;
    }

    /**
     * Setter for the clock iteration.
     *
     * @param iteration The clocks new iteration.
     */
    protected void setIteration(long iteration) {
        this.iteration = iteration;
    }

    /**
     * Getter for the clocks list of tasks.
     *
     * @return Returns an unmodifiable list of all tasks.
     */
    public Map<UUID, ClockTask> getTasks() {
        synchronized (tasks) {
            return Map.copyOf(tasks);
        }
    }

    /**
     * The default-method, which executes the tasks.
     */
    @Override
    public void run() {
        synchronized (tasks) {
            tasks.forEach((uuid, t) -> t.execute(uuid, this));
        }
        if (iteration < Long.MAX_VALUE) iteration++;
        else iteration = Long.MIN_VALUE;
    }
}
