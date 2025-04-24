package de.thecitycrafter.shop.inventories;

import de.thecitycrafter.shop.Main;
import de.thecitycrafter.shop.inventories.holders.categoryHolder;
import de.thecitycrafter.shop.misc.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;

public class shopCategoryInventory implements Listener {
    private static Inventory inventory = Bukkit.createInventory(new categoryHolder(),9*6,"Shop");

    public static void setInventoryContent(){

        for (int i = 0; i < inventory.getSize(); i++) {
            inventory.setItem(i, new ItemBuilder(Material.GRAY_STAINED_GLASS_PANE).toItemStack());
        }
        ConfigurationSection configurationSection = Main.getPluginConfig().getConfigurationSection("categories");
        for(String key: configurationSection.getKeys(false)){
            ConfigurationSection categories = configurationSection.getConfigurationSection(key);

            inventory.setItem(categories.getInt("slot"), new ItemBuilder(Material.matchMaterial(categories.getString("icon"))).setName("§r" + key).toItemStack());
        }


    }


    public static void openInventory(Player player){
        setInventoryContent();
        player.openInventory(inventory);
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event){
        if (event.getClickedInventory().getHolder() instanceof categoryHolder){
            if (event.getCurrentItem().getType() != Material.GRAY_STAINED_GLASS_PANE) {
                shopItemInventory.openInventory((Player) event.getWhoClicked(), event.getCurrentItem().getItemMeta().getDisplayName());
            }
            event.setCancelled(true);
        }

    }

}
