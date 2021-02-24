package ru.fotontv.rpbase.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import ru.fotontv.rpbase.config.GlobalConfig;
import ru.fotontv.rpbase.enums.ProfessionsEnum;
import ru.fotontv.rpbase.modules.city.CitiesManager;
import ru.fotontv.rpbase.modules.player.PlayerData;
import ru.fotontv.rpbase.modules.player.PlayersManager;

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
                                if (player.getLocation().distance(passRec.getLocation()) > 7) {
                                    player.sendMessage("§cВы должны находиться рядом с игроком на расстоянии 7 блоков");
                                    return true;
                                }
                                if (CitiesManager.getCity(args[1]) != null) {
                                    if (!data.getPassport().isPickUpCity()) {
                                        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                                        Date date = new Date();
                                        String dateOfReceipt = dateFormat.format(date);
                                        data1.getPassport().setDateOfReceipt(dateOfReceipt);
                                        data1.getPassport().setIsPickUpCity(true);
                                        data1.getPassport().setPickUpCity(args[1]);
                                        new Thread(() -> PlayersManager.savePlayerData(data1)).start();
                                        player.sendMessage(GlobalConfig.PASSPORTOFFICER_PICKUPCITY.replace("{player}", args[0]));
                                        passRec.sendMessage(GlobalConfig.PLAYER_PASSPORT_PICKUPCITY);
                                        return true;
                                    }
                                    player.sendMessage(GlobalConfig.ISPICKUPCITYISTRUE);
                                }
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
