package ru.fotontv.rpbase.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import ru.fotontv.rpbase.enums.ProfessionsEnum;
import ru.fotontv.rpbase.modules.jail.JailManager;
import ru.fotontv.rpbase.modules.player.PlayersManager;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class JailTabCompleter implements TabCompleter {

    @Override
    public List<String> onTabComplete(@Nonnull CommandSender sender, @Nonnull Command command, @Nonnull String alias, @Nonnull String[] args) {
        List<String> tab = new ArrayList<>();
        if (sender instanceof Player) {
            Player player = (Player) sender;
            if (command.getName().equals("jail")) {
                if (args.length == 1) {
                    if (player.hasPermission("police.jail.create") ||
                            Objects.requireNonNull(PlayersManager.getPlayerData(player)).getProfession().equals(ProfessionsEnum.CARETAKER))
                        tab.add("create");
                    if (player.hasPermission("police.jail.remove") ||
                            Objects.requireNonNull(PlayersManager.getPlayerData(player)).getProfession().equals(ProfessionsEnum.CARETAKER))
                        tab.add("remove");
                    if (player.hasPermission("police.jail.list") ||
                            Objects.requireNonNull(PlayersManager.getPlayerData(player)).getProfession().equals(ProfessionsEnum.CARETAKER))
                        tab.add("list");
                    return tab;
                }
                if (args.length == 2) {
                    if (args[0].equals("remove")) {
                        if (player.hasPermission("police.jail.remove") ||
                                Objects.requireNonNull(PlayersManager.getPlayerData(player)).getProfession().equals(ProfessionsEnum.CARETAKER)) {
                            return new ArrayList<>(JailManager.getListJails());
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
