package ru.fotontv.rpbase.commands;

import net.luckperms.api.model.data.DataMutateResult;
import net.luckperms.api.model.group.Group;
import net.luckperms.api.model.user.User;
import net.luckperms.api.node.NodeType;
import net.luckperms.api.node.types.InheritanceNode;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import ru.fotontv.rpbase.RPBase;
import ru.fotontv.rpbase.data.PlayerData;
import ru.fotontv.rpbase.data.PlayersManager;
import ru.fotontv.rpbase.data.ProfessionsEnum;
import ru.fotontv.rpbase.modules.config.ConfigManager;

import javax.annotation.Nonnull;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

public class ProfCommands implements CommandExecutor {

    @Override
    public boolean onCommand(@Nonnull CommandSender sender, @Nonnull Command command, @Nonnull String label, @Nonnull String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            PlayerData data = PlayersManager.getPlayerData(player);
            if (data != null) {
                if (command.getName().equals("prof")) {
                    if (player.hasPermission("prof.*") ||
                            data.getProfession().equals(ProfessionsEnum.MAYOR)) {
                        if (args.length == 2) {
                            if (args[0].equals("remove")) {
                                Player player1 = Bukkit.getPlayer(args[1]);
                                PlayerData data1 = PlayersManager.getPlayerData(player1);
                                if (player1 != null && data1 !=null) {
                                    if (data.getCityName().equals(data1.getCityName())) {
                                        ProfessionsEnum professionsEnum1 = data1.getProfession();
                                        data1.setProfession(ProfessionsEnum.PLAYER);
                                        data1.passport.setProfession(ProfessionsEnum.PLAYER.getNameProf());
                                        CompletableFuture<User> futureUser = RPBase.api.getUserManager().loadUser(player1.getUniqueId());
                                        futureUser.thenAcceptAsync(user -> {
                                            Group group = RPBase.api.getGroupManager().getGroup(professionsEnum1.getLuckpermsGroup());
                                            if (group == null) {
                                                player.sendMessage("§cОШИБКА: Группа LuckPerms не найдена! Проверьте конфиг!");
                                                RPBase.getPlugin().getLogger().info("Group does not exist.");
                                                return;
                                            }
                                            InheritanceNode node = InheritanceNode.builder(group).value(true).build();
                                            DataMutateResult result = user.data().remove(node);
                                            if(!result.wasSuccessful()) {
                                                player.sendMessage("§cLuckPerms failed with " + result.name().toUpperCase() + ".");
                                                return;
                                            }
                                            RPBase.api.getUserManager().saveUser(user);
                                        });
                                        PlayersManager.savesConfigs();
                                        player.sendMessage(ConfigManager.MAYOR_REMPROF.replace("{player}", player1.getName()).replace("{prof}", professionsEnum1.getNameProf()));
                                        player1.sendMessage(ConfigManager.PLAYER_REMPROF.replace("{city}", data.getCityName()).replace("{prof}", professionsEnum1.getNameProf()));
                                        return true;
                                    }
                                    player.sendMessage(ConfigManager.PLAYERNOTCITY);
                                    return true;
                                }
                                player.sendMessage(ConfigManager.PLAYER_NOTFOUND.replace("%s", args[1]));
                                return true;
                            }
                            return false;
                        }
                        if (args.length == 3) {
                            if (args[0].equals("add")) {
                                Player player1 = Bukkit.getPlayer(args[1]);
                                PlayerData data1 = PlayersManager.getPlayerData(player1);
                                if (player1 != null && data1 !=null) {
                                    if (data.getCityName().equals(data1.getCityName())) {
                                        ProfessionsEnum professionsEnum = ProfessionsEnum.getProf(args[2]);
                                        if (!data.getCity().isValidProf(professionsEnum)) {
                                            player.sendMessage("§cНеобходим более высокий статус города!");
                                            return true;
                                        }
                                        if (!(professionsEnum.equals(ProfessionsEnum.MAYOR) || professionsEnum.equals(ProfessionsEnum.PLAYER) || professionsEnum.equals(ProfessionsEnum.THIEF))) {
                                            data1.setProfession(professionsEnum);
                                            data1.passport.setProfession(professionsEnum.getNameProf());
                                            PlayersManager.savePlayerData(data1);
                                            CompletableFuture<User> futureUser = RPBase.api.getUserManager().loadUser(player1.getUniqueId());
                                            futureUser.thenAcceptAsync(user -> {
                                                Group group = RPBase.api.getGroupManager().getGroup(professionsEnum.getLuckpermsGroup());
                                                if (group == null) {
                                                    player.sendMessage("§cОШИБКА: Группа LuckPerms не найдена! Проверьте конфиг!");
                                                    RPBase.getPlugin().getLogger().info("Group does not exist.");
                                                    return;
                                                }
                                                Set<String> groups = user.getNodes(NodeType.INHERITANCE).stream().map(InheritanceNode::getGroupName)
                                                        .collect(Collectors.toSet());
                                                if(groups.contains(group.getName())) {
                                                    player.sendMessage("§cОШИБКА: У игрока уже установлена эта группа в LuckPerms!");
                                                    RPBase.getPlugin().getLogger().info("Player already has the same rank.");
                                                    return;
                                                }
                                                InheritanceNode node = InheritanceNode.builder(group).value(true).build();
                                                DataMutateResult result = user.data().add(node);
                                                if(!result.wasSuccessful()) {
                                                    player.sendMessage("§cLuckPerms failed with " + result.name().toUpperCase() + ".");
                                                    return;
                                                }
                                                RPBase.api.getUserManager().saveUser(user);
                                            });
                                            PlayersManager.savesConfigs();
                                            player.sendMessage(ConfigManager.MAYOR_ADDPROF.replace("{player}", player1.getName()).replace("{prof}", professionsEnum.getNameProf()));
                                            player1.sendMessage(ConfigManager.PLAYER_ADDPROF.replace("{city}", data.getCityName()).replace("{prof}", professionsEnum.getNameProf()));
                                            return true;
                                        }
                                        player.sendMessage(ConfigManager.PROFFESSIONNOTMAYOR);
                                        return true;
                                    }
                                    player.sendMessage(ConfigManager.PLAYERNOTCITY);
                                    return true;
                                }
                                player.sendMessage(ConfigManager.PLAYER_NOTFOUND.replace("%s", args[1]));
                                return true;
                            }
                            return false;
                        }
                        return false;
                    }
                    player.sendMessage(ConfigManager.PLAYER_NOPERMISSION);
                    return true;
                }
                return false;
            }
            player.sendMessage(ConfigManager.PLAYER_NOTFOUND.replace("%s", player.getName()));
            return true;
        }
        return false;
    }
}
