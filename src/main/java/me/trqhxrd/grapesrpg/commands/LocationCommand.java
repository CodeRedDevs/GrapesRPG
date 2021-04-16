package me.trqhxrd.grapesrpg.commands;

import me.trqhxrd.grapesrpg.Grapes;
import me.trqhxrd.grapesrpg.api.GrapesPlayer;
import me.trqhxrd.grapesrpg.api.attribute.Register;
import me.trqhxrd.grapesrpg.game.config.NPCLocationsConfig;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.List;

@Register(command = "npclocation")
public class LocationCommand implements CommandExecutor, TabCompleter {
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
            Grapes.getGrapes().getUtils().sendMessage(sender, "&cYou have to be a player to execute this command!");
            return true;
        }

        GrapesPlayer p = GrapesPlayer.getByPlayer((Player) sender);

        if (args.length != 1) {
            p.sendMessage("&c/npcloc <key>");
            return true;
        }

        if (!sender.hasPermission("grapes.admin.command.npclocation")) {
            Grapes.getGrapes().getUtils().noPermission(sender);
            return true;
        }

        NPCLocationsConfig.setLocation(args[0], p.getLocation());
        p.sendMessage("&aDie Location mit dem Namen &e" + args[0] + " &awurde erfolgreich gesetzt!");

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
        if (sender.hasPermission("grapes.admin.command.npclocation")) {
            return Arrays.asList("intro");
        } else return null;
    }
}
