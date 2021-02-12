package ru.fotontv.rpbase.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import ru.fotontv.rpbase.data.PlayerData;
import ru.fotontv.rpbase.data.PlayersManager;
import ru.fotontv.rpbase.data.ProfessionsEnum;
import ru.fotontv.rpbase.modules.config.ConfigManager;

import javax.annotation.Nonnull;

public class PassCommands implements CommandExecutor {

    @Override
    public boolean onCommand(@Nonnull CommandSender sender, @Nonnull Command command, @Nonnull String label, @Nonnull String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            if (command.getName().equals("pass")) {
                if (args.length == 0) {
                    PlayerData data = PlayersManager.getPlayerData(player);
                    if (data != null) {
                        if (player.hasPermission("passport.open") ||
                            ProfessionsEnum.isAll(data)) {
                            PlayersManager.openPass(player);
                            return true;
                        }
                    }
                    player.sendMessage(ConfigManager.PLAYER_NOTFOUND.replace("%s", player.getName()));
                    return true;
                }
                return false;
            }
            return false;
        }
        return false;
    }
}
