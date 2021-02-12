package ru.fotontv.rpbase.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import ru.fotontv.rpbase.data.PlayerData;
import ru.fotontv.rpbase.data.PlayersManager;
import ru.fotontv.rpbase.data.ProfessionsEnum;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;

public class ProfTabCompleter implements TabCompleter {

    @Override
    public List<String> onTabComplete(@Nonnull CommandSender sender, @Nonnull Command command, @Nonnull String alias, @Nonnull String[] args) {
        List<String> tab = new ArrayList<>();
        if (sender instanceof Player) {
            Player player = (Player) sender;
            PlayerData data = PlayersManager.getPlayerData(player);
            if (data != null) {
                if (command.getName().equals("prof")) {
                    if (player.hasPermission("prof.*") ||
                            data.getProfession().equals(ProfessionsEnum.MAYOR)) {
                        if (args.length == 1) {
                            tab.add("add");
                            tab.add("remove");
                            return tab;
                        }
                        if (args.length == 3) {
                            for (ProfessionsEnum professionsEnum : ProfessionsEnum.values()) {
                                if (!((professionsEnum.equals(ProfessionsEnum.MAYOR) || professionsEnum.equals(ProfessionsEnum.PLAYER) || professionsEnum.equals(ProfessionsEnum.THIEF))))
                                    tab.add(professionsEnum.getNameProf());
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
        return tab;
    }
}
