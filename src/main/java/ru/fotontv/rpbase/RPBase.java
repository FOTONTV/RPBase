package ru.fotontv.rpbase;

import net.luckperms.api.LuckPerms;
import org.bukkit.Bukkit;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;
import ru.fotontv.rpbase.commands.*;
import ru.fotontv.rpbase.data.CitiesManager;
import ru.fotontv.rpbase.data.PlayersManager;
import ru.fotontv.rpbase.listeners.ChatListener;
import ru.fotontv.rpbase.listeners.JoinListener;
import ru.fotontv.rpbase.listeners.LeaveListener;
import ru.fotontv.rpbase.modules.config.ConfigManager;
import ru.fotontv.rpbase.modules.config.StatusCityManager;
import ru.fotontv.rpbase.modules.jail.JailManager;
import ru.fotontv.rpbase.modules.wanted.WantedManager;

import java.util.Objects;

public final class RPBase extends JavaPlugin {

    private static RPBase plugin;

    public static RPBase getPlugin() {
        return plugin;
    }

    public static LuckPerms api;

    @Override
    public void onEnable() {
       plugin = this;
       getLogger().info("Loading RPBase...");

        RegisteredServiceProvider<LuckPerms> provider = Bukkit.getServicesManager().getRegistration(LuckPerms.class);
        if (provider != null) {
            api = provider.getProvider();
        }

       saveDefaultConfig();

       new ConfigManager(plugin);
       new StatusCityManager(plugin);

       new WantedManager(plugin);
       new JailManager(plugin);
       CitiesManager citiesManager = new CitiesManager(this);
       citiesManager.load();
       PlayersManager.load();

       registerEvents();
       registerCommands();
    }

    @Override
    public void onDisable() {
        getLogger().info("Disabling RPBase...");
        WantedManager.saveFileWanted();
        JailManager.saveFileJails();
        CitiesManager.saveCities();
    }

    private void registerCommands() {
        Objects.requireNonNull(getCommand("wanted")).setExecutor(new WantedCommands());
        Objects.requireNonNull(getCommand("wanted")).setTabCompleter(new WantedTabCompleter());
        Objects.requireNonNull(getCommand("jail")).setExecutor(new JailCommands());
        Objects.requireNonNull(getCommand("jail")).setTabCompleter(new JailTabCompleter());
        Objects.requireNonNull(getCommand("cell")).setExecutor(new CellCommands());
        Objects.requireNonNull(getCommand("cell")).setTabCompleter(new CellTabCompleter());
        Objects.requireNonNull(getCommand("imp")).setExecutor(new ImpCommands());
        Objects.requireNonNull(getCommand("imp")).setTabCompleter(new ImpTabCompleter());
        Objects.requireNonNull(getCommand("pass")).setExecutor(new PassCommands());
        Objects.requireNonNull(getCommand("pass")).setTabCompleter(new PassTabCompleter());
        Objects.requireNonNull(getCommand("passpr")).setExecutor(new PassprCommands());
        Objects.requireNonNull(getCommand("passpr")).setTabCompleter(new PassprTabCompleter());
        Objects.requireNonNull(getCommand("passcr")).setExecutor(new PasscrCommands());
        Objects.requireNonNull(getCommand("passcr")).setTabCompleter(new PasscrTabCompleter());
        Objects.requireNonNull(getCommand("passj")).setExecutor(new PassjCommands());
        Objects.requireNonNull(getCommand("passj")).setTabCompleter(new PassjTabCompleter());
        Objects.requireNonNull(getCommand("passq")).setExecutor(new PassqCommands());
        Objects.requireNonNull(getCommand("passq")).setTabCompleter(new PassqTabCompleter());
        Objects.requireNonNull(getCommand("town")).setExecutor(new TownCommands());
        Objects.requireNonNull(getCommand("town")).setTabCompleter(new TownTabCompleter());
        Objects.requireNonNull(getCommand("prof")).setExecutor(new ProfCommands());
        Objects.requireNonNull(getCommand("prof")).setTabCompleter(new ProfTabCompleter());
        Objects.requireNonNull(getCommand("tc")).setExecutor(new TcCommands());
        Objects.requireNonNull(getCommand("talent")).setExecutor(new TalentCommands());
    }

    private void registerEvents() {
        getServer().getPluginManager().registerEvents(new JoinListener(), this);
        getServer().getPluginManager().registerEvents(new LeaveListener(), this);
        getServer().getPluginManager().registerEvents(new ChatListener(), this);
        getServer().getPluginManager().registerEvents(new PlayersManager(), this);
        getServer().getPluginManager().registerEvents(new CitiesManager(this), this);
    }
}
