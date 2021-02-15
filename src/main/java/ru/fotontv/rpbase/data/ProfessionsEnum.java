package ru.fotontv.rpbase.data;

import ru.fotontv.rpbase.modules.config.ConfigManager;

import java.util.List;

public enum ProfessionsEnum {
    PLAYER("-", ConfigManager.PLAYER),
    JUDGE("§cСудья", ConfigManager.JUDGE),
    PASSPORTOFFICER("§cПаспортист", ConfigManager.PASSPORTOFFICER),
    MAYOR("§cМэр", ConfigManager.MAYOR),
    POLICEMAN("§cПолицейский", ConfigManager.POLICEMAN),
    OFFICER("§cОфицер", ConfigManager.OFFICER),
    CARETAKER("§cСмотритель", ConfigManager.CARETAKER),
    DETECTIVE("§cДетектив", ConfigManager.DETECTIVE),
    INVENTOR("§cИзобретатель", ConfigManager.INVENTOR),
    BLACKSMITH("§cКузнец", ConfigManager.BLACKSMITH),
    WIZARD("§cЧародей", ConfigManager.WIZARD),
    COOK("§cПовар", ConfigManager.COOK),
    BREWER("§cПивовар", ConfigManager.BREWER),
    THIEF("§cВор", ConfigManager.THIEF);

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

    public String getNameProf() {
        return nameProf;
    }

    public List<String> getPermissions() {
        return permissions;
    }

    public static ProfessionsEnum getProf(String nameProf) {
        for (ProfessionsEnum professionsEnum : values()) {
            if (professionsEnum.nameProf.equals(nameProf))
                return professionsEnum;
        }
        return ProfessionsEnum.PLAYER;
    }
}
