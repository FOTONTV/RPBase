package ru.fotontv.rpbase.listeners;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;

public class CitizensListener implements Listener {

    @EventHandler
    public void onRightClickEntity(PlayerInteractEntityEvent e) {
        Entity entity = e.getRightClicked();
        Player player = e.getPlayer();
        if (entity instanceof Villager) {
            if (!player.hasPermission("rpbase.talentBigNos"))
                e.setCancelled(true);
        }
    }
}
