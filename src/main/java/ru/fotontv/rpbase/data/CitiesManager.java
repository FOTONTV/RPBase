package ru.fotontv.rpbase.data;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import ru.fotontv.rpbase.RPBase;
import ru.fotontv.rpbase.modules.config.ConfigManager;
import ru.fotontv.rpbase.utils.LinkedSet;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class CitiesManager implements Listener {

    private final RPBase plugin;
    private static File cityFile;
    private static FileConfiguration cityConfig;
    private static final List<Inventory> pagesListCities = new ArrayList<>();

    private static Set<City> cityList = new LinkedSet<>();

    public CitiesManager(RPBase base) {
        this.plugin = base;
    }

    public void load() {
        cityFile = new File(plugin.getDataFolder(), "cities.yml");
        if (!(cityFile.exists())) {
            if (!(cityFile.getParentFile().mkdirs())) System.out.println();
            plugin.saveResource("cities.yml", false);
            cityConfig = new YamlConfiguration();
            try {
                cityConfig.load(cityFile);
                loadCities();
            } catch (InvalidConfigurationException | IOException e) {
                e.printStackTrace();
            }
            return;
        }
        cityConfig = new YamlConfiguration();
        try {
            cityConfig.load(cityFile);
            loadCities();
        } catch (InvalidConfigurationException | IOException e) {
            e.printStackTrace();
        }
    }

    private static void loadCities() {
        Set<City> cities = new LinkedSet<>();
        if (cityConfig.contains("cities")) {
            List<String> cityList = cityConfig.getStringList("cities");
            for (String cityName : cityList) {
                if (cityConfig.contains(cityName) && cityConfig.contains(cityName + ".mayor")) {
                    City city = new City(cityName, cityConfig.getString(cityName + ".mayor"));
                    if (cityConfig.contains(cityName + ".citizens")) {
                        List<String> citizens = new ArrayList<>(cityConfig.getStringList(cityName + ".citizens"));
                        city.setCitizen(citizens);
                    }
                    if (cityConfig.contains(cityName + ".dateOfFoundation"))
                        city.setDateOfFoundation(cityConfig.getString(cityName + ".dateOfFoundation"));
                    if (cityConfig.contains(cityName + ".cityStatus")) {
                        CityStatusEnum cityStatusEnum = CityStatusEnum.valueOf(cityConfig.getString(cityName + ".cityStatus"));
                        city.setCityStatus(cityStatusEnum);
                    }
                    cities.add(city);
                }
            }
        }
        cityList = cities;
    }

    public static void saveCities() {
        cityConfig = new YamlConfiguration();
        List<String> cities = new ArrayList<>();
        for (City city : cityList) {
            cityConfig.createSection(city.getNameCity());
            cities.add(city.getNameCity());
            cityConfig.set(city.getNameCity() + ".mayor", city.getMayor());
            cityConfig.set(city.getNameCity() + ".citizens", city.getCitizen());
            cityConfig.set(city.getNameCity() + ".dateOfFoundation", city.getDateOfFoundation());
            cityConfig.set(city.getNameCity() + ".cityStatus", city.getCityStatus().name());
        }
        cityConfig.set("cities", cities);
        try {
            cityConfig.save(cityFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void addCity(City city) {
        cityList.add(city);
        Player player = Bukkit.getPlayer(city.getMayor());
        if (player != null) {
            PlayerData data = PlayersManager.getPlayerData(player);
            if (data != null) {
                data.setDateInput(city.getDateOfFoundation());
                PlayersManager.savePlayerData(data);
            }
        }
    }

    public static void disbandCity(City city) {
        cityList.remove(city);
    }

    public static Set<City> getCityList() {
        return cityList;
    }

    public static City getCity(String name) {
        for (City city : cityList) {
            if (city.getNameCity().equals(name))
                return city;
        }
        return null;
    }

    public static boolean isCity(String name) {
        for (City city : cityList) {
            if (city.getNameCity().equals(name))
                return true;
        }
        return false;
    }

    public static void openCityGUI(City city, Player player) {
        String infoCityGUI = ConfigManager.CITYINFOGUINAME.replace("{city}", city.getNameCity());
        Inventory info = Bukkit.getServer().createInventory(player, 9, infoCityGUI);
        PlayerData data = PlayersManager.getPlayerData(player);
        if (data != null)
            info.setItem(4, data.getCityInfoItem());
        player.openInventory(info);
    }

    public static void openCityList(Player player) {
        Set<City> allCities = getCityList();
        if (allCities.size() > 8) {
            Set<City> pageListCities = new LinkedSet<>();
            for (City city : allCities) {
                pageListCities.add(city);
                if (pageListCities.size() == 8) {
                    pagesListCities.add(guiCityList(player, pageListCities, true));
                    pageListCities.clear();
                }
            }
        }
        player.openInventory(guiCityList(player, allCities, false));
    }

    @SuppressWarnings("deprecation")
    public static Inventory guiCityList(Player player, Set<City> cities, boolean isPages) {
        String listCityGUI = "§8Список городов";
        Inventory info = Bukkit.getServer().createInventory(player, 45, centerTitle(listCityGUI));
        if (isPages) {
            ItemStack leftHad = new ItemStack(Material.PLAYER_HEAD);
            SkullMeta meta = (SkullMeta)leftHad.getItemMeta();
            meta.setDisplayName("§7Назад");
            meta.setOwner("MHF_ArrowLeft");
            leftHad.setItemMeta(meta);
            info.setItem(36, leftHad);

            ItemStack rightHad = new ItemStack(Material.PLAYER_HEAD);
            SkullMeta meta1 = (SkullMeta)rightHad.getItemMeta();
            meta1.setDisplayName("§7Вперед");
            meta1.setOwner("MHF_ArrowRight");
            rightHad.setItemMeta(meta1);
            info.setItem(44, rightHad);
        }
        int i = 10;
        for (City city : cities) {
            ItemStack itemStack = new ItemStack(Material.RED_BANNER);
            ItemMeta meta = itemStack.getItemMeta();
            if (meta != null) {
                meta.setDisplayName("§c" + city.getNameCity());
                List<String> lore = new ArrayList<>();
                lore.add("§fДата основания §b§l» §f" + city.getDateOfFoundation());
                lore.add("§fМэр §b§l» §f" + city.getMayor());
                lore.add("§fСтатус §b§l» §f" + city.getCityStatus().getStatusName());
                lore.add("§fЖителей §b§l» §f" + city.getCitizen().size());
                meta.setLore(lore);
                itemStack.setItemMeta(meta);
            }
            info.setItem(i, itemStack);
            i += 2;
        }
        return info;
    }

    private static String centerTitle(String title) {
        StringBuilder result = new StringBuilder();
        int spaces = (24 - ChatColor.stripColor(title).length());

        for (int i = 0; i < spaces; i++) {
            result.append(" ");
        }

        return result.append(title).toString();
    }

    public static void openCityCitizensGUI(City city, Player player) {
        PlayerData data = PlayersManager.getPlayerData(player);
        if (data != null) {
            String citizensCityGUI = ConfigManager.CITIZENSCITYGUINAME;
            Inventory info = Bukkit.getServer().createInventory(player, Math.max(data.getCity().getCitizen().size(), 9), citizensCityGUI);
            info.setContents(city.getSkullCitizens());
            player.openInventory(info);
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void pickupItemInventory(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        PlayerData data = PlayersManager.getPlayerData(player);
        if (data != null && data.getCity() != null){
            if (player.getGameMode() == GameMode.CREATIVE) {
                event.setCancelled(false);
                return;
            }
            Inventory inv = event.getClickedInventory();
            ItemStack itemStack = event.getCurrentItem();
            if (inv != null) {
                if (itemStack != null && itemStack.getType() == Material.PLAYER_HEAD) {
                    event.setCancelled(true);
                    return;
                }
                if (inv.contains(data.getCityInfoItem())) {
                    openCityCitizensGUI(data.getCity(), player);
                    event.setCancelled(true);
                    return;
                }
                if (event.getView().getTitle().equals(centerTitle("§8Список городов"))){
                    if (itemStack != null) {
                        ItemMeta meta = itemStack.getItemMeta();
                        int page = 0;
                        if (meta.getDisplayName().equals("§7Вперед")) {
                            if (!pagesListCities.isEmpty()) {
                                page += 1;
                                player.openInventory(pagesListCities.get(page));
                            }
                        }
                        if (meta.getDisplayName().equals("§7Назад")) {
                            if (page > 0) {
                                page -= 1;
                                player.openInventory(pagesListCities.get(page));
                            }
                        }
                    }
                    event.setCancelled(true);
                }
            }
        }
    }
}
