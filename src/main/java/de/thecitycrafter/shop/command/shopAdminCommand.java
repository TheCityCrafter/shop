package de.thecitycrafter.shop.command;

import de.thecitycrafter.shop.Main;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class shopAdminCommand implements CommandExecutor, TabCompleter {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (sender instanceof Player player){
            switch (args[0]){
                case "reload":{
                    Main.getPlugin().reloadConfig();
                    player.sendMessage("Shop reloaded");
                    break;
                }
                default: {
                    player.sendMessage("Unknown Argument");
                    break;
                }
            }
        }
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender commandSender, Command command, String s, String[] args) {
        List<String> tabCompleter = new ArrayList<>();
        if (args.length == 1){
            tabCompleter.add("reload");
        }

        return tabCompleter;
    }
}
