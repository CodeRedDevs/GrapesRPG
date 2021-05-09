package me.trqhxrd.grapesrpg.anticheat.api;

import com.google.common.annotations.Beta;
import me.trqhxrd.grapesrpg.anticheat.GrapesAntiCheat;
import me.trqhxrd.grapesrpg.anticheat.api.check.Check;
import me.trqhxrd.grapesrpg.anticheat.api.check.Level;
import me.trqhxrd.grapesrpg.anticheat.api.data.ACData;
import me.trqhxrd.grapesrpg.anticheat.api.logging.CheckResult;
import me.trqhxrd.grapesrpg.anticheat.api.pass.Pass;
import me.trqhxrd.grapesrpg.api.utils.Wrapper;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.permissions.Permission;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * The user-class contains a player and additional stuff regarding the anticheat.
 *
 * @author Trqhxrd
 */
public class User extends Wrapper<Player> {

    /**
     * This field contains a list of all users.
     */
    private static final List<User> users = new ArrayList<>();
    /**
     * This field contains a set of all passes a player has.
     */
    private final List<Pass> passes = new ArrayList<>();
    /**
     * This field contains a Map of all results of failed checks.
     */
    private final Map<Long, CheckResult> failedChecks = new HashMap<>();

    /**
     * This constructor creates a new Wrapper, with the wrappedObject as it's start-parameter.
     *
     * @param wrappedObject The Object, which you want to wrap this Wrapper around.
     */
    public User(Player wrappedObject) {
        super(wrappedObject);
        users.add(this);
    }

    /**
     * This static method returns the user-object for a specific player.
     *
     * @param player The player for who you want to get the user-object.
     * @return The user object of the player.
     */
    public static User get(Player player) {
        return users.stream()
                .filter(u -> u.getWrappedObject().getUniqueId().equals(player.getUniqueId()))
                .limit(1)
                .collect(Collectors.toList())
                .get(0);
    }

    /**
     * Getter for a list of all users.
     *
     * @return A list of all users.
     */
    public static List<User> getUsers() {
        return users;
    }

    /**
     * This method runs a check for this user.
     *
     * @param check  The check, that should be run.
     * @param data   This field can contain additional data for the check.
     * @param store  Whether the check-result should be stored if it was failed.
     * @param notify Whether the admins should be notified if the player failed the check.
     * @param <D>    The type of ACData.
     * @return The result of the check.
     */
    @SuppressWarnings("unchecked")
    public <D extends ACData> CheckResult fire(Check<D> check, D data, boolean store, boolean notify) {
        Pass pass = this.getPass(check.getID());
        if (pass == null || !pass.bypass()) {
            Level level = check.fire(this, data);
            CheckResult result = new CheckResult(this, (Class<? extends Check<D>>) check.getClass(), level);
            if (store && level != Level.PASSED) {
                failedChecks.put(System.currentTimeMillis(), result);
                if (notify) GrapesAntiCheat.log("&c" + this.getWrappedObject().getName() + " &ehas failed the &c" + check.getName() + "&e Level: &c" + level.name() + "&e!");
            }
            return result;
        }
        return new CheckResult(this, (Class<? extends Check<D>>) check.getClass(), Level.PASSED);
    }

    /**
     * This method returns the first pass, that can be used to bypass a certain check.
     *
     * @param checkID The ID of the check, that you want to bypass.
     * @return If there is a pass, it will return the pass. otherwise it will return null
     */
    private Pass getPass(String checkID) {
        synchronized (this.passes) {
            for (Pass pass : this.passes)
                if (pass.getCheck().equals(checkID))
                    return pass;
        }
        return null;
    }

    /**
     * This method adds a pass to the players list of passes.
     *
     * @param pass The pass, that you want to add.
     */
    public void addPass(Pass pass) {
        synchronized (this.passes) {
            this.passes.add(pass);
        }
    }

    /**
     * This method adds a pass to the list of passes.
     *
     * @param passClass The type of pass, that you want to add.
     * @param check     The id of the check, that you want to bypass.
     * @param args      This is space for additional arguments, that are required to create the pass.
     * @return The pass, that was created.
     */
    @Beta
    public Pass addPass(Class<? extends Pass> passClass, String check, Object... args) {
        try {
            Object[] arguments = new Object[2 + args.length];
            Class<?>[] constructorArgs = new Class[2 + args.length];
            constructorArgs[0] = passClass;
            constructorArgs[1] = check.getClass();
            for (int i = 0; i < args.length; i++) constructorArgs[i + 2] = args[i].getClass();

            arguments[0] = passClass;
            arguments[1] = check;
            System.arraycopy(args, 0, arguments, 2, args.length);

            Pass p = passClass.getConstructor(constructorArgs).newInstance(arguments);
            this.addPass(p);
            return p;
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Getter for the name of the player.
     *
     * @return The name of the player.
     */
    public String getName() {
        return this.getWrappedObject().getName();
    }

    /**
     * Getter for the location of the player.
     *
     * @return The location of the player.
     */
    public Location getLocation() {
        return this.getWrappedObject().getLocation();
    }

    /**
     * Checks if the player has a certain permission
     *
     * @return Whether the player has a certain permission.
     */
    public boolean hasPermission(String perm) {
        return this.getWrappedObject().hasPermission(perm);
    }

    /**
     * Checks if the player has a certain permission
     *
     * @return Whether the player has a certain permission.
     */
    public boolean hasPermission(Permission perm) {
        return this.getWrappedObject().hasPermission(perm);
    }

    /**
     * Getter for the inventory of the player.
     *
     * @return The inventory of the player.
     */
    public PlayerInventory getInventory() {
        return this.getWrappedObject().getInventory();
    }

    /**
     * Getter for a list of checks, that the player failed.
     *
     * @return A list of failed checks.
     */
    public Map<Long, CheckResult> getFailedChecks() {
        return failedChecks;
    }

    /**
     * Getter for the gamemode of the player.
     *
     * @return The gamemode of the player.
     */
    public GameMode getGameMode() {
        return this.getWrappedObject().getGameMode();
    }
}
