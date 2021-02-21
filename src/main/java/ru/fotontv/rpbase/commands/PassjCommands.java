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

public class PassjCommands implements CommandExecutor {

    @Override
    public boolean onCommand(@Nonnull CommandSender sender, @Nonnull Command command, @Nonnull String label, @Nonnull String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            PlayerData data = PlayersManager.getPlayerData(player);
            if (data != null) {
                if (command.getName().equals("passj")) {
                    if (args.length == 3) {
                        if (player.hasPermission("passport.law") ||
                                data.getProfession().equals(ProfessionsEnum.JUDGE)) {
                            Player player1 = Bukkit.getPlayer(args[0]);
                            PlayerData data1 = PlayersManager.getPlayerData(player1);
                            if (player1 != null && data1 != null) {
                                String criminal = args[1] + " - " + args[2];
                                data1.getPassport().addCriminalRecords(criminal);
                                PlayersManager.savePlayerData(data1);
                                player.sendMessage(GlobalConfig.JUDGE_CRIMINALRECORDS.replace("{player}", args[0]));
                                player1.sendMessage(GlobalConfig.PLAYER_PASSPORT_CRIMINALRECORDS);
                                return true;
                            }
                            player.sendMessage(GlobalConfig.PLAYER_NOTFOUND.replace("%s", args[0]));
                            return true;
                        }
                        player.sendMessage(GlobalConfig.PLAYER_NOPERMISSION);
                        return true;
                    }
                    return false;
                }
                return false;
            }
            player.sendMessage(GlobalConfig.PLAYER_NOTFOUND.replace("%s", player.getName()));
            return true;
        }
        return false;
    }
}
