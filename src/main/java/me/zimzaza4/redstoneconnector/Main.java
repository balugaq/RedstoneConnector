package me.zimzaza4.redstoneconnector;

import io.github.thebusybiscuit.slimefun4.implementation.SlimefunItems;
import me.zimzaza4.redstoneconnector.remote.RemoteController;
import me.zimzaza4.redstoneconnector.remote.RemoteExecuteBlock;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.api.SlimefunAddon;

import java.util.Locale;

public class Main extends JavaPlugin implements SlimefunAddon {

    private static Main instance;

    @Override
    public void onEnable() {
        instance = this;

        new InactiveRedstoneConnector(Items.group, Items.CONNECTOR, RecipeType.ENHANCED_CRAFTING_TABLE, new ItemStack[]{
                null, new ItemStack(Material.REDSTONE_TORCH),null,
                null, SlimefunItems.ENERGY_CONNECTOR, null,
                null, null, null
        }).register(this);

        ActiveRedstoneConnector item = new ActiveRedstoneConnector(Items.group, Items.ACTIVE_CONNECTOR, RecipeType.NULL, new ItemStack[]{
                null, new ItemStack(Material.REDSTONE_TORCH), null,
                null, SlimefunItems.ENERGY_CONNECTOR, null,
                null, null, null
        });
        item.setHidden(true);
        item.register(this);

        new RemoteController(Items.group, Items.REMOTE_CONTROLLER, RecipeType.ENHANCED_CRAFTING_TABLE, new ItemStack[]{
                null, new ItemStack(Material.REPEATER), null,
                new ItemStack(Material.ENDER_PEARL), new ItemStack(Material.ENDER_PEARL), new ItemStack(Material.REDSTONE),
                null,null,null
        }, 60).register(this);

        new RemoteExecuteBlock(Items.group, Items.REMOTE_EXECUTE_BLOCK, RecipeType.ENHANCED_CRAFTING_TABLE, new ItemStack[]{
                null, new ItemStack(Material.ENDER_EYE), null,
                new ItemStack(Material.REDSTONE_TORCH), new ItemStack(Material.IRON_BLOCK), new ItemStack(Material.REDSTONE),
                null, new ItemStack(Material.ENDER_PEARL), null
        }).register(this);
    }

    @Override
    public void onDisable() {

    }

    @Override
    public String getBugTrackerURL() {

        return null;
    }

    @Override
    public JavaPlugin getJavaPlugin() {
        return this;
    }

    public static Main instance() {
        return instance;
    }

    
    public static NamespacedKey key(String key){
        return new NamespacedKey(instance(), key.toUpperCase(Locale.ROOT));
    }
}
