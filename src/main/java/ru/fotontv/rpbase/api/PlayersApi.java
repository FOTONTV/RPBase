package ru.fotontv.rpbase.api;

import org.bukkit.entity.Player;
import ru.fotontv.rpbase.enums.ProfessionsEnum;
import ru.fotontv.rpbase.modules.player.PlayerData;
import ru.fotontv.rpbase.modules.player.PlayersManager;

@SuppressWarnings("unused")
public class PlayersApi {

    public static void addLock(Player player) {
        PlayerData data = PlayersManager.getPlayerData(player);
        if (data != null) {
            data.addCountUnlock();
            if (data.getCountUnlock() == 50) {
                ProfessionsEnum professionsEnum = data.getProfession();
                data.setProfession(ProfessionsEnum.THIEF);
                data.passport.setProfession(ProfessionsEnum.THIEF.getNameProf());
            }
            PlayersManager.savePlayerData(data);
        }
    }

    public static boolean isTHIEF(Player player) {
        PlayerData data = PlayersManager.getPlayerData(player);
        if (data != null) {
            return data.getProfession().equals(ProfessionsEnum.THIEF);
        }
        return false;
    }
}
