package ru.fotontv.rpbase.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import ru.fotontv.rpbase.data.PlayerData;
import ru.fotontv.rpbase.data.PlayersManager;
import ru.fotontv.rpbase.data.ProfessionsEnum;
import ru.fotontv.rpbase.modules.config.ConfigManager;
import ru.fotontv.rpbase.modules.wanted.WantedManager;

import javax.annotation.Nonnull;

public class WantedCommands implements CommandExecutor {

    @Override
    public boolean onCommand(@Nonnull CommandSender sender, @Nonnull Command command, @Nonnull String label, @Nonnull String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            if (command.getName().equals("wanted")) {
                PlayerData data = PlayersManager.getPlayerData(player);
                if (data == null)
                    return true;
                if (args.length == 2) {
                    if (args[0].equals("remove")) {
                        if (player.hasPermission("police.wanted.remove") ||
                                data.getProfession().equals(ProfessionsEnum.OFFICER) || data.getProfession().equals(ProfessionsEnum.CARETAKER)) {
                            Player nick = Bukkit.getPlayer(args[1]);
                            if (nick != null) {
                                if (!(player.getName().equalsIgnoreCase(nick.getName()))) {
                                    if (WantedManager.isPlayerWanted(nick)) {
                                        WantedManager.removeWanted(nick);
                                        player.sendMessage(ConfigManager.POLICE_WANTED_REMOVE.replace("%s", args[1]));
                                        nick.sendMessage(ConfigManager.PLAYER_WANTED_REMOVE);
                                        return true;
                                    }
                                    player.sendMessage(ConfigManager.NOTPLAYER_WANTEDLIST.replace("%s", args[1]));
                                    return true;
                                }
                                player.sendMessage(ConfigManager.NOTPLAYER_REMOVEWANTEDLIST);
                                return true;
                            }
                            player.sendMessage(ConfigManager.PLAYER_NOTFOUND.replace("%s", args[1]));
                            return true;
                        }
                        player.sendMessage(ConfigManager.PLAYER_NOPERMISSION);
                        return true;
                    }
                    return false;
                }
                if (args.length == 1) {
                    if (args[0].equals("list")) {
                        if (player.hasPermission("police.wanted.list") ||
                                data.getProfession().equals(ProfessionsEnum.POLICEMAN) ||
                                data.getProfession().equals(ProfessionsEnum.OFFICER) ||
                                data.getProfession().equals(ProfessionsEnum.CARETAKER)) {
                            player.sendMessage(ConfigManager.POLICE_WANTEDLIST);
                            for (String nick : WantedManager.getWantedList()) {
                                player.sendMessage(ConfigManager.POLICE_WANTEDLIST_NICK.replace("%s", nick));
                            }
                            return true;
                        }
                        player.sendMessage(ConfigManager.PLAYER_NOPERMISSION);
                        return true;
                    }
                    if (player.hasPermission("police.wanted.add") || data.getProfession().equals(ProfessionsEnum.POLICEMAN) ||
                            data.getProfession().equals(ProfessionsEnum.OFFICER) || data.getProfession().equals(ProfessionsEnum.CARETAKER)) {
                        Player nick = Bukkit.getPlayer(args[0]);
                        if (nick != null) {
                            if (!(player.getName().equalsIgnoreCase(nick.getName()))) {
                                if (!(WantedManager.isPlayerWanted(nick))) {
                                    WantedManager.setWantedList(nick);
                                    player.sendMessage(ConfigManager.POLICE_WANTEDLIST_ADD.replace("%s", args[0]));
                                    nick.sendMessage(ConfigManager.PLAYER_WANTEDLIST_ADD);
                                    return true;
                                }
                                player.sendMessage(ConfigManager.PLAYER_NOWWANTEDLIST.replace("%s", args[0]));
                                return true;
                            }
                            player.sendMessage(ConfigManager.NOTPLAYER_ADDWANTEDLIST);
                            return true;
                        }
                        player.sendMessage(ConfigManager.PLAYER_NOTFOUND.replace("%s", args[0]));
                        return true;
                    }
                    player.sendMessage(ConfigManager.PLAYER_NOPERMISSION);
                    return true;
                }
                return false;
            }
            return false;
        }
        return false;
    }
}
