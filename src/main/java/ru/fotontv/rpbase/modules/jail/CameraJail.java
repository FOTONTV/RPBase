package ru.fotontv.rpbase.modules.jail;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitScheduler;
import ru.fotontv.rpbase.RPBase;
import ru.fotontv.rpbase.modules.player.PlayerData;
import ru.fotontv.rpbase.modules.player.PlayersManager;

import java.util.ArrayList;
import java.util.List;

public class CameraJail {
    private final int id;
    private final Location location;
    private final List<PlayerData> players = new ArrayList<>();

    public CameraJail(int id, Location loc) {
        this.id = id;
        this.location = loc;
    }

    public int getId() {
        return id;
    }

    public Location getLocation() {
        return location;
    }

    public List<PlayerData> getPlayers() {
        return players;
    }

    public boolean addPlayerInCamera(Player player, String time) {
        if (players.isEmpty()) {
            PlayerData playerData1 = PlayersManager.getPlayerData(player);
            if (playerData1 != null) {
                playerData1.setStartTimeImp();
                playerData1.setTimeImp(time);
                players.add(playerData1);
                player.setGameMode(GameMode.ADVENTURE);
                player.teleport(location);
                startShelduler(playerData1);
                return true;
            }
            return false;
        }
        for (PlayerData playerData : players) {
            if (!(playerData.getNick().equals(player.getName()))) {
                PlayerData playerData1 = PlayersManager.getPlayerData(player);
                if (playerData1 != null) {
                    playerData1.setStartTimeImp();
                    playerData1.setTimeImp(time);
                    players.add(playerData1);
                    player.setGameMode(GameMode.ADVENTURE);
                    player.teleport(location);
                    startShelduler(playerData1);
                    return true;
                }
            }
        }
        return false;
    }

    private void startShelduler(PlayerData data) {
        BukkitScheduler scheduler = Bukkit.getServer().getScheduler();
        String timeImp = data.getTimeImp();
        long time = 20 * 5;
        if (!(timeImp.equals(""))) {
            if (timeImp.endsWith("d")) {
                timeImp = timeImp.replace("d", "");
                try {
                    int day = Integer.parseInt(timeImp);
                    time = (long) day * 24 * 60 * 60 * 20;
                } catch (NumberFormatException ignored) {
                }
            }
            if (timeImp.endsWith("h")) {
                timeImp = timeImp.replace("h", "");
                try {
                    int hours = Integer.parseInt(timeImp);
                    time = (long) hours * 60 * 60 * 20;
                } catch (NumberFormatException ignored) {
                }
            }
            if (timeImp.endsWith("m")) {
                timeImp = timeImp.replace("m", "");
                try {
                    int minuts = Integer.parseInt(timeImp);
                    time = (long) minuts * 60 * 20;
                } catch (NumberFormatException ignored) {
                }
            }
            if (timeImp.endsWith("s")) {
                timeImp = timeImp.replace("s", "");
                try {
                    int seconds = Integer.parseInt(timeImp);
                    time = (long) seconds * 20;
                } catch (NumberFormatException ignored) {
                }
            }
        }
        scheduler.scheduleSyncDelayedTask(RPBase.getPlugin(), () -> {
            data.getPlayer().setGameMode(GameMode.SURVIVAL);
            removePlayer(data.getNick());
        }, time);
    }

    public boolean isPlayerInCamera(Player player) {
        for (PlayerData playerData : players) {
            if (playerData.getNick().equals(player.getName()))
                return true;
        }
        return false;
    }

    @SuppressWarnings("deprecation")
    public void onLeavePlayer(Player player) {
        for (PlayerData playerData : players) {
            if (playerData.getNick().equals(player.getName())) {
                playerData.setOfflinePlayer(Bukkit.getOfflinePlayer(player.getName()));
            }
        }
    }

    public void setPlayersInCamera(List<PlayerData> playersInCamera) {
        players.addAll(playersInCamera);
    }

    public void removePlayer(String nick) {
        players.removeIf(playerData -> playerData.getNick().equals(nick));
    }
}
