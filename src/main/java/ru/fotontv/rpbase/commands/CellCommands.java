package ru.fotontv.rpbase.commands;

import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import ru.fotontv.rpbase.config.GlobalConfig;
import ru.fotontv.rpbase.enums.ProfessionsEnum;
import ru.fotontv.rpbase.modules.jail.JailManager;
import ru.fotontv.rpbase.modules.player.PlayersManager;

import javax.annotation.Nonnull;
import java.text.DecimalFormat;
import java.util.List;
import java.util.Objects;

public class CellCommands implements CommandExecutor {

    @Override
    public boolean onCommand(@Nonnull CommandSender sender, @Nonnull Command command, @Nonnull String label, @Nonnull String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            if (command.getName().equals("cell")) {
                if (args.length == 3) {
                    if (args[0].equals("spawn")) {
                        if (player.hasPermission("police.cell.spawn") ||
                                Objects.requireNonNull(PlayersManager.getPlayerData(player)).getProfession().equals(ProfessionsEnum.CARETAKER)) {
                            Location location = player.getLocation();
                            if (JailManager.isJail(args[1])) {
                                if (JailManager.addCamera(args[1], args[2], location)) {
                                    String x = new DecimalFormat("#0.00").format(location.getX());
                                    String y = new DecimalFormat("#0.00").format(location.getY());
                                    String z = new DecimalFormat("#0.00").format(location.getZ());
                                    player.sendMessage(GlobalConfig.POLICE_CELL_CREATE
                                            .replace("{x}", x)
                                            .replace("{y}", y)
                                            .replace("{z}", z));
                                    return true;
                                }
                                player.sendMessage(GlobalConfig.CELL_NOWCREATE.replace("%s", args[2]));
                                return true;
                            }
                            player.sendMessage(GlobalConfig.JAIL_NOTCREATE.replace("%s", args[1]));
                            return true;
                        }
                        player.sendMessage(GlobalConfig.PLAYER_NOPERMISSION);
                        return true;
                    }
                    if (args[0].equals("remove")) {
                        if (player.hasPermission("police.cell.remove") ||
                                Objects.requireNonNull(PlayersManager.getPlayerData(player)).getProfession().equals(ProfessionsEnum.CARETAKER)) {
                            if (JailManager.isJail(args[1])) {
                                if (JailManager.removeCamera(args[1], args[2])) {
                                    player.sendMessage(GlobalConfig.POLICE_CELL_REMOVE.replace("%s", args[2]));
                                    return true;
                                }
                                player.sendMessage(GlobalConfig.CELL_NOTCREATE.replace("%s", args[2]));
                                return true;
                            }
                            player.sendMessage(GlobalConfig.JAIL_NOTCREATE.replace("%s", args[1]));
                            return true;
                        }
                        player.sendMessage(GlobalConfig.PLAYER_NOPERMISSION);
                        return true;
                    }
                    return false;
                }
                if (args.length == 2) {
                    if (args[0].equals("list")) {
                        if (player.hasPermission("police.cell.list") ||
                                Objects.requireNonNull(PlayersManager.getPlayerData(player)).getProfession().equals(ProfessionsEnum.CARETAKER)) {
                            if (JailManager.isJail(args[1])) {
                                List<String> camers = JailManager.getCamers(args[1]);
                                player.sendMessage(GlobalConfig.POLICE_CELL_LIST);
                                for (String name : camers) {
                                    player.sendMessage(GlobalConfig.POLICE_CELL_LIST_NAME.replace("%s", name));
                                }
                                return true;
                            }
                            player.sendMessage(GlobalConfig.JAIL_NOTCREATE.replace("%s", args[1]));
                            return true;
                        }
                        player.sendMessage(GlobalConfig.PLAYER_NOPERMISSION);
                        return true;
                    }
                    return false;
                }
                return false;
            }
            return false;
        }
        return false;
    }
}
