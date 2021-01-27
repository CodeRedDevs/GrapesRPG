package me.trqhxrd.grapesrpg.commands;

import me.trqhxrd.grapesrpg.Grapes;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.util.Objects;

/**
 * This class handles the main command for GrapesRPG.
 *
 * @author Trqhxrd
 */
public class GrapesCommand implements CommandExecutor {

    /**
     * The constructor registers the command. Only execute once!
     */
    public GrapesCommand() {
        Objects.requireNonNull(Grapes.getGrapes().getCommand("grapes")).setExecutor(this);
    }

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
        } else if (args.length == 2) {
            if (args[0].equalsIgnoreCase("admin")) {
                if (args[1].equalsIgnoreCase("update")) {
                    if (args[2].equalsIgnoreCase("recipe") || args[2].equalsIgnoreCase("recipes")) {
                        Grapes.getGrapes().reloadRecipes(true);
                        Grapes.getGrapes().getUtils().sendMessage(sender, "&aUpdated Recipes successfully!");
                    }
                }
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
}
