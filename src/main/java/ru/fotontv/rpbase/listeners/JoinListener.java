package ru.fotontv.rpbase.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import ru.fotontv.rpbase.modules.jail.JailManager;
import ru.fotontv.rpbase.modules.player.PlayersManager;

public class JoinListener implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        PlayersManager.loadPlayerData(event.getPlayer());
        JailManager.isPlayerJailAndTP(event.getPlayer());
    }
}
