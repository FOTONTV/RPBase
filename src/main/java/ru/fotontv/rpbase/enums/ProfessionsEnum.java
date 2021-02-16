package ru.fotontv.rpbase.enums;

import ru.fotontv.rpbase.config.GlobalConfig;
import ru.fotontv.rpbase.modules.player.PlayerData;

import java.util.List;

public enum ProfessionsEnum {
    PLAYER("-", GlobalConfig.PLAYER),
    JUDGE("Судья", GlobalConfig.JUDGE),
    PASSPORTOFFICER("Паспортист", GlobalConfig.PASSPORTOFFICER),
    MAYOR("Мэр", GlobalConfig.MAYOR),
    POLICEMAN("Полицейский", GlobalConfig.POLICEMAN),
    OFFICER("Офицер", GlobalConfig.OFFICER),
    CARETAKER("Смотритель", GlobalConfig.CARETAKER),
    DETECTIVE("Детектив", GlobalConfig.DETECTIVE),
    INVENTOR("Изобретатель", GlobalConfig.INVENTOR),
    BLACKSMITH("Кузнец", GlobalConfig.BLACKSMITH),
    WIZARD("Чародей", GlobalConfig.WIZARD),
    COOK("Повар", GlobalConfig.COOK),
    BREWER("Пивовар", GlobalConfig.BREWER),
    THIEF("Вор", GlobalConfig.THIEF);

    private final String nameProf;
    private final List<String> permissions;

    ProfessionsEnum(String nameProff, List<String> permissions) {
        this.nameProf = nameProff;
        this.permissions = permissions;
    }

    public static boolean isAll(PlayerData data) {
        return data.getProfession().equals(PLAYER) ||
                data.getProfession().equals(JUDGE) ||
                data.getProfession().equals(PASSPORTOFFICER) ||
                data.getProfession().equals(MAYOR) ||
                data.getProfession().equals(POLICEMAN) ||
                data.getProfession().equals(OFFICER) ||
                data.getProfession().equals(DETECTIVE) ||
                data.getProfession().equals(INVENTOR) ||
                data.getProfession().equals(BLACKSMITH) ||
                data.getProfession().equals(WIZARD) ||
                data.getProfession().equals(COOK) ||
                data.getProfession().equals(BREWER) ||
                data.getProfession().equals(THIEF);
    }

    public static ProfessionsEnum getProf(String nameProf) {
        for (ProfessionsEnum professionsEnum : values()) {
            if (professionsEnum.nameProf.equals(nameProf))
                return professionsEnum;
        }
        return ProfessionsEnum.PLAYER;
    }

    public String getNameProf() {
        return nameProf;
    }

    public List<String> getPermissions() {
        return permissions;
    }
}
