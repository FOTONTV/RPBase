package ru.fotontv.rpbase.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import ru.fotontv.rpbase.data.PlayersManager;
import ru.fotontv.rpbase.data.ProfessionsEnum;
import ru.fotontv.rpbase.modules.config.ConfigManager;
import ru.fotontv.rpbase.modules.jail.JailManager;
import ru.fotontv.rpbase.modules.wanted.WantedManager;

import javax.annotation.Nonnull;
import java.util.Objects;

public class ImpCommands implements CommandExecutor {

    @Override
    public boolean onCommand(@Nonnull CommandSender sender, @Nonnull Command command, @Nonnull String label, @Nonnull String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            if (command.getName().equals("imp")) {
                if (player.hasPermission("police.imprison") ||
                        Objects.requireNonNull(PlayersManager.getPlayerData(player)).getProfession().equals(ProfessionsEnum.OFFICER)) {
                    if (args.length == 2) {
                        Player nick = Bukkit.getPlayer(args[0]);
                        if (nick != null) {
                            if (!(player.getName().equals(args[0]))) {
                                if (WantedManager.isPlayerWanted(nick)) {
                                    if (JailManager.isTime(args[1])) {
                                        if (JailManager.autoAddPlayer(nick, args[1])) {
                                            player.sendMessage(ConfigManager.POLICE_CELL_ADDPLAYER.replace("{player}", args[0]).replace("{time}", args[1]));
                                            return true;
                                        }
                                        player.sendMessage(ConfigManager.JAIL_NOTCELL);
                                        return true;
                                    }
                                    player.sendMessage(ConfigManager.NOTTIME);
                                    return true;
                                }
                                player.sendMessage(ConfigManager.NOTPLAYER_WANTEDLIST.replace("%s", args[0]));
                                return true;
                            }
                            player.sendMessage(ConfigManager.NOTPLAYER_IN_CELL);
                            return true;
                        }
                        player.sendMessage(ConfigManager.PLAYER_NOTFOUND.replace("%s", args[0]));
                        return true;
                    }
                    return false;
                }
                player.sendMessage(ConfigManager.PLAYER_NOPERMISSION);
                return true;
            }
            return false;
        }
        return false;
    }
}
