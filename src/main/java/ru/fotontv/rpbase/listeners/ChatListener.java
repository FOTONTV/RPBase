package ru.fotontv.rpbase.listeners;

import net.luckperms.api.model.user.User;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import ru.fotontv.rpbase.RPBase;
import ru.fotontv.rpbase.data.PlayerData;
import ru.fotontv.rpbase.data.PlayersManager;
import ru.fotontv.rpbase.data.ProfessionsEnum;

import java.util.concurrent.CompletableFuture;

public class ChatListener implements Listener {

    @EventHandler
    public void onChat(AsyncPlayerChatEvent event) {
        String message = event.getMessage();
        System.out.println("LOGGER " + message);
        if (!message.contains("(())")) {
            if (!message.contains("!(())")) {
                Player player = event.getPlayer();
                PlayerData playerData = PlayersManager.getPlayerData(player);
                if (playerData != null) {
                    if (playerData.isChatCity() && playerData.getCity() != null) {
                        CompletableFuture<User> futureUser = RPBase.api.getUserManager().loadUser(player.getUniqueId());
                        futureUser.thenAcceptAsync(user -> {
                            String prefix = "§7[" + playerData.getProfession().getNameProf() + "§7]";
                            if (playerData.getProfession().equals(ProfessionsEnum.PLAYER)) {
                                prefix = "§7[§2Житель§7] §f";
                            }
                            String suffix = user.getCachedData().getMetaData().getSuffix();
                            String newmessage;
                            if (suffix == null) {
                                newmessage = "§7Чат города §8§l| §r" + prefix + " " + player.getName() + " » §f" + message;
                            } else {
                                suffix = ChatColor.translateAlternateColorCodes('&', suffix);
                                newmessage = "§7Чат города §8§l| §r" + prefix + " " + player.getName() + " "+ suffix + " » §f" + message;
                            }
                            for (Player player1 : Bukkit.getOnlinePlayers()) {
                                PlayerData playerData1 = PlayersManager.getPlayerData(player1);
                                if (playerData1 != null) {
                                    if (playerData1.getCityName().equals(playerData.getCityName())) {
                                        playerData1.getPlayer().sendMessage(newmessage);
                                    }
                                }
                            }
                        });
                        event.setCancelled(true);
                    }
                }
            }
        }
    }
}
