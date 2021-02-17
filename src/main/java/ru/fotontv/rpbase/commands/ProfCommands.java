package ru.fotontv.rpbase.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import ru.fotontv.rpbase.config.GlobalConfig;
import ru.fotontv.rpbase.enums.ProfessionsEnum;
import ru.fotontv.rpbase.modules.player.PlayerData;
import ru.fotontv.rpbase.modules.player.PlayersManager;

import javax.annotation.Nonnull;

public class ProfCommands implements CommandExecutor {

    @Override
    public boolean onCommand(@Nonnull CommandSender sender, @Nonnull Command command, @Nonnull String label, @Nonnull String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            PlayerData data = PlayersManager.getPlayerData(player);
            if (data != null) {
                if (command.getName().equals("prof")) {
                    if (player.hasPermission("prof.*") ||
                            data.getProfession().equals(ProfessionsEnum.MAYOR)) {
                        if (args.length == 2) {
                            if (args[0].equals("remove")) {
                                Player player1 = Bukkit.getPlayer(args[1]);
                                PlayerData data1 = PlayersManager.getPlayerData(player1);
                                if (player1 != null && data1 != null && !player.getName().equals(player1.getName())) {
                                    if (data.getCityName().equals(data1.getCityName())) {
                                        if (data1.getProfession().equals(ProfessionsEnum.THIEF))
                                            return true;
                                        if (data1.isNotAccessProf()) {
                                            player.sendMessage("§cУ этого игрока вы не можете снять профессию сейчас!");
                                            player1.sendMessage("§cВы не можете быть сняты с профессии сейчас!");
                                            return true;
                                        }
                                        ProfessionsEnum professionsEnum1 = data1.getProfession();
                                        data1.setProfession(ProfessionsEnum.PLAYER);
                                        data1.passport.setProfession(ProfessionsEnum.PLAYER.getNameProf());
                                        PlayersManager.removePex(player1, data1);
                                        PlayersManager.savesConfigs();
                                        player.sendMessage(GlobalConfig.MAYOR_REMPROF.replace("{player}", player1.getName()).replace("{prof}", professionsEnum1.getNameProf()));
                                        player1.sendMessage(GlobalConfig.PLAYER_REMPROF.replace("{city}", data.getCityName()).replace("{prof}", professionsEnum1.getNameProf()));
                                        return true;
                                    }
                                    player.sendMessage(GlobalConfig.PLAYERNOTCITY);
                                    return true;
                                }
                                player.sendMessage(GlobalConfig.PLAYER_NOTFOUND.replace("%s", args[1]));
                                return true;
                            }
                            return false;
                        }
                        if (args.length == 3) {
                            if (args[0].equals("add")) {
                                Player player1 = Bukkit.getPlayer(args[1]);
                                PlayerData data1 = PlayersManager.getPlayerData(player1);
                                if (player1 != null && data1 != null && !player.getName().equals(player1.getName())) {
                                    if (data.getCityName().equals(data1.getCityName())) {
                                        ProfessionsEnum professionsEnum = ProfessionsEnum.getProf(args[2]);
                                        if (!(data1.isVip()) &&
                                                !(data1.isPremium()) &&
                                                !(data1.isExtreme()) &&
                                                !(data1.isAglem()) &&
                                                !(data1.isGlorious())) {
                                            if (!data.getCity().isValidProf(professionsEnum)) {
                                                player.sendMessage("§cНеобходим более высокий статус города!");
                                                return true;
                                            }
                                        }
                                        if (data1.isNotAccessProf()) {
                                            player.sendMessage("§cЭтот игрок не может получить новую профессию сейчас!");
                                            player1.sendMessage("§cВы не можете получить новую профессию сейчас!");
                                            return true;
                                        }
                                        if (!(professionsEnum.equals(ProfessionsEnum.MAYOR) || professionsEnum.equals(ProfessionsEnum.PLAYER) || professionsEnum.equals(ProfessionsEnum.THIEF))) {
                                            data1.setProfession(professionsEnum);
                                            data1.passport.setProfession(professionsEnum.getNameProf());
                                            data1.setCurrentDateAddProf();
                                            PlayersManager.removePex(player1, data1);
                                            PlayersManager.addPex(professionsEnum, player1, data1);
                                            PlayersManager.savePlayerData(data1);
                                            PlayersManager.savesConfigs();
                                            player.sendMessage(GlobalConfig.MAYOR_ADDPROF.replace("{player}", player1.getName()).replace("{prof}", professionsEnum.getNameProf()));
                                            player1.sendMessage(GlobalConfig.PLAYER_ADDPROF.replace("{city}", data.getCityName()).replace("{prof}", professionsEnum.getNameProf()));
                                            return true;
                                        }
                                        player.sendMessage(GlobalConfig.PROFFESSIONNOTMAYOR);
                                        return true;
                                    }
                                    player.sendMessage(GlobalConfig.PLAYERNOTCITY);
                                    return true;
                                }
                                player.sendMessage(GlobalConfig.PLAYER_NOTFOUND.replace("%s", args[1]));
                                return true;
                            }
                            return false;
                        }
                        return false;
                    }
                    player.sendMessage(GlobalConfig.PLAYER_NOPERMISSION);
                    return true;
                }
                return false;
            }
            player.sendMessage(GlobalConfig.PLAYER_NOTFOUND.replace("%s", player.getName()));
            return true;
        }
        return false;
    }
}
