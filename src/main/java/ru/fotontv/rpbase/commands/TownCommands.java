package ru.fotontv.rpbase.commands;

import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import ru.fotontv.rpbase.config.GlobalConfig;
import ru.fotontv.rpbase.enums.CityStatusEnum;
import ru.fotontv.rpbase.enums.ProfessionsEnum;
import ru.fotontv.rpbase.modules.city.CitiesManager;
import ru.fotontv.rpbase.modules.city.City;
import ru.fotontv.rpbase.modules.player.PlayerData;
import ru.fotontv.rpbase.modules.player.PlayersManager;

import javax.annotation.Nonnull;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

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
                            if (data.getLevel() < 2) {
                                player.sendMessage("§cУ игрока не достаточно уровня для создания города!");
                                return true;
                            }
                            if (CitiesManager.isCity(args[1])) {
                                player.sendMessage(GlobalConfig.CITYNOWACCEPT);
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
                                        CitiesManager.saveCities();
                                        player.sendMessage(GlobalConfig.PLAYER_CREATECITY.replace("{city}", args[1]));
                                        for (Player player1 : Bukkit.getOnlinePlayers())
                                            player1.sendMessage(GlobalConfig.PLAYERS_CREATECITY.replace("{player}", player.getName()).replace("{city}", args[1]));
                                        return true;
                                    }
                                    player.sendMessage(GlobalConfig.CITYNAMENOTVALID);
                                    return true;
                                }
                                player.sendMessage(GlobalConfig.CITYNOWANDORE);
                                return true;
                            }
                            player.sendMessage(GlobalConfig.PLAYER_NOPERMISSION);
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
                                        if (playerData.getLevel() >= 1) {
                                            if (playerData.getCity() == null) {
                                                playerData.setNickCityRequest(player.getName());
                                                PlayersManager.savePlayerData(playerData);
                                                player.sendMessage(GlobalConfig.MAYOR_INVITECITY.replace("{player}", player1.getName()));
                                                TextComponent yes = new TextComponent(ChatColor.GREEN + "принять");
                                                TextComponent no = new TextComponent(ChatColor.RED + "отказаться");
                                                yes.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/town accept"));
                                                yes.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("Принять").create()));
                                                no.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/town deny"));
                                                no.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("Отказаться").create()));
                                                String formaterText = GlobalConfig.PLAYER_INVITECITY.replace("{mayor}", player.getName()).replace("{city}", data.getCityName());
                                                player1.sendMessage(new ComponentBuilder(formaterText).create());
                                                player1.sendMessage(new ComponentBuilder(yes).append(ChatColor.WHITE + " или ").append(no).create());
                                                return true;
                                            }
                                            player.sendMessage(GlobalConfig.PLAYERNOWCITY);
                                            return true;
                                        }
                                        player.sendMessage("§cУ игрока не достаточно уровня для вступления в город!");
                                        return true;
                                    }
                                    player.sendMessage(GlobalConfig.NOTONLINEPLAYERREQUESTCITY);
                                    return true;
                                }
                                player.sendMessage(GlobalConfig.PLAYER_NOTFOUND.replace("%s", args[1]));
                                return true;
                            }
                            player.sendMessage(GlobalConfig.PLAYER_NOPERMISSION);
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
                                        CitiesManager.saveCities();
                                        player.sendMessage(GlobalConfig.MAYOR_KICKCITY.replace("{player}", player1.getName()));
                                        player1.sendMessage(GlobalConfig.PLAYER_KICKCITY.replace("{city}", data.getCityName()));
                                        return true;
                                    }
                                    player.sendMessage(GlobalConfig.PLAYERNOTCITY);
                                    return true;
                                }
                                player.sendMessage(GlobalConfig.PLAYER_NOTFOUND.replace("%s", args[1]));
                                return true;
                            }
                            player.sendMessage(GlobalConfig.PLAYER_NOPERMISSION);
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
                                            data.getCity().setMayor(player1.getName());
                                            data.setProfession(ProfessionsEnum.PLAYER);
                                            data.getPassport().setProfession(ProfessionsEnum.PLAYER.getNameProf());
                                            playerData.setProfession(ProfessionsEnum.MAYOR);
                                            playerData.getPassport().setProfession(ProfessionsEnum.MAYOR.getNameProf());
                                            PlayersManager.addPrefix(playerData.getPlayer(), playerData);
                                            PlayersManager.addPrefix(data.getPlayer(), data);
                                            PlayersManager.savePlayerData(data);
                                            PlayersManager.savePlayerData(playerData);
                                            CitiesManager.saveCities();
                                            player.sendMessage(GlobalConfig.MAYOR_TRANSFERMAYOR.replace("{player}", player1.getName()));
                                            player1.sendMessage(GlobalConfig.PLAYER_TRANSFERMAYOR.replace("{city}", playerData.getCityName()));
                                            return true;
                                        }
                                        player.sendMessage(GlobalConfig.PLAYERNOTCITY);
                                        return true;
                                    }
                                    return true;
                                }
                                player.sendMessage(GlobalConfig.PLAYER_NOTFOUND.replace("%s", args[1]));
                                return true;
                            }
                            player.sendMessage(GlobalConfig.PLAYER_NOPERMISSION);
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
                            player.sendMessage(GlobalConfig.PLAYER_NOPERMISSION);
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
                                    player.sendMessage(GlobalConfig.PLAYER_INVITECITYACCEPT.replace("{city}", playerData.getCityName()));
                                    player1.sendMessage(GlobalConfig.MAYOR_INVITECITYACCEPT.replace("{player}", player.getName()));
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
                                    player1.sendMessage(GlobalConfig.MAYOR_INVITECITYDENY.replace("{player}", player.getName()));
                                    player.sendMessage(GlobalConfig.PLAYER_INVITECITYDENY.replace("{city}", playerData.getCityName()));
                                }
                                data.setNickCityRequest("");
                                PlayersManager.savePlayerData(data);
                                return true;
                            }
                            return true;
                        }
                        if (args[0].equals("info")) {
                            if (player.hasPermission("town.info") ||
                                    ProfessionsEnum.isAll(data)) {
                                if (data.getCity() != null) {
                                    CitiesManager.openCityGUI(data.getCity(), player);
                                    return true;
                                }
                                return true;
                            }
                            player.sendMessage(GlobalConfig.PLAYER_NOPERMISSION);
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
                                    CitiesManager.saveCities();
                                    player.sendMessage(GlobalConfig.PLAYER_LEAVEINCITY.replace("{city}", cityName));
                                    return true;
                                }
                                return true;
                            }
                            player.sendMessage(GlobalConfig.PLAYER_NOPERMISSION);
                            return true;
                        }
                        if (args[0].equals("disband")) {
                            if (player.hasPermission("town.disband") ||
                                    data.getProfession().equals(ProfessionsEnum.MAYOR)) {
                                if (data.getCity() != null) {
                                    String cityName = data.getCityName();
                                    CitiesManager.disbandCity(data.getCity());
                                    PlayersManager.disbandCity(data);
                                    CitiesManager.saveCities();
                                    player.sendMessage(GlobalConfig.MAYOR_DISBANDCITI);
                                    for (Player player1 : Bukkit.getOnlinePlayers())
                                        player1.sendMessage(GlobalConfig.PLAYERS_DISBANDCITY.replace("{player}", player.getName()).replace("{city}", cityName));
                                    return true;
                                }
                                return true;
                            }
                            player.sendMessage(GlobalConfig.PLAYER_NOPERMISSION);
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
            player.sendMessage(GlobalConfig.PLAYER_NOTFOUND.replace("%s", player.getName()));
        }
        return false;
    }
}
