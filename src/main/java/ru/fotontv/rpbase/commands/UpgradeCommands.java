package ru.fotontv.rpbase.commands;

import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import ru.fotontv.rpbase.modules.player.PlayerData;
import ru.fotontv.rpbase.modules.player.PlayersManager;

import javax.annotation.Nonnull;

public class UpgradeCommands implements CommandExecutor {

    @Override
    public boolean onCommand(@Nonnull CommandSender sender, @Nonnull Command command, @Nonnull String label, @Nonnull String[] args) {
        if (!(sender instanceof Player))
            return true;
        Player player = (Player)sender;
        if (args.length == 0) {
            PlayerData data = PlayersManager.getPlayerData(player);
            if (data != null) {
                if (data.getLevel() == 5) {
                    player.sendMessage("§fВы уже достигли §aмаксимального §fуровня!");
                    return true;
                }
                if (data.isNotAccessUpgradeTime()) {
                    int level = data.getLevel();
                    int goldOreAmount = 0;
                    ItemStack[] contents = player.getInventory().getContents();
                    for (ItemStack item : contents) {
                        if (item != null && item.getType().equals(Material.GOLD_ORE))
                            goldOreAmount += item.getAmount();
                    }
                    if (level == 0 && goldOreAmount >= 0) {
                        data.setLevel(level + 1);
                        new Thread(() -> PlayersManager.savePlayerData(data)).start();
                        player.sendMessage("§aВаш уровень повышен.");
                        return true;
                    }
                    if (level == 1 && goldOreAmount >= 30) {
                        data.setLevel(level + 1);
                        removeOre(player, 30);
                        new Thread(() -> PlayersManager.savePlayerData(data)).start();
                        player.sendMessage("§aВаш уровень повышен.");
                        return true;
                    }
                    if (level == 2 && goldOreAmount >= 60) {
                        data.setLevel(level + 1);
                        removeOre(player, 60);
                        new Thread(() -> PlayersManager.savePlayerData(data)).start();
                        player.sendMessage("§aВаш уровень повышен.");
                        return true;
                    }
                    if (level == 3 && goldOreAmount >= 120) {
                        data.setLevel(level + 1);
                        removeOre(player, 120);
                        new Thread(() -> PlayersManager.savePlayerData(data)).start();
                        player.sendMessage("§aВаш уровень повышен.");
                        return true;
                    }
                    if (level == 4 && goldOreAmount >= 240) {
                        data.setLevel(level + 1);
                        removeOre(player, 240);
                        new Thread(() -> PlayersManager.savePlayerData(data)).start();
                        player.sendMessage("§aВаш уровень повышен.");
                        return true;
                    }
                }
                player.sendMessage("§cВы не соблюдаете условие для поднятия уровня. Проверьте всё еще раз.");
                return true;
            }
        }
        return true;
    }

    private static void removeOre(Player player, int goldOre) {
        int resultOre = 0;
        ItemStack[] contents1 = player.getInventory().getContents();
        for (ItemStack stack : contents1) {
            if (resultOre == goldOre) break;
            if (!(stack == null)) {
                if (stack.getType() == Material.GOLD_ORE) {
                    if ((stack.getAmount() + resultOre) > goldOre) {
                        stack.setAmount(goldOre - resultOre);
                        resultOre += (goldOre - resultOre);
                    } else {
                        resultOre += stack.getAmount();
                        stack.setAmount(0);
                    }
                }
            }
        }
    }
}
