package ru.fotontv.rpbase.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import ru.fotontv.rpbase.modules.player.PlayerData;
import ru.fotontv.rpbase.modules.player.PlayersManager;

import javax.annotation.Nonnull;

public class FupgradeCommands implements CommandExecutor {

    @Override
    public boolean onCommand(@Nonnull CommandSender sender, @Nonnull Command command, @Nonnull String label, @Nonnull String[] args) {
        if (!(sender instanceof Player)) {
            if (sender.isOp()) {
                if (args.length == 1) {
                    new Thread(() -> {
                        PlayerData data1 = PlayersManager.loadPlayerData(args[0]);
                        if (data1 != null) {
                            int levelCurrent = data1.getLevel();
                            if (levelCurrent == 5) {
                                sender.sendMessage("§fИгрок уже достиг §aмаксимального §fуровня!");
                                return;
                            }
                            data1.setLevel(levelCurrent + 1);
                            new Thread(() -> PlayersManager.savePlayerData(data1)).start();
                            sender.sendMessage("§aУровень игрока повышен!");
                        }
                    }).start();
                    return true;
                }
            }
            return true;
        }
        Player player = (Player)sender;
        if (player.isOp()) {
            if (args.length == 0) {
                PlayerData data = PlayersManager.getPlayerData(player);
                if (data != null) {
                    int levelCurrent = data.getLevel();
                    if (levelCurrent == 5) {
                        player.sendMessage("§fВы уже достигли §aмаксимального §fуровня!");
                        return true;
                    }
                    data.setLevel(levelCurrent + 1);
                    new Thread(() -> PlayersManager.savePlayerData(data)).start();
                    player.sendMessage("§aВаш уровень повышен.");
                    return true;
                }
            }
            if (args.length == 1) {
                new Thread(() -> {
                    PlayerData data1 = PlayersManager.loadPlayerData(args[0]);
                    if (data1 != null) {
                        int levelCurrent = data1.getLevel();
                        if (levelCurrent == 5) {
                            player.sendMessage("§fИгрок уже достиг §aмаксимального §fуровня!");
                            return;
                        }
                        data1.setLevel(levelCurrent + 1);
                        new Thread(() -> PlayersManager.savePlayerData(data1)).start();
                        player.sendMessage("§aУровень игрока повышен!");
                    }
                }).start();
                return true;
            }
        }
        return true;
    }
}
