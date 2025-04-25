package de.thecitycrafter.shop.command;

import de.thecitycrafter.shop.Main;
import de.thecitycrafter.shop.misc.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class shopAdminCommand implements CommandExecutor, TabCompleter {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (sender instanceof Player player) {
            switch (args[0]) {
                case "reload" -> reload(player);
                case "create" -> create(player, args);
                case "delete" -> delete(player, args);
                default -> player.sendMessage("Unknown Argument");

            }
        }
        return true;
    }


    private static void reload(Player player) {
        Main.getPlugin().reloadConfig();
        player.sendMessage("Shop reloaded");
    }
    private static void create(Player player, String[] args) {
       if (args[1].equals("category")){
           if (args.length <= 4){
               player.sendMessage("Missing Arguments");
               return;
           }
           String name = args[2];
           String icon = args[3];
           int slot = Integer.parseInt(args[4]);

           ConfigurationSection configurationSection = Main.getPluginConfig().getConfigurationSection("categories");
           for(String categories: configurationSection.getKeys(false)){
               if (categories.equals(name)){
                   player.sendMessage("Category already exists");
                   return;
               }
           }
           configurationSection.createSection(name);
           configurationSection.getConfigurationSection(name).set("icon", icon);
           configurationSection.getConfigurationSection(name).set("slot", slot);
           configurationSection.getConfigurationSection(name).createSection("items");
           Main.getPlugin().saveConfig();
           player.sendMessage("Category created");
       }

       else if (args[1].equals("item")) {
           if (args.length <= 5){
               player.sendMessage("Missing Arguments");
               return;
           }
           String category = args[2];
           String item = args[3];
           String price = args[4];
           int pricecount = Integer.parseInt(args[5]);

           ConfigurationSection configurationSection = Main.getPluginConfig().getConfigurationSection("categories").getConfigurationSection(category).getConfigurationSection("items");



           configurationSection.createSection(String.valueOf(configurationSection.getKeys(false).size() + 1));
           configurationSection.getConfigurationSection(String.valueOf(configurationSection.getKeys(false).size())).set("id", item);
           configurationSection.getConfigurationSection(String.valueOf(configurationSection.getKeys(false).size())).createSection("price");
           configurationSection.getConfigurationSection(String.valueOf(configurationSection.getKeys(false).size())).getConfigurationSection("price").set("item", price);
           configurationSection.getConfigurationSection(String.valueOf(configurationSection.getKeys(false).size())).getConfigurationSection("price").set("count", pricecount);

           Main.getPlugin().saveConfig();
           player.sendMessage("Item created");
       }

    }
    private static void delete(Player player, String[] args) {
        if (args.length <= 2){
            player.sendMessage("Missing Arguments");
            return;
        }
        String name = args[2];

        ConfigurationSection configurationSection = Main.getPluginConfig().getConfigurationSection("categories");
        configurationSection.set(name, null);
        Main.getPlugin().saveConfig();
        player.sendMessage("Category deleted");
    }






















    @Override
    public List<String> onTabComplete(CommandSender commandSender, Command command, String s, String[] args) {
        List<String> tabCompleter = new ArrayList<>();
        if (args.length == 1) {
            tabCompleter.add("reload");
            tabCompleter.add("create");
            tabCompleter.add("delete");
        }

        if (args.length == 2){
            if (args[0].equals("create") || args[0].equals("delete")){
                tabCompleter.add("category");
                tabCompleter.add("item");
            }
        }

        if (args.length == 3){
            if (args[0].equals("create") || args[0].equals("delete")){
                if (args[1].equals("category")){
                    tabCompleter.addAll(allCategories());
                }
                if (args[1].equals("item")){
                    tabCompleter.addAll(allCategories());
                }
            }
        }

        if (args.length == 4){
            if (args[0].equals("create")){
                if (args[1].equals("category")){
                    tabCompleter.addAll(allItems());
                }
                if (args[1].equals("item")){
                    tabCompleter.addAll(allItems());

                }
            }

            if (args[0].equals("delete")){
                if (args[1].equals("item")){
                    tabCompleter.addAll(allItems());

                }
            }
        }

        if (args.length == 5){
            if (args[0].equals("create")){
                if (args[1].equals("category")){
                    tabCompleter.add("[Slot]");
                }
                if (args[1].equals("item")){
                    tabCompleter.addAll(allItems());
                }
            }
        }
        if (args.length == 6){
            if (args[0].equals("create")){
                if (args[1].equals("item")){
                    tabCompleter.add("[prizecount]");
                }
            }
        }

        return tabCompleter;
    }



    private static List<String> allItems(){
        List<String> items = new ArrayList<>();
        for(Material material: Material.values()){
            items.add(material.getKey().toString());
        }
        return items;
    }
    private static List<String> allCategories(){
        ConfigurationSection configurationSection = Main.getPluginConfig().getConfigurationSection("categories");
        List<String> categories = new ArrayList<>(configurationSection.getKeys(false));
        return categories;
    }
}
