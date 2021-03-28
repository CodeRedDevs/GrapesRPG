package me.trqhxrd.grapesrpg.commands;

import me.trqhxrd.grapesrpg.Grapes;
import me.trqhxrd.grapesrpg.api.attribute.Register;
import me.trqhxrd.grapesrpg.game.inventory.ColorMenu;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@Register(command = "color")
public class ColorCommand implements CommandExecutor, TabCompleter {

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
        if (args.length == 0) {
            this.showHelp(sender);
        } else if (args.length == 1) {
            if (args[0].equalsIgnoreCase("random")) {
                int[] color = {
                        ThreadLocalRandom.current().nextInt(0, 256),
                        ThreadLocalRandom.current().nextInt(0, 256),
                        ThreadLocalRandom.current().nextInt(0, 256)
                };

                StringBuilder colorCode = new StringBuilder();
                for (int value : color) {
                    String s = Integer.toHexString(value);
                    if (s.length() == 1) s = "0" + s;
                    colorCode.append(s);
                }

                Grapes.getGrapes().getUtils().sendMessage(sender, "&#" + colorCode.toString().repeat(2));
            } else if (args[0].equalsIgnoreCase("menu") && sender instanceof Player) new ColorMenu().open((HumanEntity) sender);
            else this.showHelp(sender);
        } else this.showHelp(sender);
        return true;
    }

    private void showHelp(CommandSender sender) {
        Grapes.getGrapes().getUtils().sendMessage(sender, "&c/color random");
        Grapes.getGrapes().getUtils().sendMessage(sender, "&c/color menu");
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
        if (args.length == 1) return Arrays.asList("random", "menu");
        return Collections.emptyList();
    }
}
