package ru.fotontv.rpbase.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import ru.fotontv.rpbase.modules.player.PlayersManager;

public class LeaveListener implements Listener {

    @EventHandler
    public void onLeave(PlayerQuitEvent event) {
        PlayersManager.savePlayerData(event.getPlayer());
    }
}
