package ru.fotontv.rpbase.enums;

import ru.fotontv.rpbase.config.StatusCityConfig;

public enum CityStatusEnum {
    SETTLEMENT("Поселение",
            StatusCityConfig.SETTLEMENT_maxCitizens,
            StatusCityConfig.SETTLEMENT_goldOre,
            StatusCityConfig.SETTLEMENT_JUDGE,
            StatusCityConfig.SETTLEMENT_PASSPORTOFFICER,
            StatusCityConfig.SETTLEMENT_POLICEMAN,
            StatusCityConfig.SETTLEMENT_OFFICER,
            StatusCityConfig.SETTLEMENT_CARETAKER,
            StatusCityConfig.SETTLEMENT_DETECTIVE,
            StatusCityConfig.SETTLEMENT_INVENTOR,
            StatusCityConfig.SETTLEMENT_BLACKSMITH,
            StatusCityConfig.SETTLEMENT_WIZARD,
            StatusCityConfig.SETTLEMENT_COOK,
            StatusCityConfig.SETTLEMENT_BREWER),
    PROVINCE("Провинция",
            StatusCityConfig.PROVINCE_maxCitizens,
            StatusCityConfig.PROVINCE_goldOre,
            StatusCityConfig.PROVINCE_JUDGE,
            StatusCityConfig.PROVINCE_PASSPORTOFFICER,
            StatusCityConfig.PROVINCE_POLICEMAN,
            StatusCityConfig.PROVINCE_OFFICER,
            StatusCityConfig.PROVINCE_CARETAKER,
            StatusCityConfig.PROVINCE_DETECTIVE,
            StatusCityConfig.PROVINCE_INVENTOR,
            StatusCityConfig.PROVINCE_BLACKSMITH,
            StatusCityConfig.PROVINCE_WIZARD,
            StatusCityConfig.PROVINCE_COOK,
            StatusCityConfig.PROVINCE_BREWER),
    CITY("Город",
            StatusCityConfig.CITY_maxCitizens,
            StatusCityConfig.CITY_goldOre,
            StatusCityConfig.CITY_JUDGE,
            StatusCityConfig.CITY_PASSPORTOFFICER,
            StatusCityConfig.CITY_POLICEMAN,
            StatusCityConfig.CITY_OFFICER,
            StatusCityConfig.CITY_CARETAKER,
            StatusCityConfig.CITY_DETECTIVE,
            StatusCityConfig.CITY_INVENTOR,
            StatusCityConfig.CITY_BLACKSMITH,
            StatusCityConfig.CITY_WIZARD,
            StatusCityConfig.CITY_COOK,
            StatusCityConfig.CITY_BREWER),
    METROPOLIS("Мегаполис",
            StatusCityConfig.METROPOLIS_maxCitizens,
            StatusCityConfig.METROPOLIS_goldOre,
            StatusCityConfig.METROPOLIS_JUDGE,
            StatusCityConfig.METROPOLIS_PASSPORTOFFICER,
            StatusCityConfig.METROPOLIS_POLICEMAN,
            StatusCityConfig.METROPOLIS_OFFICER,
            StatusCityConfig.METROPOLIS_CARETAKER,
            StatusCityConfig.METROPOLIS_DETECTIVE,
            StatusCityConfig.METROPOLIS_INVENTOR,
            StatusCityConfig.METROPOLIS_BLACKSMITH,
            StatusCityConfig.METROPOLIS_WIZARD,
            StatusCityConfig.METROPOLIS_COOK,
            StatusCityConfig.METROPOLIS_BREWER);

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

    public static CityStatusEnum getNameToValue(String nameStatus) {
        for (CityStatusEnum cityStatusEnum : values()) {
            if (cityStatusEnum.statusName.equals(nameStatus))
                return cityStatusEnum;
        }
        return CityStatusEnum.SETTLEMENT;
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
}
