package ru.fotontv.rpbase.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import ru.fotontv.rpbase.enums.CityStatusEnum;
import ru.fotontv.rpbase.enums.ProfessionsEnum;
import ru.fotontv.rpbase.modules.player.PlayerData;
import ru.fotontv.rpbase.modules.player.PlayersManager;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;

public class TownTabCompleter implements TabCompleter {

    @Override
    public List<String> onTabComplete(@Nonnull CommandSender sender, @Nonnull Command command, @Nonnull String alias, @Nonnull String[] args) {
        List<String> tab = new ArrayList<>();
        if (sender instanceof Player) {
            Player player = (Player) sender;
            PlayerData data = PlayersManager.getPlayerData(player);
            if (data != null) {
                if (command.getName().equals("town")) {
                    if (args.length == 1) {
                        if (player.hasPermission("town.create") ||
                                !data.getProfession().equals(ProfessionsEnum.MAYOR)) {
                            tab.add("create");
                        }
                        if (player.hasPermission("town.upgrade") ||
                                data.getProfession().equals(ProfessionsEnum.MAYOR)) {
                            tab.add("upgrade");
                        }
                        if (player.hasPermission("town.leave") ||
                                !data.getProfession().equals(ProfessionsEnum.MAYOR)) {
                            tab.add("leave");
                        }
                        if (player.hasPermission("town.info") ||
                                ProfessionsEnum.isAll(data)) {
                            if (data.getCity() != null) {
                                tab.add("info");
                            }
                        }
                        if (player.hasPermission("town.add") ||
                                data.getProfession().equals(ProfessionsEnum.MAYOR)) {
                            tab.add("add");
                        }
                        if (player.hasPermission("town.kick") ||
                                data.getProfession().equals(ProfessionsEnum.MAYOR)) {
                            tab.add("kick");
                        }
                        if (player.hasPermission("town.transfer") ||
                                data.getProfession().equals(ProfessionsEnum.MAYOR)) {
                            tab.add("transfer");
                        }
                        if (player.hasPermission("town.disband") ||
                                data.getProfession().equals(ProfessionsEnum.MAYOR)) {
                            tab.add("disband");
                        }
                        tab.add("list");
                        return tab;
                    }
                    if (args.length == 2) {
                        if (args[0].equals("upgrade")) {
                            for (CityStatusEnum cityStatusEnum : CityStatusEnum.values())
                                tab.add(cityStatusEnum.getStatusName());
                        }
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
