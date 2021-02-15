package me.trqhxrd.grapesrpg.commands;

import me.trqhxrd.grapesrpg.api.attribute.Register;
import me.trqhxrd.grapesrpg.api.objects.entity.npc.NPC;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@Register(command = "npc")
public class NPCCommand implements CommandExecutor {

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
    @SuppressWarnings("NullableProblems")
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (sender instanceof Player) {
            if (args.length == 0) {

            } else if (args.length == 1) {
                if (args[0].equalsIgnoreCase("spawn")) {
                    new NPC(sender.getName(), ((Player) sender).getLocation(), false).display();
                }
            } else if (args.length == 2) {
                if (args[0].equalsIgnoreCase("spawn")) {
                    new NPC(args[1], ((Player) sender).getLocation(), false).display();
                }
            }
        }
        return true;
    }
}
