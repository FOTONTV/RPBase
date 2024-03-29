package ru.fotontv.rpbase.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import ru.fotontv.rpbase.data.PlayersManager;
import ru.fotontv.rpbase.data.ProfessionsEnum;
import ru.fotontv.rpbase.modules.config.ConfigManager;
import ru.fotontv.rpbase.modules.jail.JailManager;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.Objects;

public class JailCommands implements CommandExecutor {

    @Override
    public boolean onCommand(@Nonnull CommandSender sender, @Nonnull Command command, @Nonnull String label, @Nonnull String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            if (command.getName().equals("jail")) {
                if (args.length == 2) {
                    if (args[0].equals("create")) {
                        if (player.hasPermission("police.jail.create") ||
                                Objects.requireNonNull(PlayersManager.getPlayerData(player)).getProfession().equals(ProfessionsEnum.CARETAKER)) {
                            if (JailManager.addJail(args[1])) {
                                player.sendMessage(ConfigManager.POLICE_JAIL_CREATE.replace("%s", args[1]));
                                return true;
                            }
                            player.sendMessage(ConfigManager.JAIL_NOWCREATE.replace("%s", args[1]));
                            return true;
                        }
                        player.sendMessage(ConfigManager.PLAYER_NOPERMISSION);
                        return true;
                    }
                    if (args[0].equals("remove")) {
                        if (player.hasPermission("police.jail.remove") ||
                                Objects.requireNonNull(PlayersManager.getPlayerData(player)).getProfession().equals(ProfessionsEnum.CARETAKER)) {
                            if (JailManager.removeJail(args[1])) {
                                player.sendMessage(ConfigManager.POLICE_JAIL_REMOVE.replace("%s", args[1]));
                                return true;
                            }
                            player.sendMessage(ConfigManager.JAIL_NOTCREATE.replace("%s", args[1]));
                            return true;
                        }
                        player.sendMessage(ConfigManager.PLAYER_NOPERMISSION);
                        return true;
                    }
                    return false;
                }
                if (args.length == 1) {
                    if (args[0].equals("list")) {
                        if (player.hasPermission("police.jail.list") ||
                                Objects.requireNonNull(PlayersManager.getPlayerData(player)).getProfession().equals(ProfessionsEnum.CARETAKER)) {
                            List<String> jails = JailManager.getListJails();
                            player.sendMessage(ConfigManager.POLICE_JAIL_LIST);
                            for (String name : jails) {
                                player.sendMessage(ConfigManager.POLICE_JAIL_LIST_NAME.replace("%s", name));
                            }
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
        return false;
    }
}
