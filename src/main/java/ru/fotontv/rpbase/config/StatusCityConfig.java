package ru.fotontv.rpbase.config;

import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import ru.fotontv.rpbase.RPBase;

import java.io.File;
import java.io.IOException;

public class StatusCityConfig {
    public static Integer SETTLEMENT_maxCitizens = 6;
    public static Integer SETTLEMENT_goldOre = 0;
    public static Integer SETTLEMENT_JUDGE = 1;
    public static Integer SETTLEMENT_PASSPORTOFFICER = 1;
    public static Integer SETTLEMENT_POLICEMAN = 1;
    public static Integer SETTLEMENT_OFFICER = 1;
    public static Integer SETTLEMENT_CARETAKER = 1;
    public static Integer SETTLEMENT_DETECTIVE = 1;
    public static Integer SETTLEMENT_INVENTOR = 1;
    public static Integer SETTLEMENT_BLACKSMITH = 1;
    public static Integer SETTLEMENT_WIZARD = 1;
    public static Integer SETTLEMENT_COOK = 1;
    public static Integer SETTLEMENT_BREWER = 1;
    public static Integer PROVINCE_maxCitizens = 18;
    public static Integer PROVINCE_goldOre = 640;
    public static Integer PROVINCE_JUDGE = 1;
    public static Integer PROVINCE_PASSPORTOFFICER = 1;
    public static Integer PROVINCE_POLICEMAN = 1;
    public static Integer PROVINCE_OFFICER = 1;
    public static Integer PROVINCE_CARETAKER = 1;
    public static Integer PROVINCE_DETECTIVE = 1;
    public static Integer PROVINCE_INVENTOR = 1;
    public static Integer PROVINCE_BLACKSMITH = 1;
    public static Integer PROVINCE_WIZARD = 1;
    public static Integer PROVINCE_COOK = 1;
    public static Integer PROVINCE_BREWER = 1;
    public static Integer CITY_maxCitizens = 36;
    public static Integer CITY_goldOre = 900;
    public static Integer CITY_JUDGE = 1;
    public static Integer CITY_PASSPORTOFFICER = 1;
    public static Integer CITY_POLICEMAN = 1;
    public static Integer CITY_OFFICER = 1;
    public static Integer CITY_CARETAKER = 1;
    public static Integer CITY_DETECTIVE = 1;
    public static Integer CITY_INVENTOR = 1;
    public static Integer CITY_BLACKSMITH = 1;
    public static Integer CITY_WIZARD = 1;
    public static Integer CITY_COOK = 1;
    public static Integer CITY_BREWER = 1;
    public static Integer METROPOLIS_maxCitizens = 108;
    public static Integer METROPOLIS_goldOre = 1500;
    public static Integer METROPOLIS_JUDGE = 1;
    public static Integer METROPOLIS_PASSPORTOFFICER = 1;
    public static Integer METROPOLIS_POLICEMAN = 1;
    public static Integer METROPOLIS_OFFICER = 1;
    public static Integer METROPOLIS_CARETAKER = 1;
    public static Integer METROPOLIS_DETECTIVE = 1;
    public static Integer METROPOLIS_INVENTOR = 1;
    public static Integer METROPOLIS_BLACKSMITH = 1;
    public static Integer METROPOLIS_WIZARD = 1;
    public static Integer METROPOLIS_COOK = 1;
    public static Integer METROPOLIS_BREWER = 1;
    private final RPBase plugin;
    private FileConfiguration cityStatusConfig;

    public StatusCityConfig(RPBase plugin) {
        this.plugin = plugin;
        loadFile();
    }

    private void loadFile() {
        File cityFile = new File(plugin.getDataFolder(), "statusCityConfig.yml");
        if (!(cityFile.exists())) {
            if (!(cityFile.getParentFile().mkdirs())) System.out.println();
            plugin.saveResource("statusCityConfig.yml", false);
            cityStatusConfig = new YamlConfiguration();
            try {
                cityStatusConfig.load(cityFile);
                loadStatusCities();
            } catch (InvalidConfigurationException | IOException e) {
                e.printStackTrace();
            }
            return;
        }
        cityStatusConfig = new YamlConfiguration();
        try {
            cityStatusConfig.load(cityFile);
            loadStatusCities();
        } catch (InvalidConfigurationException | IOException e) {
            e.printStackTrace();
        }
    }

    private void loadStatusCities() {
        SETTLEMENT_maxCitizens = cityStatusConfig.getInt("SETTLEMENT.maxCitizens");
        SETTLEMENT_goldOre = cityStatusConfig.getInt("SETTLEMENT.goldOre");
        SETTLEMENT_JUDGE = cityStatusConfig.getInt("SETTLEMENT.JUDGE");
        SETTLEMENT_PASSPORTOFFICER = cityStatusConfig.getInt("SETTLEMENT.PASSPORTOFFICER");
        SETTLEMENT_POLICEMAN = cityStatusConfig.getInt("SETTLEMENT.POLICEMAN");
        SETTLEMENT_OFFICER = cityStatusConfig.getInt("SETTLEMENT.OFFICER");
        SETTLEMENT_CARETAKER = cityStatusConfig.getInt("SETTLEMENT.CARETAKER");
        SETTLEMENT_DETECTIVE = cityStatusConfig.getInt("SETTLEMENT.DETECTIVE");
        SETTLEMENT_INVENTOR = cityStatusConfig.getInt("SETTLEMENT.INVENTOR");
        SETTLEMENT_BLACKSMITH = cityStatusConfig.getInt("SETTLEMENT.BLACKSMITH");
        SETTLEMENT_WIZARD = cityStatusConfig.getInt("SETTLEMENT.WIZARD");
        SETTLEMENT_COOK = cityStatusConfig.getInt("SETTLEMENT.COOK");
        SETTLEMENT_BREWER = cityStatusConfig.getInt("SETTLEMENT.BREWER");

        PROVINCE_maxCitizens = cityStatusConfig.getInt("PROVINCE.maxCitizens");
        PROVINCE_goldOre = cityStatusConfig.getInt("PROVINCE.goldOre");
        PROVINCE_JUDGE = cityStatusConfig.getInt("PROVINCE.JUDGE");
        PROVINCE_PASSPORTOFFICER = cityStatusConfig.getInt("PROVINCE.PASSPORTOFFICER");
        PROVINCE_POLICEMAN = cityStatusConfig.getInt("PROVINCE.POLICEMAN");
        PROVINCE_OFFICER = cityStatusConfig.getInt("PROVINCE.OFFICER");
        PROVINCE_CARETAKER = cityStatusConfig.getInt("PROVINCE.CARETAKER");
        PROVINCE_DETECTIVE = cityStatusConfig.getInt("PROVINCE.DETECTIVE");
        PROVINCE_INVENTOR = cityStatusConfig.getInt("PROVINCE.INVENTOR");
        PROVINCE_BLACKSMITH = cityStatusConfig.getInt("PROVINCE.BLACKSMITH");
        PROVINCE_WIZARD = cityStatusConfig.getInt("PROVINCE.WIZARD");
        PROVINCE_COOK = cityStatusConfig.getInt("PROVINCE.COOK");
        PROVINCE_BREWER = cityStatusConfig.getInt("PROVINCE.BREWER");

        CITY_maxCitizens = cityStatusConfig.getInt("CITY.maxCitizens");
        CITY_goldOre = cityStatusConfig.getInt("CITY.goldOre");
        CITY_JUDGE = cityStatusConfig.getInt("CITY.JUDGE");
        CITY_PASSPORTOFFICER = cityStatusConfig.getInt("CITY.PASSPORTOFFICER");
        CITY_POLICEMAN = cityStatusConfig.getInt("CITY.POLICEMAN");
        CITY_OFFICER = cityStatusConfig.getInt("CITY.OFFICER");
        CITY_CARETAKER = cityStatusConfig.getInt("CITY.CARETAKER");
        CITY_DETECTIVE = cityStatusConfig.getInt("CITY.DETECTIVE");
        CITY_INVENTOR = cityStatusConfig.getInt("CITY.INVENTOR");
        CITY_BLACKSMITH = cityStatusConfig.getInt("CITY.BLACKSMITH");
        CITY_WIZARD = cityStatusConfig.getInt("CITY.WIZARD");
        CITY_COOK = cityStatusConfig.getInt("CITY.COOK");
        CITY_BREWER = cityStatusConfig.getInt("CITY.BREWER");

        METROPOLIS_maxCitizens = cityStatusConfig.getInt("METROPOLIS.maxCitizens");
        METROPOLIS_goldOre = cityStatusConfig.getInt("METROPOLIS.goldOre");
        METROPOLIS_JUDGE = cityStatusConfig.getInt("METROPOLIS.JUDGE");
        METROPOLIS_PASSPORTOFFICER = cityStatusConfig.getInt("METROPOLIS.PASSPORTOFFICER");
        METROPOLIS_POLICEMAN = cityStatusConfig.getInt("METROPOLIS.POLICEMAN");
        METROPOLIS_OFFICER = cityStatusConfig.getInt("METROPOLIS.OFFICER");
        METROPOLIS_CARETAKER = cityStatusConfig.getInt("METROPOLIS.CARETAKER");
        METROPOLIS_DETECTIVE = cityStatusConfig.getInt("METROPOLIS.DETECTIVE");
        METROPOLIS_INVENTOR = cityStatusConfig.getInt("METROPOLIS.INVENTOR");
        METROPOLIS_BLACKSMITH = cityStatusConfig.getInt("METROPOLIS.BLACKSMITH");
        METROPOLIS_WIZARD = cityStatusConfig.getInt("METROPOLIS.WIZARD");
        METROPOLIS_COOK = cityStatusConfig.getInt("METROPOLIS.COOK");
        METROPOLIS_BREWER = cityStatusConfig.getInt("METROPOLIS.BREWER");
    }
}
