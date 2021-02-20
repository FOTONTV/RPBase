package ru.fotontv.rpbase;

import net.luckperms.api.LuckPerms;
import net.milkbowl.vault.chat.Chat;
import net.milkbowl.vault.permission.Permission;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;
import ru.fotontv.rpbase.api.placeholders.RPBaseExpansion;
import ru.fotontv.rpbase.commands.*;
import ru.fotontv.rpbase.config.ChatConfig;
import ru.fotontv.rpbase.config.GlobalConfig;
import ru.fotontv.rpbase.config.StatusCityConfig;
import ru.fotontv.rpbase.listeners.*;
import ru.fotontv.rpbase.modules.city.CitiesManager;
import ru.fotontv.rpbase.modules.jail.JailManager;
import ru.fotontv.rpbase.modules.player.PlayersManager;
import ru.fotontv.rpbase.modules.wanted.WantedManager;
import ru.fotontv.rpbase.utils.DateUtils;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.*;
import java.util.stream.Stream;

public final class RPBase extends JavaPlugin {

    public static LuckPerms api;
    private static RPBase plugin;
    public Permission permission;
    private GlobalConfig globalConfig;
    public Map<String, Long> whitelistedPlayers = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);
    private Chat chat = null;

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

        RegisteredServiceProvider<Chat> chatProvider = getServer().getServicesManager().getRegistration(Chat.class);
        if (chatProvider == null)
            throw new RuntimeException("Менеджер чата не найден");
        this.chat = chatProvider.getProvider();

        setupPermissions();

        saveDefaultConfig();

        this.globalConfig = new GlobalConfig(plugin);
        new StatusCityConfig(plugin);
        new ChatConfig(plugin);
        reloadData();

        new WantedManager(plugin);
        new JailManager(plugin);
        CitiesManager citiesManager = new CitiesManager(this);
        citiesManager.load();
        PlayersManager.load();

        registerEvents();
        registerCommands();

        Bukkit.getScheduler().runTaskTimer(this, this::validateOnlinePlayers, 0L, 1200L);
    }

    @Override
    public void onDisable() {
        getLogger().info("Disabling RPBase...");
        WantedManager.saveFileWanted();
        JailManager.saveFileJails();
        CitiesManager.saveCities();
        HandlerList.unregisterAll(this);
    }

    public void reloadData() {
        if (!loadWhitelist())
            throw new RuntimeException("Не удалось загрузить WhiteList");
    }

    private boolean loadWhitelist() {
        this.whitelistedPlayers.clear();
        boolean save = false;
        boolean success = true;
        ArrayList<String> source = new ArrayList<>();
        try {
            try (Stream<String> stream = Files.lines(getWhitelistFile().toPath())) {
                Objects.requireNonNull(source);
                stream.forEach(source::add);
            }
        } catch (IOException ex) {
            getLogger().severe("Не удалось загрузить файл WhiteList: " + ex.getMessage());
            ex.printStackTrace();
            return false;
        }
        for (String line : source) {
            try {
                String playerName = line.split(" ")[0];
                long until = DateUtils.millsFromStatic(line.substring(playerName.length() + 1));
                if (until != -1L && System.currentTimeMillis() >= until) {
                    save = true;
                    continue;
                }
                this.whitelistedPlayers.put(playerName, until);
            } catch (Exception ex) {
                getLogger().severe("Не удалось загрузить запись whitelist: " + line);
                success = false;
            }
        }
        if (!success)
            return false;
        validateOnlinePlayers();
        return !save || saveWhitelist();
    }

    private void validateOnlinePlayers() {
        for (Player player : Bukkit.getOnlinePlayers()) {
            if (!isWhitelisted(player.getName()))
                player.kickPlayer(ChatConfig.msgNotWhitelisted);
        }
    }

    public boolean isWhitelisted(@Nonnull String playerName) {
        Long until = this.whitelistedPlayers.get(playerName);
        if (until == null)
            return false;
        if (until == -1L)
            return true;
        return (System.currentTimeMillis() < until);
    }

    public boolean saveWhitelist() {
        try {
            try (PrintWriter pw = new PrintWriter(new FileOutputStream(getWhitelistFile()))) {
                for (Map.Entry<String, Long> entry : this.whitelistedPlayers.entrySet()) {
                    if (isWhitelisted(entry.getKey()))
                        pw.println(entry.getKey() + " " + DateUtils.staticFromMills(entry.getValue()));
                }
                pw.close();
                return true;
            }
        } catch (FileNotFoundException ex) {
            getLogger().severe("Не удалось сохранить файл WhiteList: " + ex.getMessage());
            ex.printStackTrace();
            return false;
        }
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    private File getWhitelistFile() {
        try {
            File result = new File(getDataFolder(), "whitelist.txt");
            if (!result.isFile()) {
                result.getParentFile().mkdirs();
                result.createNewFile();
            }
            return result;
        } catch (IOException var2) {
            throw new RuntimeException();
        }
    }

    public String getOfflineUuid(@Nonnull String playerName) {
        return UUID.nameUUIDFromBytes(("OfflinePlayer:" + playerName).getBytes(StandardCharsets.UTF_8)).toString();
    }

    public File getWorldFile(@Nonnull String playerName) {
        return new File(new File(new File(Bukkit.getWorldContainer(), Bukkit.getWorlds().get(0).getName()), "playerdata"), getOfflineUuid(playerName) + ".dat");
    }

    public void sendNoRpMessage(@Nonnull Player sender, boolean global, @Nonnull String message) {
        String msg = getFormattedMessage(sender, global ? ChatConfig.noRpGlobalChatFormat : ChatConfig.noRpLocalChatFormat, message);
        if (msg != null) {
            if (global) {
                Bukkit.getOnlinePlayers().forEach(receiver -> receiver.sendMessage(msg));
            } else {
                Location senderLocation = sender.getLocation();
                World senderWorld = senderLocation.getWorld();
                double maxNoRpDistanceSquired = Math.pow(ChatConfig.noRpLocalChatDistance, 2.0D);
                Bukkit.getOnlinePlayers().stream().filter(receiver -> (receiver.getWorld() == senderWorld))
                        .filter(receiver -> (receiver.getLocation().distanceSquared(senderLocation) <= maxNoRpDistanceSquired))
                        .forEach(receiver -> receiver.sendMessage(msg));
            }
            Bukkit.getConsoleSender().sendMessage(msg);
        }
    }

    @Nullable
    public String getFormattedMessage(@Nonnull Player player, @Nonnull String format, @Nonnull String rawMessage) {
        rawMessage = rawMessage.trim();
        if (rawMessage.isEmpty())
            return null;
        String world = player.getWorld().getName();
        String prefix = colorize(this.chat.getPlayerPrefix(world, player));
        String suffix = colorize(this.chat.getPlayerSuffix(world, player));
        world = colorize(world);
        return format.replace("{world}", world).replace("{player}", player.getName()).replace("{prefix}", prefix).replace("{suffix}", suffix).replace("{prefix_s}", getSpacedVersion(prefix)).replace("{suffix_s}", getSpacedVersion(suffix)).replace("{message}", rawMessage);
    }

    @Nonnull
    private String getSpacedVersion(@Nonnull String input) {
        if (!input.startsWith(" "))
            input = " " + input;
        if (!input.endsWith(" "))
            input = input + " ";
        return input;
    }

    @Nonnull
    private static String colorize(@Nullable String input) {
        if (input == null)
            throw new NullPointerException();
        return ChatColor.translateAlternateColorCodes('&', input);
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
        Objects.requireNonNull(getCommand("rp")).setExecutor(new RPCommands());
        Objects.requireNonNull(getCommand("do")).setExecutor(new DoCommand());
        Objects.requireNonNull(getCommand("me")).setExecutor(new MeCommand());
        Objects.requireNonNull(getCommand("try")).setExecutor(new TryCommand());
        Objects.requireNonNull(getCommand("upgrade")).setExecutor(new UpgradeCommands());
        Objects.requireNonNull(getCommand("fupgrade")).setExecutor(new FupgradeCommands());
    }

    private void registerEvents() {
        getServer().getPluginManager().registerEvents(new JoinListener(), this);
        getServer().getPluginManager().registerEvents(new LeaveListener(), this);
        getServer().getPluginManager().registerEvents(new ChatListener(), this);
        getServer().getPluginManager().registerEvents(new RPListener(this), this);
        getServer().getPluginManager().registerEvents(new ArmorChangeListener(), this);
        getServer().getPluginManager().registerEvents(new CitizensListener(), this);
        getServer().getPluginManager().registerEvents(new PlayersManager(), this);
        getServer().getPluginManager().registerEvents(new CitiesManager(this), this);
    }
}
