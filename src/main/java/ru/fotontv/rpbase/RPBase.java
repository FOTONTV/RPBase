package ru.fotontv.rpbase;

import net.luckperms.api.LuckPerms;
import net.milkbowl.vault.permission.Permission;
import org.bukkit.Bukkit;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;
import ru.fotontv.rpbase.api.placeholders.RPBaseExpansion;
import ru.fotontv.rpbase.commands.*;
import ru.fotontv.rpbase.config.GlobalConfig;
import ru.fotontv.rpbase.config.StatusCityConfig;
import ru.fotontv.rpbase.listeners.*;
import ru.fotontv.rpbase.modules.city.CitiesManager;
import ru.fotontv.rpbase.modules.jail.JailManager;
import ru.fotontv.rpbase.modules.player.PlayersManager;
import ru.fotontv.rpbase.modules.wanted.WantedManager;

import java.util.Objects;

public final class RPBase extends JavaPlugin {

    public static LuckPerms api;
    private static RPBase plugin;
    public Permission permission;
    private GlobalConfig globalConfig;

    public static RPBase getPlugin() {
        return plugin;
    }

    @Override
    public void onEnable() {
        plugin = this;
        getLogger().info("Loading RPBase...");

        if (Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null) {
            new RPBaseExpansion(this).register();
        }

        RegisteredServiceProvider<LuckPerms> provider = Bukkit.getServicesManager().getRegistration(LuckPerms.class);
        if (provider != null) {
            api = provider.getProvider();
        }

        setupPermissions();

        saveDefaultConfig();

        this.globalConfig = new GlobalConfig(plugin);
        new StatusCityConfig(plugin);

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

    public void setupPermissions() {
        RegisteredServiceProvider<Permission> rsp = getServer().getServicesManager().getRegistration(Permission.class);
        if (rsp != null) {
            permission = rsp.getProvider();
        }
    }

    public Permission getPermission() {
        return permission;
    }

    public GlobalConfig getConfigManager() {
        return globalConfig;
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
        Objects.requireNonNull(getCommand("rpbase")).setExecutor(new ReloadCommands());
    }

    private void registerEvents() {
        getServer().getPluginManager().registerEvents(new JoinListener(), this);
        getServer().getPluginManager().registerEvents(new LeaveListener(), this);
        getServer().getPluginManager().registerEvents(new ChatListener(), this);
        getServer().getPluginManager().registerEvents(new ArmorChangeListener(), this);
        getServer().getPluginManager().registerEvents(new CitizensListener(), this);
        getServer().getPluginManager().registerEvents(new PlayersManager(), this);
        getServer().getPluginManager().registerEvents(new CitiesManager(this), this);
    }
}
