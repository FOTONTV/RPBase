package ru.fotontv.rpbase.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import ru.fotontv.rpbase.RPBase;
import ru.fotontv.rpbase.config.ChatConfig;
import ru.fotontv.rpbase.utils.DateUtils;

import javax.annotation.Nonnull;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class RPCommands implements CommandExecutor {

    @SuppressWarnings("ResultOfMethodCallIgnored")
    @Override
    public boolean onCommand(@Nonnull CommandSender sender, @Nonnull Command command, @Nonnull String label, @Nonnull String[] args) {
        if (args.length < 1) {
            sender.sendMessage("[§6RPBase] " + "/rp list - список добавленных в вайтлист игроков");
            sender.sendMessage("[§6RPBase] " + ChatColor.GREEN + "/rp add <ник игрока> <время> - добавить игрока на указанное время. Например: 30d. Доступные ключи: mhdwMy");
            sender.sendMessage("[§6RPBase] " + ChatColor.GREEN + "/rp add <ник игрока> * - добавить игрока навсегда");
            sender.sendMessage("[§6RPBase] " + ChatColor.GREEN + "/rp delete/remove <ник игрока> - удалить игрока из вайтлиста");
            sender.sendMessage("[§6RPBase] " + ChatColor.GREEN + "/rp delete/remove <ник игрока> d/data - удалить игрока вместе с данными");
            return true;
        }
        if (args[0].equalsIgnoreCase("list")) {
            if (!sender.hasPermission("rp.manage-players.list")) {
                sender.sendMessage("[§6RPBase] " + ChatColor.RED + "Недостаточно прав");
                return true;
            }
            sender.sendMessage("[§6RPBase] " + ChatColor.GREEN + "Список игроков в вайтлисте:");
            RPBase.getPlugin().whitelistedPlayers.forEach((playName, until) -> sender.sendMessage(ChatColor.GREEN + playName + " (до " + DateUtils.staticFromMills(until) + ")"));
            return true;
        }
        boolean add;
        if (args[0].equalsIgnoreCase("add") && args.length == 3) {
            add = true;
        } else {
            if ((!args[0].equalsIgnoreCase("remove") && !args[0].equalsIgnoreCase("delete")) || args.length < 2 || args.length > 3) {
                sender.sendMessage("[§6RPBase] " + ChatColor.RED + "Использование:");
                sender.sendMessage("[§6RPBase] " + ChatColor.RED + "/rp add <ник игрока> <время> - добавить игрока на указанное время. Например: 30d. Доступные ключи: mhdwMy");
                sender.sendMessage("[§6RPBase] " + ChatColor.RED + "/rp add <ник игрока> * - добавить игрока навсегда");
                sender.sendMessage("[§6RPBase] " + ChatColor.RED + "/rp delete/remove <ник игрока> - удалить игрока из вайтлиста");
                sender.sendMessage("[§6RPBase] " + ChatColor.RED + "/rp delete/remove <ник игрока> d/data - удалить игрока вместе с данными");
                return true;
            }
            add = false;
        }
        String targetName = args[1];
        List<String> messages = new ArrayList<>();
        if (add) {
            if (!sender.hasPermission("rp.manage-players.add")) {
                sender.sendMessage("[§6RPBase] " + ChatColor.RED + "Недостаточно прав");
                return true;
            }
            messages.add("[§6RPBase] " + ">>> Добавление игрока " + targetName + " <<<");
            long current = DateUtils.millsFromOffset(args[2]);
            Long previous = RPBase.getPlugin().whitelistedPlayers.put(targetName, current);
            if (!RPBase.getPlugin().saveWhitelist()) {
                messages.add(ChatColor.RED + "Не удалось сохранить вайтлист");
            } else if (previous == null) {
                messages.add(ChatColor.GREEN + "Игрок добавлен в вайтлист до " + DateUtils.staticFromMills(current));
            } else {
                messages.add(ChatColor.YELLOW + "Время нахождения в вайтлисте обновлено до " + DateUtils.staticFromMills(current));
            }
            messages.add(ChatColor.GREEN + "UUID игрока: " + RPBase.getPlugin().getOfflineUuid(targetName));
            messages.add("[§6RPBase] " + ">>> Добавление игрока " + targetName + " завершено <<<");
            Player targetPlayer = Bukkit.getPlayer(targetName);
            if (targetPlayer != null && !RPBase.getPlugin().isWhitelisted(targetPlayer.getName()))
                targetPlayer.kickPlayer(ChatConfig.msgNotWhitelisted);
            if (sender instanceof org.bukkit.command.RemoteConsoleCommandSender)
                Bukkit.getConsoleSender().sendMessage("[§6RPBase] " + ChatColor.GREEN + "Игроку " + targetName + " выдан доступ на " + args[2] + ", до " + DateUtils.staticFromMills(current));
        } else {
            if (!sender.hasPermission("rp.manage-players.remove")) {
                sender.sendMessage("[§6RPBase] " + ChatColor.RED + "Недостаточно прав");
                return true;
            }
            messages.add("[§6RPBase] " + ">>> Удаление игрока " + targetName + " <<<");
            Player targetPlayer = Bukkit.getPlayer(targetName);
            if (targetPlayer != null && targetPlayer.isOnline()) {
                targetPlayer.kickPlayer(ChatColor.RED + "Вы были удалены из вайтлиста");
                messages.add(ChatColor.GREEN + "Игрок кикнут с сервера");
            } else {
                messages.add(ChatColor.YELLOW + "Игрок не на сервере");
            }
            if (args.length >= 3 && (args[2].equalsIgnoreCase("d") || args[2].equalsIgnoreCase("data"))) {
                File worldFile = RPBase.getPlugin().getWorldFile(targetName);
                if (worldFile.isFile()) {
                    worldFile.delete();
                    messages.add(ChatColor.GREEN + "Игровые данные игрока удалены");
                } else {
                    messages.add(ChatColor.YELLOW + "Игровые данные не обнаружены");
                }
            }
            if (RPBase.getPlugin().whitelistedPlayers.remove(targetName) == null) {
                messages.add(ChatColor.YELLOW + "Игрок отсутствует в вайтлисте");
            } else if (RPBase.getPlugin().saveWhitelist()) {
                messages.add(ChatColor.GREEN + "Игрок удалён из вайтлиста");
            } else {
                messages.add(ChatColor.RED + "Не удалось сохранить вайтлист");
            }
            messages.add("[§6RPBase] " + ">>> Удаление игрока " + targetName + " завершено <<<");
        }
        for (String message : messages) {
            sender.sendMessage(message);
        }
        return true;
    }
}
