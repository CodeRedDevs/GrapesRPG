package me.trqhxrd.grapesrpg.commands;

import me.trqhxrd.grapesrpg.Grapes;
import me.trqhxrd.grapesrpg.api.attribute.Register;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * This class handles the main command for GrapesRPG.
 *
 * @author Trqhxrd
 */
@Register(command = "grapes")
public class GrapesCommand implements CommandExecutor, TabCompleter {

    /**
     * This method handles the command-entries.
     *
     * @param sender The Sender of the command. Can be a Player but also the Console.
     * @param cmd    The Command, which was entered.
     * @param label  The entered label. {@literal (for Example: /give Trqhxrd apple 64 -> "give")}.
     * @param args   The command arguments. {@literal (for Example: /give Trqhxrd apple 64 -> {"Trqhxrd", "apple", "64"})}
     * @return If this returns false, the command help will be displayed.
     */
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (args.length == 0) {
            Grapes.getGrapes().getUtils().sendMessage(sender, "&cPlease do &e/grapes help &cfor more information!");
        } else if (args.length == 1) {
            if (args[0].equalsIgnoreCase("help")) this.sendHelp(sender);
        } else if (args.length == 3) {
            if (args[0].equalsIgnoreCase("admin")) {
                if (sender.hasPermission("grapes.admin")) {
                    if (args[1].equalsIgnoreCase("update")) {
                        if (args[2].equalsIgnoreCase("recipe") || args[2].equalsIgnoreCase("recipes")) {
                            Grapes.getGrapes().reloadRecipes();
                            Grapes.getGrapes().getUtils().sendMessage(sender, "&aUpdated Recipes successfully!");
                        } else if (args[2].equalsIgnoreCase("plugin")) {
                            // TODO: 19.02.2021 Create Plugin Updater
                            Grapes.getGrapes().getUtils().sendMessage(sender, "&cThis is not implemented yet!");
                        }
                    }
                } else Grapes.getGrapes().getUtils().noPermission(sender);
            } else sendHelp(sender);
        }
        return true;
    }

    /**
     * This method sends the Help-Messages to the given {@link CommandSender}.
     *
     * @param cs The Sender, that will receive the help messages.
     */
    public void sendHelp(CommandSender cs) {
        Grapes.getGrapes().getUtils().sendMessage(cs, "&eGrapesRPG &aby &cTabbyplayz &aand &bTrqhxrd");
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
        if (args.length == 1) return Arrays.asList("help", "admin");
        else if (args.length == 2 && args[0].equalsIgnoreCase("admin")) return Arrays.asList("update");
        else if (args.length == 3 && args[0].equalsIgnoreCase("admin") && args[2].equalsIgnoreCase("update")) return Arrays.asList("plugin", "recipes");
        return Collections.emptyList();
    }
}
