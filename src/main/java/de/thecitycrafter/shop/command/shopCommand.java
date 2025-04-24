package de.thecitycrafter.shop.command;

import de.thecitycrafter.shop.Main;
import de.thecitycrafter.shop.inventories.shopCategoryInventory;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

import java.util.List;

public class shopCommand implements CommandExecutor, TabCompleter {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        Player player = (Player) sender;
        if (args.length == 1 && args[0].equalsIgnoreCase("reload")){
            Main.getPlugin().reloadConfig();
            player.sendMessage("Shop reloaded");
            return true;
        }


//        ConfigurationSection configurationSection = Main.getPluginConfig().getConfigurationSection("categories");
//
//        for(String key: configurationSection.getKeys(false)){
//            ConfigurationSection categories = configurationSection.getConfigurationSection(key);
//            player.sendMessage("Kategorie: " + key + "\nSlot: " + categories.getInt("slot"));
//            ConfigurationSection items = categories.getConfigurationSection("items");
//            for(String itemKey: items.getKeys(false)){
//                ConfigurationSection item = items.getConfigurationSection(itemKey);
//                player.sendMessage(" Item: " + item.getString("id") + "\n Slot: " + item.getInt("slot"));
//            }
//        }
        shopCategoryInventory.openInventory(player);
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String s, String[] args) {
        return List.of();
    }
}
