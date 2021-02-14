package ru.fotontv.rpbase.listeners;

import com.destroystokyo.paper.event.player.PlayerArmorChangeEvent;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class ArmorChangeListener implements Listener {

    @EventHandler
    public void onChangeArmor(PlayerArmorChangeEvent event) {
        Player player = event.getPlayer();
        if (player.hasPermission("rpbase.talentVoin")) {
            ItemStack helmet = player.getInventory().getHelmet();
            ItemStack chestplate = player.getInventory().getChestplate();
            ItemStack leggins = player.getInventory().getLeggings();
            ItemStack boots = player.getInventory().getBoots();
            if (helmet != null && chestplate != null && leggins != null && boots != null) {
                if (helmet.getType().equals(Material.DIAMOND_HELMET) &&
                        chestplate.getType().equals(Material.DIAMOND_CHESTPLATE) &&
                        leggins.getType().equals(Material.DIAMOND_LEGGINGS) &&
                        boots.getType().equals(Material.DIAMOND_BOOTS)) {
                    player.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, Integer.MAX_VALUE, 0));
                    player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, Integer.MAX_VALUE, 0));
                }
            } else {
                if (player.hasPotionEffect(PotionEffectType.INCREASE_DAMAGE) && player.hasPotionEffect(PotionEffectType.SLOW)) {
                    player.removePotionEffect(PotionEffectType.INCREASE_DAMAGE);
                    player.removePotionEffect(PotionEffectType.SLOW);
                }
            }
        }
    }
}
