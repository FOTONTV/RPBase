package ru.fotontv.rpbase.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import ru.fotontv.rpbase.RPBase;

import javax.annotation.Nonnull;

public class ReloadCommands implements CommandExecutor {

    @Override
    public boolean onCommand(@Nonnull CommandSender sender, @Nonnull Command command, @Nonnull String label, @Nonnull String[] args) {
        if (sender.isOp()) {
            if (args[0].equals("reload")) {
                RPBase.getPlugin().reloadConfig();
                RPBase.getPlugin().getConfigManager().load();
                RPBase.getPlugin().reloadData();
                sender.sendMessage(ChatColor.GREEN + "Конфиг и вайтлист успешно перезагружены");
            }
        }
        return true;
    }
}
