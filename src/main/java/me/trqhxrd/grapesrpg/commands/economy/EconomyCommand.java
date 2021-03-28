package me.trqhxrd.grapesrpg.commands.economy;

import me.trqhxrd.grapesrpg.Grapes;
import me.trqhxrd.grapesrpg.api.GrapesPlayer;
import me.trqhxrd.grapesrpg.api.attribute.Register;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.List;

@Register(command = "economy")
public class EconomyCommand implements CommandExecutor, TabCompleter {
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
        if (args.length == 0) {
            Grapes.getGrapes().getUtils().sendMessage(sender, "&aThis command is used for managing the servers economy.");
            Grapes.getGrapes().getUtils().sendMessage(sender, "&aYou can give, take and set a players amount of cash.");
            Grapes.getGrapes().getUtils().sendMessage(sender, "&aUse &b/economy help &afor more information!");
        } else if (args.length == 1) {
            if (args[0].equalsIgnoreCase("help")) {
                Grapes.getGrapes().getUtils().sendMessage(sender, "&aHelp for the &beconomy-command&a:");
                Grapes.getGrapes().getUtils().sendMessage(sender, "&b/eco set &d<player> &c<value> &e<bank | wallet> &7- &aSets the players cash amount.");
                Grapes.getGrapes().getUtils().sendMessage(sender, "&b/eco add &d<player> &c<value> &e<bank | wallet> &7- &aAdds a certain amount of cash to the players account.");
                Grapes.getGrapes().getUtils().sendMessage(sender, "&b/eco remove &d<player> &c<value> &e<bank | wallet> &7- &aRemoves a certain amount of cash from the players account.");
                Grapes.getGrapes().getUtils().sendMessage(sender, "&b/eco &d<player> &7- &aThis does the same as the balance command. It returns the amount of cash, a player has in the chat.");
                Grapes.getGrapes().getUtils().sendMessage(sender, "");
                Grapes.getGrapes().getUtils().sendMessage(sender, "&d<player>: &aThe player, who is supposed to be affected by the command.");
                Grapes.getGrapes().getUtils().sendMessage(sender, "&c<value>: &aThe amount of money, that should be added / removed / set.");
                Grapes.getGrapes().getUtils().sendMessage(sender, "&e<bank | wallet>: &aWhether the wallet or the bank account of the player should be affected.");
            } else {
                if (sender.hasPermission("grapes.eco.balance.other")) {
                    Player target = Bukkit.getPlayerExact(args[0]);
                    if (target != null) {
                        GrapesPlayer gTarget = GrapesPlayer.getByPlayer(target);
                        Grapes.getGrapes().getUtils().sendMessage(sender, gTarget.getEcoSet().getBalanceFormatted());
                    } else Grapes.getGrapes().getUtils().sendMessage(sender, "&cThere is no player with the name &e" + args[0] + " &conline!");
                } else Grapes.getGrapes().getUtils().noPermission(sender);
            }
        }
        return true;
    }

    /**
     * Requests a list of possible completions for a command argument.
     *
     * @param sender  Source of the command.  For players tab-completing a
     *                command inside of a command block, this will be the player, not
     *                the command block.
     * @param command Command which was executed
     * @param alias   The alias used
     * @param args    The arguments passed to the command, including final
     *                partial argument to be completed and command label
     * @return A List of possible completions for the final argument, or null
     * to default to the command executor
     */
    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        return null;
    }
}
