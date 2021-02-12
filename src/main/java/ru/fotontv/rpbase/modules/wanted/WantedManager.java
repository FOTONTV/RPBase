package ru.fotontv.rpbase.modules.wanted;

import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import ru.fotontv.rpbase.RPBase;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class WantedManager {

    private final RPBase plugin;
    private static File wantedFile;
    private static FileConfiguration wantedConfig;
    private static List<String> wantedList = new ArrayList<>();

    public WantedManager(RPBase rpBase) {
        plugin = rpBase;
        load();
    }

    private void load() {
        wantedFile = new File(plugin.getDataFolder(), "wantedList.yml");
        if (!(wantedFile.exists())) {
            if (!(wantedFile.getParentFile().mkdirs())) System.out.println();
            plugin.saveResource("wantedList.yml", false);
            wantedConfig = new YamlConfiguration();
            try {
                wantedConfig.load(wantedFile);
            } catch (InvalidConfigurationException | IOException e) {
                e.printStackTrace();
            }
            return;
        }
        wantedConfig = new YamlConfiguration();
        try {
            wantedConfig.load(wantedFile);
        } catch (InvalidConfigurationException | IOException e) {
            e.printStackTrace();
        }
        wantedList = wantedConfig.getStringList("wanted");
    }

    public static void saveFileWanted() {
        wantedConfig.set("wanted", wantedList);
        try {
            wantedConfig.save(wantedFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static List<String> getWantedList() {
        return wantedList;
    }

    public static void setWantedList(Player player) {
        wantedList.add(player.getName());
    }

    public static boolean isPlayerWanted(Player player) {
        return wantedList.contains(player.getName());
    }

    public static void removeWanted(Player player) {
        wantedList.remove(player.getName());
    }
}
