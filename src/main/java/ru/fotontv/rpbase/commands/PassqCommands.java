package ru.fotontv.rpbase.commands;

import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import ru.fotontv.rpbase.data.PlayerData;
import ru.fotontv.rpbase.data.PlayersManager;
import ru.fotontv.rpbase.data.ProfessionsEnum;
import ru.fotontv.rpbase.modules.config.ConfigManager;

import javax.annotation.Nonnull;

public class PassqCommands implements CommandExecutor {

    @Override
    public boolean onCommand(@Nonnull CommandSender sender, @Nonnull Command command, @Nonnull String label, @Nonnull String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            PlayerData data = PlayersManager.getPlayerData(player);
            if (data != null) {
                if (player.hasPermission("passport.request.true") ||
                        player.hasPermission("passport.request") ||
                        ProfessionsEnum.isAll(data)) {
                    if (command.getName().equals("passq")) {
                        if (args.length == 1) {
                            if (args[0].equals("true")) {
                                Player player1 = Bukkit.getPlayer(data.getNickPassportRequest());
                                PlayerData playerData = PlayersManager.getPlayerData(player1);
                                if (player1 != null && playerData != null) {
                                    PlayersManager.openPassOther(player, player1);
                                    playerData.setNickPassportRequest("");
                                    PlayersManager.savePlayerData(playerData);
                                    return true;
                                }
                                data.setNickPassportRequest("");
                                PlayersManager.savePlayerData(data);
                                return true;
                            }
                            if (args[0].equals("false")) {
                                data.setNickPassportRequest("");
                                PlayersManager.savePlayerData(data);
                                return true;
                            }
                            Player player1 = Bukkit.getPlayer(args[0]);
                            PlayerData playerData = PlayersManager.getPlayerData(player1);
                            if (player1 != null && playerData != null) {
                                playerData.setNickPassportRequest(player.getName());
                                PlayersManager.savePlayerData(playerData);
                                TextComponent yes = new TextComponent("✔");
                                TextComponent no = new TextComponent("✖");
                                yes.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "passq true"));
                                no.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "passq false"));
                                player1.sendMessage(ConfigManager.PLAYER_PASSPORT_REQUESTOPEN.replace("{player}", player.getName()).replace("%yes", yes.getText()).replace("%no", no.getText()));
                                player.sendMessage(ConfigManager.PLAYER_PASSPORT_REQUEST.replace("{player}", player1.getName()));
                            }
                            player.sendMessage(ConfigManager.PLAYER_NOTFOUND.replace("%s", args[0]));
                            return true;
                        }
                        return false;
                    }
                    return false;
                }
                player.sendMessage(ConfigManager.PLAYER_NOPERMISSION);
                return true;
            }
            player.sendMessage(ConfigManager.PLAYER_NOTFOUND.replace("%s", player.getName()));
            return true;
        }
        return false;
    }
}
