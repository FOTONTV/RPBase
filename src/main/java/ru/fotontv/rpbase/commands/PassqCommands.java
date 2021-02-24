package ru.fotontv.rpbase.commands;

import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import ru.fotontv.rpbase.config.GlobalConfig;
import ru.fotontv.rpbase.enums.ProfessionsEnum;
import ru.fotontv.rpbase.modules.player.PlayerData;
import ru.fotontv.rpbase.modules.player.PlayersManager;

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
                                    if (player.getLocation().distance(player1.getLocation()) > 7) {
                                        player.sendMessage("§cВы должны находиться рядом с игроком на расстоянии 7 блоков");
                                        return true;
                                    }
                                    PlayersManager.openPassOther(player, player1);
                                    playerData.setNickPassportRequest("");
                                    new Thread(() -> PlayersManager.savePlayerData(playerData)).start();
                                    return true;
                                }
                                data.setNickPassportRequest("");
                                new Thread(() -> PlayersManager.savePlayerData(data)).start();
                                return true;
                            }
                            if (args[0].equals("false")) {
                                data.setNickPassportRequest("");
                                new Thread(() -> PlayersManager.savePlayerData(data)).start();
                                return true;
                            }
                            Player player1 = Bukkit.getPlayer(args[0]);
                            PlayerData playerData = PlayersManager.getPlayerData(player1);
                            if (player1 != null && playerData != null) {
                                playerData.setNickPassportRequest(player.getName());
                                new Thread(() -> PlayersManager.savePlayerData(playerData)).start();
                                TextComponent yes = new TextComponent("✔");
                                TextComponent no = new TextComponent("✖");
                                yes.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "passq true"));
                                no.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "passq false"));
                                player1.sendMessage(GlobalConfig.PLAYER_PASSPORT_REQUESTOPEN.replace("{player}", player.getName()).replace("%yes", yes.getText()).replace("%no", no.getText()));
                                player.sendMessage(GlobalConfig.PLAYER_PASSPORT_REQUEST.replace("{player}", player1.getName()));
                            }
                            player.sendMessage(GlobalConfig.PLAYER_NOTFOUND.replace("%s", args[0]));
                            return true;
                        }
                        return false;
                    }
                    return false;
                }
                player.sendMessage(GlobalConfig.PLAYER_NOPERMISSION);
                return true;
            }
            player.sendMessage(GlobalConfig.PLAYER_NOTFOUND.replace("%s", player.getName()));
            return true;
        }
        return false;
    }
}
