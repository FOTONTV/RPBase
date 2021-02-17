package ru.fotontv.rpbase.listeners;

import com.earth2me.essentials.Essentials;
import com.earth2me.essentials.User;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import ru.fotontv.rpbase.RPBase;
import ru.fotontv.rpbase.config.ChatConfig;

import javax.annotation.Nonnull;

public class RPListener implements Listener {
    private final RPBase plugin;

    public RPListener(@Nonnull RPBase plugin) {
        this.plugin = plugin;
    }

    @EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
    public void on(AsyncPlayerPreLoginEvent event) {
        if (event.getLoginResult() == AsyncPlayerPreLoginEvent.Result.ALLOWED && !this.plugin.isWhitelisted(event.getName()))
            event.disallow(AsyncPlayerPreLoginEvent.Result.KICK_WHITELIST, ChatConfig.msgNotWhitelisted);
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.MONITOR)
    private void on(AsyncPlayerChatEvent event) {
        boolean global = event.getFormat().contains(ChatConfig.globalChatFormatDetector);
        String message = event.getMessage().trim();
        boolean isess = (this.plugin.getServer().getPluginManager().getPlugin("Essentials") != null);
        if (isess) {
            Essentials ess = (Essentials)Essentials.getProvidingPlugin(Essentials.class);
            User user = ess.getUser(event.getPlayer());
            if (message.isEmpty()) {
                event.setCancelled(true);
            } else if (message.startsWith("(())") && !user.isMuted()) {
                this.plugin.sendNoRpMessage(event.getPlayer(), global, message.substring(4).trim());
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    private void on(EntityExplodeEvent event) {
        if (event.getEntityType() == EntityType.CREEPER)
            event.blockList().clear();
    }

    @EventHandler(ignoreCancelled = true)
    private void on(PlayerInteractEvent event) {
        Player player;
        if (event.getAction() == Action.RIGHT_CLICK_BLOCK && event.getClickedBlock() != null && event.getClickedBlock().getType() == Material.ENCHANTING_TABLE && !(player = event.getPlayer()).isOp() && !player.hasPermission("rp.open_enchanting_table")) {
            event.setCancelled(true);
            player.sendMessage(ChatColor.RED + "Вы должны быть Чародеем или Изобретателем, чтобы использовать чародейский стол");
        }
    }
}
