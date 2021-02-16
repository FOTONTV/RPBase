package ru.fotontv.rpbase.modules.jail;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import ru.fotontv.rpbase.RPBase;
import ru.fotontv.rpbase.config.GlobalConfig;
import ru.fotontv.rpbase.modules.player.PlayerData;

import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class JailManager {

    private static final List<Jail> jails = new ArrayList<>();
    private static File jailFile;
    private static FileConfiguration jailConfig;
    private final RPBase plugin;

    public JailManager(RPBase rpBase) {
        plugin = rpBase;
        load();
    }

    public static void saveFileJails() {
        jailConfig = new YamlConfiguration();
        if (!(jails.isEmpty())) {
            List<String> nameJails = new ArrayList<>();
            for (Jail jail : jails) {
                String nameJail = jail.getName();
                nameJails.add(nameJail);
                jailConfig.createSection(nameJail);
                List<String> camers = new ArrayList<>();
                if (!(jail.getCamers().isEmpty())) {
                    for (CameraJail cameraJail : jail.getCamers()) {
                        String name = String.valueOf(cameraJail.getId());
                        Location location = cameraJail.getLocation();
                        String x = new DecimalFormat("#0.00").format(location.getX());
                        String y = new DecimalFormat("#0.00").format(location.getY());
                        String z = new DecimalFormat("#0.00").format(location.getZ());
                        System.out.println(x + ", " + y + ", " + z);
                        List<PlayerData> players = cameraJail.getPlayers();
                        List<String> pls = new ArrayList<>();
                        camers.add(name);
                        jailConfig.set(nameJail + "." + name + ".location.x", x);
                        jailConfig.set(nameJail + "." + name + ".location.y", y);
                        jailConfig.set(nameJail + "." + name + ".location.z", z);
                        for (PlayerData pl : players) {
                            jailConfig.createSection(nameJail + "." + name + "." + pl.getNick());
                            jailConfig.set(nameJail + "." + name + "." + pl.getNick() + ".time", pl.getTimeImp());
                            jailConfig.set(nameJail + "." + name + "." + pl.getNick() + ".timeStart", pl.getStartTimeImp());
                        }
                        jailConfig.set(nameJail + "." + name + ".players", pls);
                    }
                    jailConfig.set(nameJail + ".camers", camers);
                }
            }
            jailConfig.set("jails", nameJails);
        }
        try {
            jailConfig.save(jailFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static boolean addJail(String name) {
        Jail jail = new Jail(name);
        for (Jail jail1 : jails) {
            if (jail1.getName().equals(name))
                return false;
        }
        jails.add(jail);
        return true;
    }

    public static boolean removeJail(String name) {
        for (Jail jail1 : jails) {
            if (jail1.getName().equals(name)) {
                jails.remove(jail1);
                return true;
            }
        }
        return false;
    }

    public static boolean isJail(String name) {
        for (Jail jail1 : jails) {
            if (jail1.getName().equals(name))
                return true;
        }
        return false;
    }

    public static List<String> getListJails() {
        List<String> listjails = new ArrayList<>();
        for (Jail jail1 : jails) {
            listjails.add(jail1.getName());
        }
        return listjails;
    }

    public static boolean addCamera(String jailName, String num, Location location) {
        for (Jail jail1 : jails) {
            if (jail1.getName().equals(jailName)) {
                try {
                    return jail1.addCamera(Integer.parseInt(num), location);
                } catch (NumberFormatException | NullPointerException e) {
                    return false;
                }
            }
        }
        return false;
    }

    public static boolean removeCamera(String jailName, String num) {
        for (Jail jail1 : jails) {
            if (jail1.getName().equals(jailName)) {
                try {
                    return jail1.removeCamera(Integer.parseInt(num));
                } catch (NumberFormatException | NullPointerException e) {
                    return false;
                }
            }
        }
        return false;
    }

    public static List<String> getCamers(String jailName) {
        List<String> listcamers = new ArrayList<>();
        for (Jail jail1 : jails) {
            if (jail1.getName().equals(jailName)) {
                List<CameraJail> cameraJails = jail1.getCamers();
                for (CameraJail cameraJail : cameraJails) {
                    listcamers.add(String.valueOf(cameraJail.getId()));
                }
            }
        }
        return listcamers;
    }

    public static boolean autoAddPlayer(Player player, String time) {
        for (Jail jail1 : jails) {
            List<CameraJail> cameraJails = jail1.getCamers();
            for (CameraJail cameraJail : cameraJails) {
                if (!(cameraJail.getPlayers().size() == GlobalConfig.MAX_PLAYER_IN_CELL)) {
                    return cameraJail.addPlayerInCamera(player, time);
                }
            }
        }
        return false;
    }

    public static void isPlayerJailAndTP(Player player) {
        for (Jail jail1 : jails) {
            List<CameraJail> cameraJails = jail1.getCamers();
            for (CameraJail cameraJail : cameraJails) {
                if (cameraJail.isPlayerInCamera(player))
                    player.teleport(cameraJail.getLocation());
            }
        }
    }

    public static void leavePlayer(Player player) {
        for (Jail jail1 : jails) {
            List<CameraJail> cameraJails = jail1.getCamers();
            for (CameraJail cameraJail : cameraJails) {
                if (cameraJail.isPlayerInCamera(player))
                    cameraJail.onLeavePlayer(player);
            }
        }
    }

    public static boolean isTime(String time) {
        if (time.endsWith("d")) {
            time = time.replace("d", "");
            try {
                int timeint = Integer.parseInt(time);
                return timeint == 1;
            } catch (NumberFormatException | NullPointerException e) {
                return false;
            }
        }
        if (time.endsWith("h")) {
            time = time.replace("h", "");
            try {
                int timeint = Integer.parseInt(time);
                return timeint <= 23 && timeint > 0;
            } catch (NumberFormatException | NullPointerException e) {
                return false;
            }
        }
        if (time.endsWith("m")) {
            time = time.replace("m", "");
            try {
                int timeint = Integer.parseInt(time);
                return timeint <= 60 && timeint > 0;
            } catch (NumberFormatException | NullPointerException e) {
                return false;
            }
        }
        if (time.endsWith("s")) {
            time = time.replace("s", "");
            try {
                int timeint = Integer.parseInt(time);
                return timeint <= 60 && timeint >= 10;
            } catch (NumberFormatException | NullPointerException e) {
                return false;
            }
        }
        return false;
    }

    @SuppressWarnings("deprecation")
    private void load() {
        jailFile = new File(plugin.getDataFolder(), "jails.yml");
        if (!(jailFile.exists())) {
            if (!(jailFile.getParentFile().mkdirs())) System.out.println();
            plugin.saveResource("jails.yml", false);
            jailConfig = new YamlConfiguration();
            try {
                jailConfig.load(jailFile);
            } catch (InvalidConfigurationException | IOException e) {
                e.printStackTrace();
            }
            return;
        }
        jailConfig = new YamlConfiguration();
        try {
            jailConfig.load(jailFile);
        } catch (InvalidConfigurationException | IOException e) {
            e.printStackTrace();
        }
        if (!(jailConfig.getStringList("jails").isEmpty())) {
            List<String> jailNames = jailConfig.getStringList("jails");
            for (String jailName : jailNames) {
                Jail jail = new Jail(jailName);
                if (!(jailConfig.getStringList(jailName + ".camers").isEmpty())) {
                    List<String> cameraNames = jailConfig.getStringList(jailName + ".camers");
                    List<CameraJail> cameraJails = new ArrayList<>();
                    for (String cameraName : cameraNames) {
                        double x = jailConfig.getDouble(jailName + "." + cameraName + ".location.x");
                        double y = jailConfig.getDouble(jailName + "." + cameraName + ".location.y");
                        double z = jailConfig.getDouble(jailName + "." + cameraName + ".location.z");
                        Location loc = new Location(Bukkit.getWorld("world"), x, y, z);
                        CameraJail cameraJail = new CameraJail(Integer.parseInt(cameraName), loc);
                        if (!(jailConfig.getStringList(jailName + "." + cameraName + ".players").isEmpty())) {
                            List<String> nicks = jailConfig.getStringList(jailName + "." + cameraName + ".players");
                            List<PlayerData> pls = new ArrayList<>();
                            for (String nick : nicks) {
                                String time = jailConfig.getString(jailName + "." + cameraName + "." + nick + ".time");
                                long timeStart = jailConfig.getLong(jailName + "." + cameraName + "." + nick + ".timeStart");
                                OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(nick);
                                PlayerData playerData = new PlayerData(offlinePlayer);
                                playerData.setTimeImp(time);
                                playerData.setStartTimeImp(timeStart);
                            }
                            cameraJail.setPlayersInCamera(pls);
                        }
                        cameraJails.add(cameraJail);
                    }
                    jail.setCamers(cameraJails);
                }
                jails.add(jail);
            }
        }
    }
}
