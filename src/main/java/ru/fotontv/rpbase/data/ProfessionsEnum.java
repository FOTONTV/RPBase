package ru.fotontv.rpbase.data;

import ru.fotontv.rpbase.modules.config.ConfigManager;

public enum ProfessionsEnum {
    PLAYER("-", ConfigManager.PLAYER),
    JUDGE("Судья", ConfigManager.JUDGE),
    PASSPORTOFFICER("Паспортист", ConfigManager.PASSPORTOFFICER),
    MAYOR("Мэр", ConfigManager.MAYOR),
    POLICEMAN("Полицейский", ConfigManager.POLICEMAN),
    OFFICER("Офицер", ConfigManager.OFFICER),
    CARETAKER("Смотритель", ConfigManager.CARETAKER),
    DETECTIVE("Детектив", ConfigManager.DETECTIVE),
    INVENTOR("Изобретатель", ConfigManager.INVENTOR),
    BLACKSMITH("Кузнец", ConfigManager.BLACKSMITH),
    WIZARD("Чародей", ConfigManager.WIZARD),
    COOK("Повар", ConfigManager.COOK),
    BREWER("Пивовар", ConfigManager.BREWER),
    THIEF("Вор", ConfigManager.THIEF);

    private final String nameProf;
    private final String luckpermsGroup;

    ProfessionsEnum(String nameProff, String luckpermsGroup) {
        this.nameProf = nameProff;
        this.luckpermsGroup = luckpermsGroup;
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

    public String getLuckpermsGroup() {
        return luckpermsGroup;
    }

    public static ProfessionsEnum getProf(String nameProf) {
        for (ProfessionsEnum professionsEnum : values()) {
            if (professionsEnum.nameProf.equals(nameProf))
                return professionsEnum;
        }
        return ProfessionsEnum.PLAYER;
    }
}
