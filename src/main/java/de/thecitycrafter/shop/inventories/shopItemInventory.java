package de.thecitycrafter.shop.inventories;

import de.thecitycrafter.shop.Main;
import de.thecitycrafter.shop.inventories.holders.itemHolder;
import de.thecitycrafter.shop.misc.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;

public class shopItemInventory implements Listener {
    private static Inventory inventory = Bukkit.createInventory(new itemHolder(),9*6,"Shop");

    public static void setInventoryContent(Player player, String category){

        for (int i = 0; i < inventory.getSize(); i++) {
            inventory.setItem(i, new ItemBuilder(Material.GRAY_STAINED_GLASS_PANE).toItemStack());
        }

        ConfigurationSection configurationSection = Main.getPluginConfig().getConfigurationSection("categories");

        for(String key: configurationSection.getKeys(false)){
            ConfigurationSection categories = configurationSection.getConfigurationSection(key);
            if (key.equals(category)){
                ConfigurationSection items = categories.getConfigurationSection("items");
                int currentSlot = 0;
                for(String itemKey: items.getKeys(false)){
                    ConfigurationSection item = items.getConfigurationSection(itemKey);
                    String priceID = "";
                    int priceCount = 0;

                    for (String price: item.getKeys(false)){
                        if (!price.equals("id")){
                            ConfigurationSection priceItem = item.getConfigurationSection(price);
                            priceID = priceItem.getString("item");
                            priceCount = priceItem.getInt("count");
                        }
                    }
                    inventory.setItem(currentSlot, new ItemBuilder(Material.matchMaterial(item.getString("id"))).addLoreLine("§r§a" + priceCount + "x " + priceID).toItemStack());
                    currentSlot++;
                }
            }
        }
    }

    public static void openInventory(Player player, String category){
        setInventoryContent(player, category);
        player.openInventory(inventory);
    }


    @EventHandler
    public void onInventoryClick(InventoryClickEvent event){
        if (event.getWhoClicked() instanceof Player player){
            if (event.getClickedInventory().getHolder() instanceof itemHolder){
                if (event.getCurrentItem().getType() != Material.GRAY_STAINED_GLASS_PANE){

                    String[] price = event.getCurrentItem().getItemMeta().getLore().get(0).split("x");
                    String priceItem = price[1].replaceAll(" ", "");
                    int priceCount = Integer.parseInt(price[0].replaceAll("§a", ""));
                    if (player.getInventory().contains(Material.matchMaterial(priceItem), priceCount)){
                        player.getInventory().removeItem(new ItemBuilder(Material.matchMaterial(priceItem),priceCount).toItemStack());
                        player.getInventory().addItem(new ItemBuilder(event.getCurrentItem().getType()).toItemStack());
                    }
                }
                event.setCancelled(true);
            }

        }
    }

}
