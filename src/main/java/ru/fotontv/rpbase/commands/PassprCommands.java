package ru.fotontv.rpbase.commands;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import ru.fotontv.rpbase.config.GlobalConfig;
import ru.fotontv.rpbase.enums.ProfessionsEnum;
import ru.fotontv.rpbase.modules.player.PlayerData;
import ru.fotontv.rpbase.modules.player.PlayersManager;

import javax.annotation.Nonnull;

public class PassprCommands implements CommandExecutor {

    @Override
    public boolean onCommand(@Nonnull CommandSender sender, @Nonnull Command command, @Nonnull String label, @Nonnull String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            PlayerData data = PlayersManager.getPlayerData(player);
            if (data != null) {
                if (command.getName().equals("passpr")) {
                    if (args.length == 2) {
                        if (player.hasPermission("passport.place") || data.getProfession().equals(ProfessionsEnum.PASSPORTOFFICER)) {
                            Player passRec = Bukkit.getPlayer(args[0]);
                            if (passRec != null) {
                                Location playerLoc1 = player.getLocation();
                                Location playerLoc2 = passRec.getLocation();
                                if (playerLoc1.distance(playerLoc2) <= 7.0) {
                                    PlayerData data1 = PlayersManager.getPlayerData(passRec);
                                    if (data1 != null) {
                                        data1.getPassport().setPlaceOfResidence(args[1]);
                                        PlayersManager.savePlayerData(data1);
                                        PlayersManager.savesConfigs();
                                        player.sendMessage(GlobalConfig.PASSPORTOFFICER_PLACEEDIT.replace("{player}", args[0]));
                                        passRec.sendMessage(GlobalConfig.PLAYER_PASSPORT_PLACEEDIT);
                                        return true;
                                    }
                                    player.sendMessage(GlobalConfig.PLAYER_NOTFOUND.replace("%s", args[0]));
                                    return true;
                                }
                                player.sendMessage(GlobalConfig.PASSPORTOFFICERINVALIDDISTANCE);
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
