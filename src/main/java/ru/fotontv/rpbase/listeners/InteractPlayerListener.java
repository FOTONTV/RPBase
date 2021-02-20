package ru.fotontv.rpbase.listeners;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import ru.fotontv.rpbase.modules.player.PlayerData;
import ru.fotontv.rpbase.modules.player.PlayersManager;

public class InteractPlayerListener implements Listener {

    @EventHandler
    public void onInteractPlayer(PlayerInteractEvent event) {
        if (event.getAction().equals(Action.LEFT_CLICK_BLOCK)) {
            Player player = event.getPlayer();
            ItemStack stack = event.getItem();
            if (stack != null && stack.getType().equals(Material.FLINT_AND_STEEL)) {
                PlayerData data = PlayersManager.getPlayerData(player);
                if (data != null) {
                    if (data.getLevel() < 4) {
                        event.setCancelled(true);
                        return;
                    }
                }
            }
            if (stack != null && stack.getType().equals(Material.LAVA_BUCKET)) {
                PlayerData data = PlayersManager.getPlayerData(player);
                if (data != null) {
                    if (data.getLevel() < 4) {
                        event.setCancelled(true);
                        return;
                    }
                }
            }
        }
        if (event.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
            Player player = event.getPlayer();
            ItemStack stack = event.getItem();
            if (stack != null && stack.getType().equals(Material.FLINT_AND_STEEL)) {
                PlayerData data = PlayersManager.getPlayerData(player);
                if (data != null) {
                    if (data.getLevel() < 4) {
                        event.setCancelled(true);
                        return;
                    }
                }
            }
            if (stack != null && stack.getType().equals(Material.LAVA_BUCKET)) {
                PlayerData data = PlayersManager.getPlayerData(player);
                if (data != null) {
                    if (data.getLevel() < 4) {
                        event.setCancelled(true);
                        return;
                    }
                }
            }
        }
        if (event.getAction().equals(Action.LEFT_CLICK_AIR)) {
            Player player = event.getPlayer();
            ItemStack stack = event.getItem();
            if (stack != null && stack.getType().equals(Material.FLINT_AND_STEEL)) {
                PlayerData data = PlayersManager.getPlayerData(player);
                if (data != null) {
                    if (data.getLevel() < 4) {
                        event.setCancelled(true);
                        return;
                    }
                }
            }
            if (stack != null && stack.getType().equals(Material.LAVA_BUCKET)) {
                PlayerData data = PlayersManager.getPlayerData(player);
                if (data != null) {
                    if (data.getLevel() < 4) {
                        event.setCancelled(true);
                        return;
                    }
                }
            }
        }
        if (event.getAction().equals(Action.RIGHT_CLICK_AIR)) {
            Player player = event.getPlayer();
            ItemStack stack = event.getItem();
            if (stack != null && stack.getType().equals(Material.FLINT_AND_STEEL)) {
                PlayerData data = PlayersManager.getPlayerData(player);
                if (data != null) {
                    if (data.getLevel() < 4) {
                        event.setCancelled(true);
                        return;
                    }
                }
            }
            if (stack != null && stack.getType().equals(Material.LAVA_BUCKET)) {
                PlayerData data = PlayersManager.getPlayerData(player);
                if (data != null) {
                    if (data.getLevel() < 4) {
                        event.setCancelled(true);
                    }
                }
            }
        }
    }
}
