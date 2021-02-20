package ru.fotontv.rpbase.listeners;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import ru.fotontv.rpbase.enums.ProfessionsEnum;
import ru.fotontv.rpbase.modules.player.PlayerData;
import ru.fotontv.rpbase.modules.player.PlayersManager;

public class CitizensListener implements Listener {

    @EventHandler
    public void onRightClickEntity(PlayerInteractEntityEvent e) {
        Entity entity = e.getRightClicked();
        Player player = e.getPlayer();
        PlayerData data = PlayersManager.getPlayerData(player);
        if (entity instanceof Villager) {
            if (!player.hasPermission("rpbase.talentBigNos") || (data != null && !data.getProfession().equals(ProfessionsEnum.TRADER)))
                e.setCancelled(true);
        }
    }
}
