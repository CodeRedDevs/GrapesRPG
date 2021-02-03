package me.trqhxrd.grapesrpg.commands;

import me.trqhxrd.grapesrpg.Grapes;
import me.trqhxrd.grapesrpg.game.inventories.ColorMenu;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;

import java.util.Objects;
import java.util.concurrent.ThreadLocalRandom;

public class ColorCommand implements CommandExecutor {

    public ColorCommand() {
        Objects.requireNonNull(Grapes.getGrapes().getCommand("color")).setExecutor(this);
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

                Grapes.getGrapes().getUtils().sendMessage(sender, "&#" + colorCode.toString() + colorCode.toString());
            } else if (args[0].equalsIgnoreCase("menu")) {
                if (sender instanceof Player)new ColorMenu().openInventory((HumanEntity) sender);
            } else this.showHelp(sender);
        } else this.showHelp(sender);
        return true;
    }

    private void showHelp(CommandSender sender) {
        Grapes.getGrapes().getUtils().sendMessage(sender, "&c/color random");
        Grapes.getGrapes().getUtils().sendMessage(sender, "&c/color menu");
    }
}
