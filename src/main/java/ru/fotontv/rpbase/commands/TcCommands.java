package ru.fotontv.rpbase.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import ru.fotontv.rpbase.data.PlayerData;
import ru.fotontv.rpbase.data.PlayersManager;

import javax.annotation.Nonnull;

public class TcCommands implements CommandExecutor {

    @Override
    public boolean onCommand(@Nonnull CommandSender sender, @Nonnull Command command, @Nonnull String label, @Nonnull String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            PlayerData data = PlayersManager.getPlayerData(player);
            if (data != null) {
                if (data.getCity() != null) {
                    data.setChatCity(!data.isChatCity());
                    player.sendMessage("§7Вы переключили чат города на " + data.isChatCity());
                    return true;
                }
                player.sendMessage("§cВы еще не вступили не в один город.");
                return true;
            }
        }
        return true;
    }
}
