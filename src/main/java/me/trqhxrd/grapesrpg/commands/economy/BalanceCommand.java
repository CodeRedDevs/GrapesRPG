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

import java.util.ArrayList;
import java.util.List;

@Register(command = "balance")
public class BalanceCommand implements CommandExecutor, TabCompleter {
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
        if (args.length > 2) {
            Grapes.getGrapes().getUtils().sendMessage(sender, "&cUsage: /balance [player]");
            return true;
        }

        if (args.length == 0) {
            if (!(sender instanceof Player)) {
                Grapes.getGrapes().getUtils().sendMessage(sender, "&cYou have to be a player to execute this command!");
                return true;
            }

            Player p = (Player) sender;

            if (!p.hasPermission("grapes.eco.balance")) {
                Grapes.getGrapes().getUtils().noPermission(p);
                return true;
            }

            GrapesPlayer target = GrapesPlayer.getByPlayer(p);
            target.sendMessage("&aYou &ehave &c" + target.getEcoSet().getWallet() + " &ein your wallet and &c" + target.getEcoSet().getBank() + " &ein your bank-account!");

            return true;
        } else if (args.length == 1) {
            if (!GrapesPlayer.exists(args[0])) {
                Grapes.getGrapes().getUtils().sendMessage(sender, "&cThe player &e\"" + args[0] + "\" &cis not online at the moment! Please try again later!");
                return true;
            }

            GrapesPlayer target = GrapesPlayer.getByName(args[0]);
            if (target != null)
                Grapes.getGrapes().getUtils().sendMessage(sender, target.getEcoSet().getBalanceFormatted());
            return true;
        }
        return false;
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
        List<String> list = new ArrayList<>();
        if (args.length == 1) Bukkit.getOnlinePlayers().forEach(p -> list.add(p.getName()));
        return list;
    }
}
