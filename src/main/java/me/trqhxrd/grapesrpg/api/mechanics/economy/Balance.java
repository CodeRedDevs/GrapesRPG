package me.trqhxrd.grapesrpg.api.mechanics.economy;

import me.trqhxrd.grapesrpg.Grapes;
import me.trqhxrd.grapesrpg.api.attribute.Serializable;
import me.trqhxrd.grapesrpg.api.common.GrapesPlayer;

/**
 * This class represents a players balance.
 *
 * @author Trqhxrd
 */
public class Balance implements Serializable<Balance> {

    /**
     * This constant represents the starter-money for players, who were never on the server before.
     */
    private static final long START_MONEY = 10000;

    /**
     * This field contains the Player, who owns this Balance-Object
     */
    private final transient GrapesPlayer player;

    /**
     * The actual balance of the player.
     */
    private long money;

    /**
     * This constructor creates a new Balance-Object with the Player as its owner and the money as the start money.
     *
     * @param player The Owner of this Object.
     * @param money  The start-balance.
     */
    public Balance(GrapesPlayer player, long money) {
        this.player = player;
        this.money = money;
    }

    /**
     * This constructor creates a new Balance-Object with the player as it's owner and {@link Balance#START_MONEY} as its start-balance.
     *
     * @param player The owner of this Object.
     */
    public Balance(GrapesPlayer player) {
        this(player, START_MONEY);
    }

    /**
     * Getter for the Balances Owner.
     *
     * @return Teh balances owner.
     */
    public GrapesPlayer getPlayer() {
        return player;
    }

    /**
     * Returns the actual value of the players balance.
     *
     * @return The amount of money a player has.
     */
    public long getMoney() {
        return money;
    }

    /**
     * This method sets the players actual amount of money.
     *
     * @param money The players new balance.
     */
    public void setMoney(long money) {
        this.money = money;
    }

    /**
     * This method adds the given amount to the players balance.
     *
     * @param change The amount, that will be added.
     */
    public void add(long change) {
        this.money = this.money + change;
    }

    /**
     * This method removes the given amount of money from the players balance.
     *
     * @param change The amount of money, you want to remove.
     */
    public void subtract(long change) {
        this.add(-change);
    }

    /**
     * This method checks, if the player could pay the given amount, without going into debt.
     *
     * @param requirement The amount of money, for which you want to check, if the player has enough.
     * @return returns true, if the player is able to pay the given amount, without going into dept.
     */
    public boolean hasEnough(long requirement) {
        return this.money - requirement >= 0;
    }

    /**
     * Returns true, if the players balance is below 0.
     *
     * @return true, if the player can afford to pay the given amount.
     */
    public boolean isInDept() {
        return this.money < 0;
    }

    /**
     * This method serializes an Object (t) into a String.
     *
     * @param balance The Object, which you want to serialize.
     * @return The String containing all data about the object.
     */
    @Override
    public String serialize(Balance balance) {
        return Grapes.GSON.toJson(balance);
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
    public Balance deserialize(String s) {
        return Grapes.GSON.fromJson(s, Balance.class);
    }
}
