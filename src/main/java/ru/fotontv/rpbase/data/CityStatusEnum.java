package ru.fotontv.rpbase.data;

import ru.fotontv.rpbase.modules.config.StatusCityManager;

public enum CityStatusEnum {
    SETTLEMENT("Поселение",
            StatusCityManager.SETTLEMENT_maxCitizens,
            StatusCityManager.SETTLEMENT_goldOre,
            StatusCityManager.SETTLEMENT_JUDGE,
            StatusCityManager.SETTLEMENT_PASSPORTOFFICER,
            StatusCityManager.SETTLEMENT_POLICEMAN,
            StatusCityManager.SETTLEMENT_OFFICER,
            StatusCityManager.SETTLEMENT_CARETAKER,
            StatusCityManager.SETTLEMENT_DETECTIVE,
            StatusCityManager.SETTLEMENT_INVENTOR,
            StatusCityManager.SETTLEMENT_BLACKSMITH,
            StatusCityManager.SETTLEMENT_WIZARD,
            StatusCityManager.SETTLEMENT_COOK,
            StatusCityManager.SETTLEMENT_BREWER),
    PROVINCE("Провинция",
            StatusCityManager.PROVINCE_maxCitizens,
            StatusCityManager.PROVINCE_goldOre,
            StatusCityManager.PROVINCE_JUDGE,
            StatusCityManager.PROVINCE_PASSPORTOFFICER,
            StatusCityManager.PROVINCE_POLICEMAN,
            StatusCityManager.PROVINCE_OFFICER,
            StatusCityManager.PROVINCE_CARETAKER,
            StatusCityManager.PROVINCE_DETECTIVE,
            StatusCityManager.PROVINCE_INVENTOR,
            StatusCityManager.PROVINCE_BLACKSMITH,
            StatusCityManager.PROVINCE_WIZARD,
            StatusCityManager.PROVINCE_COOK,
            StatusCityManager.PROVINCE_BREWER),
    CITY("Город",
            StatusCityManager.CITY_maxCitizens,
            StatusCityManager.CITY_goldOre,
            StatusCityManager.CITY_JUDGE,
            StatusCityManager.CITY_PASSPORTOFFICER,
            StatusCityManager.CITY_POLICEMAN,
            StatusCityManager.CITY_OFFICER,
            StatusCityManager.CITY_CARETAKER,
            StatusCityManager.CITY_DETECTIVE,
            StatusCityManager.CITY_INVENTOR,
            StatusCityManager.CITY_BLACKSMITH,
            StatusCityManager.CITY_WIZARD,
            StatusCityManager.CITY_COOK,
            StatusCityManager.CITY_BREWER),
    METROPOLIS("Мегаполис",
            StatusCityManager.METROPOLIS_maxCitizens,
            StatusCityManager.METROPOLIS_goldOre,
            StatusCityManager.METROPOLIS_JUDGE,
            StatusCityManager.METROPOLIS_PASSPORTOFFICER,
            StatusCityManager.METROPOLIS_POLICEMAN,
            StatusCityManager.METROPOLIS_OFFICER,
            StatusCityManager.METROPOLIS_CARETAKER,
            StatusCityManager.METROPOLIS_DETECTIVE,
            StatusCityManager.METROPOLIS_INVENTOR,
            StatusCityManager.METROPOLIS_BLACKSMITH,
            StatusCityManager.METROPOLIS_WIZARD,
            StatusCityManager.METROPOLIS_COOK,
            StatusCityManager.METROPOLIS_BREWER);

    private final String statusName;
    private final Integer maxCitizens;
    private final Integer goldOre;
    private final Integer JUDGE;
    private final Integer PASSPORTOFFICER;
    private final Integer POLICEMAN;
    private final Integer OFFICER;
    private final Integer CARETAKER;
    private final Integer DETECTIVE;
    private final Integer INVENTOR;
    private final Integer BLACKSMITH;
    private final Integer WIZARD;
    private final Integer COOK;
    private final Integer BREWER;

    CityStatusEnum(String statusName,
                   Integer maxCitizens,
                   Integer goldOre,
                   Integer JUDGE,
                   Integer PASSPORTOFFICER,
                   Integer POLICEMAN,
                   Integer OFFICER,
                   Integer CARETAKER,
                   Integer DETECTIVE,
                   Integer INVENTOR,
                   Integer BLACKSMITH,
                   Integer WIZARD,
                   Integer COOK,
                   Integer BREWER) {
        this.statusName = statusName;
        this.maxCitizens = maxCitizens;
        this.goldOre = goldOre;
        this.JUDGE = JUDGE;
        this.PASSPORTOFFICER = PASSPORTOFFICER;
        this.POLICEMAN = POLICEMAN;
        this.OFFICER = OFFICER;
        this.CARETAKER = CARETAKER;
        this.DETECTIVE = DETECTIVE;
        this.INVENTOR = INVENTOR;
        this.BLACKSMITH = BLACKSMITH;
        this.WIZARD = WIZARD;
        this.COOK = COOK;
        this.BREWER = BREWER;
    }


    public String getStatusName() {
        return statusName;
    }

    public Integer getMaxCitizens() {
        return maxCitizens;
    }

    public Integer getGoldOre() {
        return goldOre;
    }

    public Integer getJUDGE() {
        return JUDGE;
    }

    public Integer getPASSPORTOFFICER() {
        return PASSPORTOFFICER;
    }

    public Integer getPOLICEMAN() {
        return POLICEMAN;
    }

    public Integer getOFFICER() {
        return OFFICER;
    }

    public Integer getCARETAKER() {
        return CARETAKER;
    }

    public Integer getDETECTIVE() {
        return DETECTIVE;
    }

    public Integer getINVENTOR() {
        return INVENTOR;
    }

    public Integer getBLACKSMITH() {
        return BLACKSMITH;
    }

    public Integer getWIZARD() {
        return WIZARD;
    }

    public Integer getCOOK() {
        return COOK;
    }

    public Integer getBREWER() {
        return BREWER;
    }

    public static CityStatusEnum getNameToValue(String nameStatus) {
        for (CityStatusEnum cityStatusEnum : values()) {
            if (cityStatusEnum.statusName.equals(nameStatus))
                return cityStatusEnum;
        }
        return CityStatusEnum.SETTLEMENT;
    }
}
