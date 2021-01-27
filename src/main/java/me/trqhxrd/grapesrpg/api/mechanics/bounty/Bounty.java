package me.trqhxrd.grapesrpg.api.mechanics.bounty;

import me.trqhxrd.grapesrpg.Grapes;
import me.trqhxrd.grapesrpg.api.attribute.Serializable;
import me.trqhxrd.grapesrpg.api.common.GrapesPlayer;
import org.bukkit.Bukkit;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.util.*;

/**
 * This class represents a bounty on a players head.
 *
 * @author Trqhxrd
 */
public class Bounty implements Serializable<Bounty> {

    /**
     * This Set contains all open bounties.
     */
    private static final Set<Bounty> bounties = new HashSet<>();

    /**
     * This is the File, which stores all the data about this bounty.
     */
    private final transient File file;

    /**
     * The id of this Bounty.
     */
    private final UUID id;

    /**
     * The id of the targeted player.
     */
    private final UUID target;

    /**
     * The name of the creator of this bounty.
     * This is only used for cosmetic reasons.
     */
    private final String creator;

    /**
     * This long is the amount of money, the player, who completes this bounty, will get.
     */
    private final long reward;

    /**
     * This constructor creates a new bounty and adds it to the list of already existing bounties.
     *
     * @param creator The name of the creator of this bounty.
     * @param target  The targeted player of this bounty.
     * @param reward  The reward in coins.
     */
    public Bounty(String creator, UUID target, long reward) {
        this.creator = creator;
        this.target = target;
        this.reward = reward;
        this.id = UUID.randomUUID();
        this.file = new File(Grapes.getGrapes().getDataFolder(), "\\bounties\\" + id.toString() + ".json");

        bounties.add(this);
        this.save();
    }

    /**
     * This method reloads all bounty-files.
     */
    public static void loadBounties() {
        bounties.clear();
        File folder = new File(Grapes.getGrapes().getDataFolder(), "\\bounties");
        //noinspection ResultOfMethodCallIgnored
        folder.mkdirs();
        try {
            Files.list(folder.toPath()).forEach(f -> {
                try {
                    StringBuilder s = new StringBuilder();
                    Scanner scanner = new Scanner(f);
                    while (scanner.hasNextLine()) s.append(scanner.nextLine());
                    scanner.close();
                    Bounty b = Grapes.GSON.fromJson(s.toString(), Bounty.class);
                    Bounty.getBounties().add(b);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * This method returns the set of all available bounties.
     *
     * @return A list of bounties.
     */
    public static Set<Bounty> getBounties() {
        return bounties;
    }

    /**
     * This method returns all bounties, of which the target is the player with the uuid given.
     *
     * @param uuid UUID of the player, of which you want to get all the bounties.
     * @return A Set of all bounties for the given player.
     */
    public static Set<Bounty> getBounties(UUID uuid) {
        Set<Bounty> bounties = new HashSet<>();
        Bounty.getBounties().forEach(b -> {
            if (b.getTarget().equals(uuid)) bounties.add(b);
        });
        return bounties;
    }

    /**
     * This method deletes the bounties file and gives the player the amount of money, he is owed.
     *
     * @param player The player, who completed the bounty.
     */
    public void done(GrapesPlayer player) {
        player.getBalance().add(this.reward);
        player.sendMessage("&aYou got &e" + this.reward + "$ &afor killing &c" + Objects.requireNonNull(Bukkit.getPlayer(this.target)).getName() +
                " &7(Creator: " + this.getCreator() + ")");
        bounties.remove(this);
        //noinspection ResultOfMethodCallIgnored
        this.file.delete();
    }

    /**
     * Getter for the bounties creator.
     *
     * @return The bounties creator.
     */
    public String getCreator() {
        return creator;
    }

    /**
     * Getter for the bounties file.
     *
     * @return The bounties file.
     */
    public File getFile() {
        return file;
    }

    /**
     * Getter for the bounties id.
     *
     * @return The bounties id.
     */
    public UUID getId() {
        return id;
    }

    /**
     * Getter for the bounties target.
     *
     * @return The bounties target.
     */
    public UUID getTarget() {
        return target;
    }

    /**
     * Getter for the bounties reward.
     * @return The bounties rewars
     */
    public long getReward() {
        return reward;
    }

    /**
     * This method saves the bounties data into its file.
     */
    @SuppressWarnings("ResultOfMethodCallIgnored")
    public void save() {
        try {
            file.getParentFile().mkdirs();
            file.createNewFile();
            BufferedWriter writer = new BufferedWriter(new FileWriter(file));
            writer.write(Grapes.GSON.toJson(this));
            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * This method serializes an Object (t) into a String.
     *
     * @param bounty The Object, which you want to serialize.
     * @return The String containing all data about the object.
     */
    @Override
    public String serialize(Bounty bounty) {
        return Grapes.GSON.toJson(bounty);
    }

    /**
     * This method serializes the Object, from which it will be executed.
     *
     * @return The serialized object.
     */
    @Override
    public String serialize() {
        return this.serialize(this);
    }

    /**
     * This method is able to create an object from a serialized String.
     *
     * @param s The String you want to deserialize.
     * @return The Object.
     */
    @Override
    public Bounty deserialize(String s) {
        return Grapes.GSON.fromJson(s, Bounty.class);
    }
}
