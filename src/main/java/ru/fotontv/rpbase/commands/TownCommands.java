package ru.fotontv.rpbase.commands;

import net.luckperms.api.model.data.DataMutateResult;
import net.luckperms.api.model.group.Group;
import net.luckperms.api.model.user.User;
import net.luckperms.api.node.NodeType;
import net.luckperms.api.node.types.InheritanceNode;
import net.md_5.bungee.api.chat.*;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import ru.fotontv.rpbase.RPBase;
import ru.fotontv.rpbase.data.*;
import ru.fotontv.rpbase.modules.config.ConfigManager;

import javax.annotation.Nonnull;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;

public class TownCommands implements CommandExecutor {

    @Override
    public boolean onCommand(@Nonnull CommandSender sender, @Nonnull Command command, @Nonnull String label, @Nonnull String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            PlayerData data = PlayersManager.getPlayerData(player);
            if (data != null) {
                if (command.getName().equals("town")) {
                    if (args.length == 2) {
                        if (args[0].equals("create")) {
                            if (CitiesManager.isCity(args[1])) {
                                player.sendMessage(ConfigManager.CITYNOWACCEPT);
                                return true;
                            }
                            if (player.hasPermission("town.create") ||
                                !data.getProfession().equals(ProfessionsEnum.MAYOR)) {
                                int goldOreAmount = 0;
                                ItemStack[] contents = player.getInventory().getContents();
                                for (ItemStack item : contents) {
                                    if (item != null && item.getType().equals(Material.GOLD_ORE))
                                        goldOreAmount += item.getAmount();
                                }
                                if (goldOreAmount >= 160 && data.getCityName().equals("-")) {
                                    if ((args[1].length() <= 20)) {
                                        CompletableFuture<User> futureUser = RPBase.api.getUserManager().loadUser(player.getUniqueId());
                                        AtomicBoolean isGroup = new AtomicBoolean(false);
                                        futureUser.thenAcceptAsync(user -> {
                                            Group group = RPBase.api.getGroupManager().getGroup(ProfessionsEnum.MAYOR.getLuckpermsGroup());
                                            if (group == null) {
                                                player.sendMessage("§cОШИБКА: Группа LuckPerms не найдена! Проверьте конфиг!");
                                                RPBase.getPlugin().getLogger().info("Group does not exist.");
                                                isGroup.set(true);
                                                return;
                                            }
                                            Set<String> groups = user.getNodes(NodeType.INHERITANCE).stream().map(InheritanceNode::getGroupName)
                                                    .collect(Collectors.toSet());
                                            if(groups.contains(group.getName())) {
                                                player.sendMessage("§cОШИБКА: У игрока уже установлена профессия мэра!");
                                                RPBase.getPlugin().getLogger().info("Player already has the same rank.");
                                                isGroup.set(true);
                                                return;
                                            }
                                            InheritanceNode node = InheritanceNode.builder(group).value(true).build();
                                            DataMutateResult result = user.data().add(node);
                                            if(!result.wasSuccessful()) {
                                                player.sendMessage("§cLuckPerms failed with " + result.name().toUpperCase() + ".");
                                                isGroup.set(true);
                                                return;
                                            }
                                            RPBase.api.getUserManager().saveUser(user);
                                        });
                                        if (isGroup.get())
                                            return true;
                                        int resultOre = 0;
                                        ItemStack[] contents1 = player.getInventory().getContents();
                                        for (ItemStack stack : contents1) {
                                            if (resultOre == 160) break;
                                            if (!(stack == null)) {
                                                if (stack.getType() == Material.GOLD_ORE) {
                                                    if ((stack.getAmount() + resultOre) > 160) {
                                                        stack.setAmount(160 - resultOre);
                                                        resultOre += (160 - resultOre);
                                                    } else {
                                                        resultOre += stack.getAmount();
                                                        stack.setAmount(0);
                                                    }
                                                }
                                            }
                                        }
                                        data.setProfession(ProfessionsEnum.MAYOR);
                                        data.getPassport().setProfession(ProfessionsEnum.MAYOR.getNameProf());
                                        data.setCityName(args[1]);
                                        City city = new City(args[1], player.getName());
                                        city.addCitizen(player);
                                        CitiesManager.addCity(city);
                                        data.setCity(city);
                                        PlayersManager.savePlayerData(data);
                                        PlayersManager.savesConfigs();
                                        CitiesManager.saveCities();
                                        player.sendMessage(ConfigManager.PLAYER_CREATECITY.replace("{city}", args[1]));
                                        for (Player player1 : Bukkit.getOnlinePlayers())
                                            player1.sendMessage(ConfigManager.PLAYERS_CREATECITY.replace("{player}", player.getName()).replace("{city}", args[1]));
                                        return true;
                                    }
                                    player.sendMessage(ConfigManager.CITYNAMENOTVALID);
                                    return true;
                                }
                                player.sendMessage(ConfigManager.CITYNOWANDORE);
                                return true;
                            }
                            player.sendMessage(ConfigManager.PLAYER_NOPERMISSION);
                            return true;
                        }
                        if (args[0].equals("add")) {
                            if (player.hasPermission("town.add") ||
                                    data.getProfession().equals(ProfessionsEnum.MAYOR)) {
                                if (data.getCity().getCityStatus().getMaxCitizens() == data.getCity().getCitizen().size()) {
                                    player.sendMessage("§cНеобходим более высокий статус города!");
                                    return true;
                                }
                                Player player1 = Bukkit.getPlayer(args[1]);
                                if (player1 != null) {
                                    PlayerData playerData = PlayersManager.getPlayerData(player1);
                                    if (playerData != null) {
                                        if (playerData.getCity() == null) {
                                            playerData.setNickCityRequest(player.getName());
                                            PlayersManager.savePlayerData(playerData);
                                            player.sendMessage(ConfigManager.MAYOR_INVITECITY.replace("{player}", player1.getName()));
                                            TextComponent yes = new TextComponent(ChatColor.GREEN + "принять");
                                            TextComponent no = new TextComponent(ChatColor.RED + "отказаться");
                                            yes.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/town accept"));
                                            yes.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("Принять").create()));
                                            no.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/town deny"));
                                            no.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("Отказаться").create()));
                                            String formaterText = ConfigManager.PLAYER_INVITECITY.replace("{mayor}", player.getName()).replace("{city}", data.getCityName());
                                            player1.sendMessage(new ComponentBuilder(formaterText).create());
                                            player1.sendMessage(new ComponentBuilder(yes).append(ChatColor.WHITE + " или ").append(no).create());
                                            return true;
                                        }
                                        player.sendMessage(ConfigManager.PLAYERNOWCITY);
                                        return true;
                                    }
                                    player.sendMessage(ConfigManager.NOTONLINEPLAYERREQUESTCITY);
                                    return true;
                                }
                                player.sendMessage(ConfigManager.PLAYER_NOTFOUND.replace("%s", args[1]));
                                return true;
                            }
                            player.sendMessage(ConfigManager.PLAYER_NOPERMISSION);
                            return true;
                        }
                        if (args[0].equals("kick")) {
                            if (player.hasPermission("town.kick") ||
                                    data.getProfession().equals(ProfessionsEnum.MAYOR)) {
                                Player player1 = Bukkit.getPlayer(args[1]);
                                PlayerData playerData = PlayersManager.getPlayerData(player1);
                                if (player1 != null && playerData != null) {
                                    if (playerData.getCityName().equals(data.getCityName())) {
                                        playerData.setCity(null);
                                        playerData.setCityName("-");
                                        playerData.setDateInput("-");
                                        data.getCity().kickCitizen(player1);
                                        PlayersManager.savePlayerData(playerData);
                                        PlayersManager.savePlayerData(data);
                                        PlayersManager.savesConfigs();
                                        CitiesManager.saveCities();
                                        player.sendMessage(ConfigManager.MAYOR_KICKCITY.replace("{player}", player1.getName()));
                                        player1.sendMessage(ConfigManager.PLAYER_KICKCITY.replace("{city}", data.getCityName()));
                                        return true;
                                    }
                                    player.sendMessage(ConfigManager.PLAYERNOTCITY);
                                    return true;
                                }
                                player.sendMessage(ConfigManager.PLAYER_NOTFOUND.replace("%s", args[1]));
                                return true;
                            }
                            player.sendMessage(ConfigManager.PLAYER_NOPERMISSION);
                            return true;
                        }
                        if (args[0].equals("transfer")) {
                            if (player.hasPermission("town.transfer") ||
                                    data.getProfession().equals(ProfessionsEnum.MAYOR)) {
                                Player player1 = Bukkit.getPlayer(args[1]);
                                PlayerData playerData = PlayersManager.getPlayerData(player1);
                                if (player1 != null && playerData != null) {
                                    if (playerData.getProfession() != ProfessionsEnum.MAYOR) {
                                        if (playerData.getCityName().equals(data.getCityName())) {
                                            CompletableFuture<User> futureUser = RPBase.api.getUserManager().loadUser(player.getUniqueId());
                                            futureUser.thenAcceptAsync(user -> {
                                                Group group = RPBase.api.getGroupManager().getGroup(ProfessionsEnum.MAYOR.getLuckpermsGroup());
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
                                            CompletableFuture<User> futureUser1 = RPBase.api.getUserManager().loadUser(player1.getUniqueId());
                                            futureUser1.thenAcceptAsync(user -> {
                                                Group group = RPBase.api.getGroupManager().getGroup(ProfessionsEnum.MAYOR.getLuckpermsGroup());
                                                if (group == null) {
                                                    player.sendMessage("§cОШИБКА: Группа LuckPerms не найдена! Проверьте конфиг!");
                                                    RPBase.getPlugin().getLogger().info("Group does not exist.");
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
                                            data.getCity().setMayor(player1.getName());
                                            data.setProfession(ProfessionsEnum.PLAYER);
                                            data.getPassport().setProfession(ProfessionsEnum.PLAYER.getNameProf());
                                            playerData.setProfession(ProfessionsEnum.MAYOR);
                                            playerData.getPassport().setProfession(ProfessionsEnum.MAYOR.getNameProf());
                                            PlayersManager.savePlayerData(data);
                                            PlayersManager.savePlayerData(playerData);
                                            PlayersManager.savesConfigs();
                                            CitiesManager.saveCities();
                                            player.sendMessage(ConfigManager.MAYOR_TRANSFERMAYOR.replace("{player}", player1.getName()));
                                            player1.sendMessage(ConfigManager.PLAYER_TRANSFERMAYOR.replace("{city}", playerData.getCityName()));
                                            return true;
                                        }
                                        player.sendMessage(ConfigManager.PLAYERNOTCITY);
                                        return true;
                                    }
                                    return true;
                                }
                                player.sendMessage(ConfigManager.PLAYER_NOTFOUND.replace("%s", args[1]));
                                return true;
                            }
                            player.sendMessage(ConfigManager.PLAYER_NOPERMISSION);
                            return true;
                        }
                        if (args[0].equals("upgrade")) {
                            if (player.hasPermission("town.upgrade") ||
                                    data.getProfession().equals(ProfessionsEnum.MAYOR)) {
                                City city = data.getCity();
                                if (city != null) {
                                    if (city.getCityStatus().equals(CityStatusEnum.METROPOLIS)) {
                                        player.sendMessage("§fУ вас максимальный статус города!");
                                        return true;
                                    }
                                    city.upgrade(data);
                                    return true;
                                }
                                return true;
                            }
                            player.sendMessage(ConfigManager.PLAYER_NOPERMISSION);
                            return true;
                        }
                        return false;
                    }
                    if (args.length == 1) {
                        if (args[0].equals("accept")) {
                            if (!(data.getNickCityRequest().equals(""))) {
                                Player player1 = Bukkit.getPlayer(data.getNickCityRequest());
                                PlayerData playerData = PlayersManager.getPlayerData(player1);
                                if (player1 != null && playerData != null) {
                                    data.setCity(playerData.getCity());
                                    data.setCityName(playerData.getCityName());
                                    DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                                    Date date = new Date();
                                    data.setDateInput(dateFormat.format(date));
                                    data.setNickCityRequest("");
                                    playerData.getCity().addCitizen(player);
                                    PlayersManager.savePlayerData(data);
                                    PlayersManager.savePlayerData(playerData);
                                    PlayersManager.savesConfigs();
                                    player.sendMessage(ConfigManager.PLAYER_INVITECITYACCEPT.replace("{city}", playerData.getCityName()));
                                    player1.sendMessage(ConfigManager.MAYOR_INVITECITYACCEPT.replace("{player}", player.getName()));
                                    return true;
                                }
                                return true;
                            }
                            return true;
                        }
                        if (args[0].equals("deny")) {
                            if (!(data.getNickCityRequest().equals(""))) {
                                Player player1 = Bukkit.getPlayer(data.getNickCityRequest());
                                PlayerData playerData = PlayersManager.getPlayerData(player1);
                                if (player1 != null && playerData != null) {
                                    player1.sendMessage(ConfigManager.MAYOR_INVITECITYDENY.replace("{player}", player.getName()));
                                    player.sendMessage(ConfigManager.PLAYER_INVITECITYDENY.replace("{city}", playerData.getCityName()));
                                }
                                data.setNickCityRequest("");
                                PlayersManager.savePlayerData(data);
                                return true;
                            }
                            return true;
                        }
                        if (args[0].equals("info")) {
                            if (player.hasPermission("town.info") ||
                                    data.getProfession().equals(ProfessionsEnum.MAYOR)) {
                                CitiesManager.openCityGUI(data.getCity(), player);
                                return true;
                            }
                            player.sendMessage(ConfigManager.PLAYER_NOPERMISSION);
                            return true;
                        }
                        if (args[0].equals("leave")) {
                            if (player.hasPermission("town.leave") || !(data.getProfession().equals(ProfessionsEnum.MAYOR))) {
                                if (data.getCity() != null) {
                                    String cityName = data.getCityName();
                                    data.getCity().kickCitizen(player);
                                    data.setCity(null);
                                    data.setCityName("-");
                                    PlayersManager.savePlayerData(data);
                                    PlayersManager.savesConfigs();
                                    CitiesManager.saveCities();
                                    player.sendMessage(ConfigManager.PLAYER_LEAVEINCITY.replace("{city}", cityName));
                                    return true;
                                }
                                return true;
                            }
                            player.sendMessage(ConfigManager.PLAYER_NOPERMISSION);
                            return true;
                        }
                        if (args[0].equals("disband")) {
                            if (player.hasPermission("town.disband") ||
                                    data.getProfession().equals(ProfessionsEnum.MAYOR)) {
                                if (data.getCity() != null) {
                                    CompletableFuture<User> futureUser = RPBase.api.getUserManager().loadUser(player.getUniqueId());
                                    futureUser.thenAcceptAsync(user -> {
                                        Group group = RPBase.api.getGroupManager().getGroup(ProfessionsEnum.MAYOR.getLuckpermsGroup());
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
                                    String cityName = data.getCityName();
                                    CitiesManager.disbandCity(data.getCity());
                                    PlayersManager.disbandCity(data);
                                    PlayersManager.savesConfigs();
                                    CitiesManager.saveCities();
                                    player.sendMessage(ConfigManager.MAYOR_DISBANDCITI);
                                    for (Player player1 : Bukkit.getOnlinePlayers())
                                        player1.sendMessage(ConfigManager.PLAYERS_DISBANDCITY.replace("{player}", player.getName()).replace("{city}", cityName));
                                    return true;
                                }
                                return true;
                            }
                            player.sendMessage(ConfigManager.PLAYER_NOPERMISSION);
                            return true;
                        }
                        if (args[0].equals("list")) {
                            CitiesManager.openCityList(player);
                            return true;
                        }
                        return false;
                    }
                    return false;
                }
                return false;
            }
            player.sendMessage(ConfigManager.PLAYER_NOTFOUND.replace("%s", player.getName()));
        }
        return false;
    }
}
