package ru.fotontv.rpbase.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import ru.fotontv.rpbase.data.PlayersManager;
import ru.fotontv.rpbase.data.ProfessionsEnum;
import ru.fotontv.rpbase.modules.jail.JailManager;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class CellTabCompleter implements TabCompleter {

    @Override
    public List<String> onTabComplete(@Nonnull CommandSender sender, @Nonnull Command command, @Nonnull String alias, @Nonnull String[] args) {
        List<String> tab = new ArrayList<>();
        if (sender instanceof Player) {
            Player player = (Player) sender;
            if (command.getName().equals("cell")) {
                if (args.length == 1) {
                    if (player.hasPermission("police.cell.spawn") ||
                            Objects.requireNonNull(PlayersManager.getPlayerData(player)).getProfession().equals(ProfessionsEnum.CARETAKER))
                        tab.add("spawn");
                    if (player.hasPermission("police.cell.remove") ||
                            Objects.requireNonNull(PlayersManager.getPlayerData(player)).getProfession().equals(ProfessionsEnum.CARETAKER))
                        tab.add("remove");
                    if (player.hasPermission("police.cell.list") ||
                            Objects.requireNonNull(PlayersManager.getPlayerData(player)).getProfession().equals(ProfessionsEnum.CARETAKER))
                        tab.add("list");
                    return tab;
                }
                if (args.length == 2) {
                    if (player.hasPermission("police.cell.spawn") ||
                            player.hasPermission("police.cell.remove") ||
                            player.hasPermission("police.cell.list") ||
                            Objects.requireNonNull(PlayersManager.getPlayerData(player)).getProfession().equals(ProfessionsEnum.CARETAKER))
                        return new ArrayList<>(JailManager.getListJails());
                    return tab;
                }
                if (args.length == 3) {
                    if (player.hasPermission("police.cell.remove") ||
                            player.hasPermission("police.cell.list") ||
                            Objects.requireNonNull(PlayersManager.getPlayerData(player)).getProfession().equals(ProfessionsEnum.CARETAKER)) {
                        if (args[0].equals("remove")) {
                            return new ArrayList<>(JailManager.getCamers(args[1]));
                        }
                        return tab;
                    }
                    return tab;
                }
                return tab;
            }
            return tab;
        }
        return tab;
    }
}
