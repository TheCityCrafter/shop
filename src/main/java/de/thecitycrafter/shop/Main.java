package de.thecitycrafter.shop;

import de.thecitycrafter.shop.command.shopCommand;
import de.thecitycrafter.shop.inventories.shopCategoryInventory;
import de.thecitycrafter.shop.inventories.shopItemInventory;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

public final class Main extends JavaPlugin {
    private static Main plugin;

    @Override
    public void onEnable() {
        plugin = this;
        this.saveDefaultConfig();
        getCommand("shop").setExecutor(new shopCommand());
        getCommand("shop").setTabCompleter(new shopCommand());
        getServer().getPluginManager().registerEvents(new shopCategoryInventory(), this);
        getServer().getPluginManager().registerEvents(new shopItemInventory(), this);

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
    public static Plugin getPlugin() {
        return plugin;
    }
    public static FileConfiguration getPluginConfig() {
        return getPlugin().getConfig();
    }
}
