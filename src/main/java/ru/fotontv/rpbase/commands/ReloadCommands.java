package ru.fotontv.rpbase.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import ru.fotontv.rpbase.RPBase;

import javax.annotation.Nonnull;

public class ReloadCommands implements CommandExecutor {

    @Override
    public boolean onCommand(@Nonnull CommandSender sender, @Nonnull Command command, @Nonnull String label, @Nonnull String[] args) {
        if (sender.isOp()) {
            RPBase.getPlugin().reloadConfig();
            RPBase.getPlugin().getConfigManager().load();
        }
        return true;
    }
}
