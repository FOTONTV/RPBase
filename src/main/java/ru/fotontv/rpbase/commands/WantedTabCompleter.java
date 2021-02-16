package ru.fotontv.rpbase.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import ru.fotontv.rpbase.data.PlayerData;
import ru.fotontv.rpbase.data.PlayersManager;
import ru.fotontv.rpbase.data.ProfessionsEnum;
import ru.fotontv.rpbase.modules.wanted.WantedManager;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;

public class WantedTabCompleter implements TabCompleter {

    @Override
    public List<String> onTabComplete(@Nonnull CommandSender sender, @Nonnull Command command, @Nonnull String alias, @Nonnull String[] args) {
        List<String> tab = new ArrayList<>();
        if (sender instanceof Player) {
            Player player = (Player) sender;
            PlayerData data = PlayersManager.getPlayerData(player);
            if (data == null)
                return tab;
            if (player.hasPermission("police.wanted.add") ||
                    player.hasPermission("police.wanted.list") ||
                    player.hasPermission("police.wanted.remove") ||
                    data.getProfession().equals(ProfessionsEnum.POLICEMAN) ||
                    data.getProfession().equals(ProfessionsEnum.OFFICER) ||
                    data.getProfession().equals(ProfessionsEnum.CARETAKER)) {
                if (command.getName().equals("wanted")) {
                    if (args.length == 1) {
                        if (player.hasPermission("police.wanted.list") ||
                                data.getProfession().equals(ProfessionsEnum.POLICEMAN) ||
                                data.getProfession().equals(ProfessionsEnum.OFFICER) ||
                                data.getProfession().equals(ProfessionsEnum.CARETAKER))
                            tab.add("list");
                        if (player.hasPermission("police.wanted.remove") ||
                                data.getProfession().equals(ProfessionsEnum.OFFICER) ||
                                data.getProfession().equals(ProfessionsEnum.CARETAKER))
                            tab.add("remove");
                        return tab;
                    }
                    if (args.length == 2) {
                        if (args[0].equals("remove")) {
                            if (player.hasPermission("police.wanted.remove") ||
                                    data.getProfession().equals(ProfessionsEnum.OFFICER) ||
                                    data.getProfession().equals(ProfessionsEnum.CARETAKER))
                                tab.addAll(WantedManager.getWantedList());
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
        return tab;
    }
}
