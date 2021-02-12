package ru.fotontv.rpbase.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import ru.fotontv.rpbase.data.CitiesManager;
import ru.fotontv.rpbase.data.PlayerData;
import ru.fotontv.rpbase.data.PlayersManager;
import ru.fotontv.rpbase.data.ProfessionsEnum;
import ru.fotontv.rpbase.modules.config.ConfigManager;

import javax.annotation.Nonnull;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class PasscrCommands implements CommandExecutor {

    @Override
    public boolean onCommand(@Nonnull CommandSender sender, @Nonnull Command command, @Nonnull String label, @Nonnull String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            PlayerData data = PlayersManager.getPlayerData(player);
            if (data != null) {
                if (command.getName().equals("passcr")) {
                    if (args.length == 2) {
                        if (player.hasPermission("passport.receiving") ||
                            data.getProfession().equals(ProfessionsEnum.PASSPORTOFFICER)) {
                            Player passRec = Bukkit.getPlayer(args[0]);
                            PlayerData data1 = PlayersManager.getPlayerData(passRec);
                            if (passRec != null && data1 != null) {
                                if (CitiesManager.getCity(args[1]) != null) {
                                    if (!data.getPassport().isPickUpCity()) {
                                        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                                        Date date = new Date();
                                        String dateOfReceipt = dateFormat.format(date);
                                        data1.getPassport().setDateOfReceipt(dateOfReceipt);
                                        data1.getPassport().setIsPickUpCity(true);
                                        data1.getPassport().setPickUpCity(args[1]);
                                        PlayersManager.savePlayerData(data1);
                                        PlayersManager.savesConfigs();
                                        player.sendMessage(ConfigManager.PASSPORTOFFICER_PICKUPCITY.replace("{player}", args[0]));
                                        passRec.sendMessage(ConfigManager.PLAYER_PASSPORT_PICKUPCITY);
                                        return true;
                                    }
                                    player.sendMessage(ConfigManager.ISPICKUPCITYISTRUE);
                                }
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
            player.sendMessage(ConfigManager.PLAYER_NOTFOUND.replace("%s", player.getName()));
            return true;
        }
        return false;
    }
}
