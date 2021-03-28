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

@Register(command = "pay")
public class PayCommand implements CommandExecutor, TabCompleter {
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
        if (!(sender instanceof Player)) {
            Grapes.getGrapes().getUtils().sendMessage(sender, "&cI am sorry, but you have to be a player to execute this command!");
            return true;
        }

        Player player = (Player) sender;
        GrapesPlayer p = GrapesPlayer.getByPlayer(player);

        if (!p.getWrappedObject().hasPermission("grapes.eco.pay")) {
           Grapes.getGrapes().getUtils().noPermission(sender);
            return true;
        }

        if (args.length != 2) {
            p.sendMessage("&cUsage: &b/pay &a<player> &c<amount>");
            return true;
        }

        Player target = Bukkit.getPlayerExact(args[0]);

        if (target == null) {
            p.sendMessage("&cThe player \"" + args[0] + "\" does not exist!");
            return true;
        }

        if (player.getUniqueId().equals(target.getUniqueId())) {
            p.sendMessage("&cYou can't pay money to yourself!");
            return true;
        }

        try {
            long amount = Long.parseLong(args[1].replace(".", "").replace(",", ""));

            if (amount <= 0) {
                p.sendMessage("&cThe amount, that you enter has to be bigger then 0!");
                return true;
            }

            if (p.getEcoSet().pay(GrapesPlayer.getByPlayer(target), amount)) {
                p.sendMessage("&aYou successfully payed &e" + target.getName() + " &b" + amount + "$&a!");
                Grapes.getGrapes().getUtils().sendMessage(target, "&aThe player &e" + p.getName() + " &asent you &b" + amount + "$&a!");
            } else p.sendMessage("&cSomething went wrong please try again! (Maybe you don't have enough coins?");
        } catch (NumberFormatException ex) {
            Grapes.getGrapes().getUtils().sendMessage(p, "&c\"" + args[1] + "\" is not a number!");
            return true;
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
        List<String> list = new ArrayList<>();
        if (sender instanceof Player) {
            Player p = (Player) sender;
            if (args.length == 1) {
                Bukkit.getOnlinePlayers().stream()
                        .filter(obj -> !obj.getUniqueId().equals(p.getUniqueId()))
                        .forEach(player -> list.add(player.getName()));
            } else if (args.length == 2) list.addAll(Grapes.getGrapes().getGrapesConfig().getEcoCommandTabCompletions());
        }
        return list;
    }
}
