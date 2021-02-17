package ru.fotontv.rpbase.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import ru.fotontv.rpbase.config.ChatConfig;
import ru.fotontv.rpbase.utils.Utils;

import javax.annotation.Nonnull;

public class MeCommand implements CommandExecutor {

    @Override
    public boolean onCommand(@Nonnull CommandSender sender, @Nonnull Command command, @Nonnull String label, @Nonnull String[] args) {
        if (!(sender instanceof Player))
            return true;
        Player player = (Player)sender;
        if (args.length < 1) {
            player.sendMessage(ChatConfig.meChatUsage
                    .replace("%label%", label));
            return true;
        }
        String message = Utils.extractMessage(args, 0);
        int radius = ChatConfig.meChatRadius;
        Utils.sendMessage(player, radius, ChatConfig.meChatFormat
                .replace("%name%", player.getName())
                .replace("%message%", message));
        return true;
    }
}
