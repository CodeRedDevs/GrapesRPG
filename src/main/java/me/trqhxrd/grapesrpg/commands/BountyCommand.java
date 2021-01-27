package me.trqhxrd.grapesrpg.commands;

import me.trqhxrd.grapesrpg.Grapes;
import me.trqhxrd.grapesrpg.api.common.GrapesPlayer;
import me.trqhxrd.grapesrpg.api.mechanics.bounty.Bounty;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

/**
 * This class handles the bounty-command.
 * @author Trqhxrd
 */
public class BountyCommand implements CommandExecutor {

    /**
     * This constructor registers the command to the server.
     * Only execute once.
     */
    public BountyCommand() {
        Grapes.getGrapes().getCommand("bounty").setExecutor(this);
    }

    /**
     * Executes the given command, returning its success.
     * <br>
     * If false is returned, then the "usage" plugin.yml entry for this command
     * (if defined) will be sent to the player.
     *
     * @param sender  Source of the command
     * @param command Command which was executed
     * @param label   Alias of the command which was used
     * @param args    Passed command arguments
     * @return true if a valid command, otherwise false
     */
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender.hasPermission("grapes.command.bounty")) {
            if (args.length == 1) {
                if (args[0].equalsIgnoreCase("list")) {
                    List<Bounty> bounties = new ArrayList<>(Bounty.getBounties());
                    bounties.sort((o1, o2) -> {
                        if (o1.getReward() > o2.getReward()) return -1;
                        else if (o1.getReward() == o2.getReward()) return 0;
                        else return 1;
                    });
                    bounties.stream().limit(10).forEach(b -> {
                        OfflinePlayer op = Bukkit.getOfflinePlayer(b.getTarget());
                        sender.sendMessage("§a" + op.getName() + "   §e$" + b.getReward() + "   §7(created by: " + b.getCreator() + ")");
                    });
                    sender.sendMessage("§7Use §e/bounty list <page> §7to see more bounties!");
                }
            } else if (args.length == 3) {
                if (args[0].equalsIgnoreCase("create")) {
                    OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayerIfCached(args[1]);
                    int price = Integer.parseInt(args[2]);
                    if (price > 0) {
                        if (offlinePlayer != null) {
                            try {
                                if (sender instanceof Player) {
                                    GrapesPlayer player = GrapesPlayer.getByUniqueId(((Player) sender).getUniqueId());
                                    if (player.getBalance().hasEnough(price)) {
                                        player.getBalance().subtract(price);
                                        new Bounty(sender.getName(), offlinePlayer.getUniqueId(), price);
                                        sender.sendMessage("§aA §e" + args[2] + "$ §aBounty was created for §c" + args[1] + "§a!");
                                    } else player.sendMessage("§cYou don't have enough money in your account!");
                                } else {
                                    new Bounty("CONSOLE", offlinePlayer.getUniqueId(), price);
                                    sender.sendMessage("§aA §e" + args[2] + "$ §aBounty was created for §c" + args[1] + "§a!");
                                }
                            } catch (NumberFormatException e) {
                                // TODO: 27.01.2021 UPDATE TO COLOR CODED METHOD
                                sender.sendMessage("§cI am sorry but \"" + args[2] + "\" is not a number.");
                            }
                        } else sender.sendMessage("§cI'm sorry, but the player was never online.");
                    } else sender.sendMessage("§cI'm sorry, but you can't create a negative bounty.");
                } else showHelp(sender);
            } else showHelp(sender);
        } else sender.sendMessage("§cYou don't have the permission to execute this command!");
        return true;
    }

    private void showHelp(CommandSender sender) {
        // TODO: 27.01.2021 UPDATE WITH COLORED MESSAGES.
        sender.sendMessage("§cHelp");
    }
}
