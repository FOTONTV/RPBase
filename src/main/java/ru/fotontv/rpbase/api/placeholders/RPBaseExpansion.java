package ru.fotontv.rpbase.api.placeholders;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.entity.Player;
import ru.fotontv.rpbase.RPBase;
import ru.fotontv.rpbase.enums.ProfessionsEnum;
import ru.fotontv.rpbase.modules.player.PlayerData;
import ru.fotontv.rpbase.modules.player.PlayersManager;

import javax.annotation.Nonnull;

public class RPBaseExpansion extends PlaceholderExpansion {
    private final RPBase plugin;

    public RPBaseExpansion(RPBase plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean persist() {
        return true;
    }

    @Override
    public boolean canRegister() {
        return true;
    }

    @Override
    public @Nonnull
    String getIdentifier() {
        return "rpbase";
    }

    @Override
    public @Nonnull
    String getAuthor() {
        return plugin.getDescription().getAuthors().toString();
    }

    @Override
    public @Nonnull
    String getVersion() {
        return plugin.getDescription().getVersion();
    }

    @Override
    public String onPlaceholderRequest(Player player, @Nonnull String identifier) {
        if (player == null) {
            return "";
        }
        if (identifier.equals("prefix")) {
            PlayerData data = PlayersManager.getPlayerData(player);
            if (data != null) {
                if (data.getProfession().equals(ProfessionsEnum.PLAYER)) {
                    return "§7[§2Житель§7] §f";
                }
                return "§7[§c" + data.getProfession().getNameProf() + "§7] §f";
            }
        }
        if (identifier.equals("suffix")) {
            PlayerData data = PlayersManager.getPlayerData(player);
            return data != null ? data.getProfession().getNameProf() : "";
        }
        if (identifier.equals("prof")) {
            PlayerData data = PlayersManager.getPlayerData(player);
            return data != null ? data.getProfession().getNameProf() : "";
        }
        if (identifier.equals("town")) {
            PlayerData data = PlayersManager.getPlayerData(player);
            return data != null ? data.getCityName() : "";
        }

        return null;
    }
}
