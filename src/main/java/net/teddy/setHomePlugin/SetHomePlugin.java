package net.teddy.setHomePlugin;

import net.teddy.setHomePlugin.commands.DelHomeCommand;
import net.teddy.setHomePlugin.commands.HomeCommand;
import net.teddy.setHomePlugin.commands.SeeHomesCommand;
import net.teddy.setHomePlugin.commands.SetHomeCommand;
import net.teddy.setHomePlugin.listeners.HomesListener;
import org.bukkit.plugin.java.JavaPlugin;

public final class SetHomePlugin extends JavaPlugin {

    private static SetHomePlugin instance;

    @Override
    public void onEnable() {
        // Plugin startup logic
        instance = this;

        getCommand("sethome").setExecutor(new SetHomeCommand());
        getCommand("home").setExecutor(new HomeCommand());
        getCommand("delhome").setExecutor(new DelHomeCommand());
        getCommand("seehomes").setExecutor(new SeeHomesCommand());
        
        getServer().getPluginManager().registerEvents(new HomesListener(), this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public static SetHomePlugin getInstance() {
        return instance;
    }
}
