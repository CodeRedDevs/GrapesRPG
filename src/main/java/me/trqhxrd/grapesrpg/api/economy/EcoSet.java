package me.trqhxrd.grapesrpg.api.economy;

import me.trqhxrd.grapesrpg.api.GrapesPlayer;
import me.trqhxrd.grapesrpg.api.attribute.Owneable;
import me.trqhxrd.grapesrpg.api.attribute.Savable;
import me.trqhxrd.grapesrpg.api.utils.Utils;
import me.trqhxrd.grapesrpg.game.config.GrapesConfig;

/**
 * The EcoSet stores all information about the players cash-data.
 * There is a wallet, which can be accessed using the pay command or a trading menu and a bank account, which also stores the players money.
 * The wallet will be lost if the player dies while the bank account stays the same.
 * That's why the player should always deposit his money in the bank account.
 *
 * @author Trqhxrd
 */
public class EcoSet implements Owneable<GrapesPlayer>, Savable {

    /**
     * This field stores the owner of this EcoSet.
     */
    private final GrapesPlayer owner;
    /**
     * This field stores the amount of money, that the player currently has on him.
     */
    private long wallet;
    /**
     * This field stores the amount of money, that the player has safely deposited in the bank.
     */
    private long bank;

    /**
     * This constructor creates a new EcoSet and automatically loads the values from the config.
     * If no economy data is available the bank account will be 0 and the wallet will contain the start-amount of cash specified in the main config.
     *
     * @param owner The owner of this EcoSet. If entered null, there will be an error thrown.
     */
    public EcoSet(GrapesPlayer owner) {
        this.owner = owner;
        if (this.owner.getFile().contains("economy.wallet")) this.wallet = this.owner.getFile().getLong("economy.wallet");
        else this.wallet = GrapesConfig.getInstance().getStarterCash();
        if (this.owner.getFile().contains("economy.bank")) this.wallet = this.owner.getFile().getLong("economy.bank");
        else this.bank = 0;
        this.save();
    }

    /**
     * This method just checks, if the player has enough money to pay a specific amount without removing the amount from his wallet.
     *
     * @param amount The amount, for which you want to check if it can be payed.
     * @return true if the player has enough cash to pay the amount given. Otherwise false.
     */
    public boolean wouldBeInDeptIfPayed(long amount) {
        return this.wallet - amount < 0;
    }

    /**
     * This method sends a certain amount of money to another player.
     *
     * @param target The player who is supposed to receive the money.
     * @param amount The amount, that should be sent.
     * @return Whether the money was actually sent. True if the payment was successful. Otherwise false.
     */
    public boolean pay(GrapesPlayer target, long amount) {
        if (amount <= 0) return false;
        if (!this.wouldBeInDeptIfPayed(amount)) {
            if (target != null) {
                target.getEcoSet().addWallet(amount);
                this.addWallet(-amount);
                return true;
            }
        }
        return false;
    }

    /**
     * This method adds a certain amount of cash to the wallet.
     * If you want to remove money from the wallet just enter a negative number.
     *
     * @param amount The amount, that should be added to the players wallet.
     */
    public void addWallet(long amount) {
        this.wallet += amount;
    }

    /**
     * This method adds a certain amount of cash to the bank account.
     * If you want to remove money from the bank account just enter a negative number.
     *
     * @param amount The amount, that should be added to the players bank account.
     */
    public void addBank(long amount) {
        this.bank += amount;
    }

    /**
     * This method moves a certain amount of money from the players wallet to his bank account.
     * If he doesn't have enough money in his wallet the action will be cancelled.
     *
     * @param amount The amount, that should be deposited.
     * @return Whether the deposition was successful. True if it was. Otherwise false.
     */
    public boolean deposit(int amount) {
        if (!this.wouldBeInDeptIfPayed(amount)) {
            this.addWallet(-amount);
            this.addBank(amount);
            return true;
        }
        return false;
    }

    /**
     * This method removes a certain amount of money from the player's bank account and adds it to his wallet.
     *
     * @param amount The amount of money, that should be withdrawn.
     * @return Whether the withdrawal was successful. True if it was. Otherwise false.
     */
    public boolean withdraw(int amount) {
        if (this.bank - amount >= 0) {
            this.addBank(-amount);
            this.addWallet(amount);
            return true;
        }
        return false;
    }

    /**
     * This method returns a string, that contains the players economy data.
     * This string is also able to contain color codes and is only used as a chat status about the player's wallet and bank account.
     *
     * @return A chat-message, which gives information about the player's money.
     */
    public String getBalanceFormatted() {
        return Utils.translateColorCodes("&aYou &ehave &c" + this.wallet +
                " &ein your wallet and &c" + this.bank + " &ein your bank-account!");
    }

    /**
     * Getter for the amount of money in the wallet of the player.
     *
     * @return The amount of money in the wallet of the player.
     */
    public long getWallet() {
        return wallet;
    }

    /**
     * Setter for the amount of money in the player's wallet.
     *
     * @param wallet The new amount of money, that should be in the players wallet.
     */
    public void setWallet(long wallet) {
        this.wallet = wallet;
    }

    /**
     * Getter for the amount of money in the player's bank account.
     *
     * @return The amount of money in the player's bank account.
     */
    public long getBank() {
        return bank;
    }

    /**
     * Setter for the amount of money in the player's bank account.
     *
     * @param bank The new amount of money in the player's bank account.
     */
    public void setBank(long bank) {
        this.bank = bank;
    }

    /**
     * This method returns the owner of this object.
     *
     * @return The owner of this object.
     */
    @Override
    public GrapesPlayer getOwner() {
        return owner;
    }

    /**
     * This method will save the current state of this class in some way.
     *
     * @param flush If flush is true, the changes will automatically be written to a file.
     */
    @Override
    public void save(boolean flush) {
        this.owner.getFile().set("economy.wallet", this.wallet);
        this.owner.getFile().set("economy.bank", this.bank);
        if (flush) this.owner.getFile().save();
    }

    /**
     * This method just runs {@code this.save(true);}, which will save the state of this class and automatically write it to the file.
     * This method has to be overwritten tho.
     */
    @Override
    public void save() {
        this.save(true);
    }
}
