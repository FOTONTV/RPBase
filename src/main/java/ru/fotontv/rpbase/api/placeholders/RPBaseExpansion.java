package ru.fotontv.rpbase.api.placeholders;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.OfflinePlayer;
import ru.fotontv.rpbase.data.PlayerData;
import ru.fotontv.rpbase.data.PlayersManager;
import ru.fotontv.rpbase.data.ProfessionsEnum;

import javax.annotation.Nonnull;

public class RPBaseExpansion extends PlaceholderExpansion {

    @Override
    public boolean canRegister(){
        return true;
    }

    @Override
    public @Nonnull String getIdentifier() {
        return "rpbase";
    }

    @Override
    public @Nonnull String getAuthor() {
        return "FOTONTV";
    }

    @Override
    public @Nonnull String getVersion() {
        return "1.0.0";
    }

    @Override
    public String onRequest(OfflinePlayer player, @Nonnull String identifier) {
        if (identifier.equals("prefix")) {
            PlayerData data = PlayersManager.loadPlayerData(player.getName());
            if (data != null) {
                if (data.getProfession().equals(ProfessionsEnum.PLAYER)) {
                    return "§7[§2Житель§7] §f";
                }
                return "§7[§c" + data.getProfession().getNameProf() + "§7] §f";
            }
        }
        if (identifier.equals("suffix")) {
            PlayerData data = PlayersManager.loadPlayerData(player.getName());
            return data != null ? data.getProfession().getNameProf() : "";
        }
        if (identifier.equals("prof")) {
            PlayerData data = PlayersManager.loadPlayerData(player.getName());
            return data != null ? data.getProfession().getNameProf() : "";
        }
        return null;
    }
}
