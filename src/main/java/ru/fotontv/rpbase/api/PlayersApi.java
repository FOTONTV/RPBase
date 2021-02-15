package ru.fotontv.rpbase.api;

import org.bukkit.entity.Player;
import ru.fotontv.rpbase.data.PlayerData;
import ru.fotontv.rpbase.data.PlayersManager;
import ru.fotontv.rpbase.data.ProfessionsEnum;

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
