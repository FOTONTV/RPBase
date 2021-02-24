package ru.fotontv.rpbase.modules.city;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;
import ru.fotontv.rpbase.config.GlobalConfig;
import ru.fotontv.rpbase.enums.CityStatusEnum;
import ru.fotontv.rpbase.enums.ProfessionsEnum;
import ru.fotontv.rpbase.modules.player.PlayerData;
import ru.fotontv.rpbase.modules.player.PlayersManager;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class City {
    private final String nameCity;
    private List<String> citizen = new ArrayList<>();
    private String mayor;
    private String dateOfFoundation;
    private CityStatusEnum cityStatus;

    public City(String nameCity, String mayor) {
        this.nameCity = nameCity;
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Date date = new Date();
        this.dateOfFoundation = dateFormat.format(date);
        this.mayor = mayor;
        this.cityStatus = CityStatusEnum.SETTLEMENT;
    }

    @SuppressWarnings("deprecation")
    public List<ItemStack> getSkullCitizens() {
        List<ItemStack> skullCitizens = new ArrayList<>();
        for (String nick : citizen) {
            Player player = Bukkit.getPlayer(nick);
            PlayerData playerData = PlayersManager.getPlayerData(player);
            ItemStack skull = new ItemStack(Material.PLAYER_HEAD);
            SkullMeta meta = (SkullMeta) skull.getItemMeta();
            List<String> lore = new ArrayList<>();
            if (player != null && playerData != null) {
                meta.setOwningPlayer(player);
                meta.setDisplayName(player.getName());
                for (String lor : GlobalConfig.LORESKULLCITIZEN) {
                    if (lor.contains("{player}")) {
                        lor = lor.replace("{player}", player.getName());
                    }
                    if (lor.contains("{profession}")) {
                        lor = lor.replace("{profession}", playerData.getPassport().getProfession());
                    }
                    if (lor.contains("{dateOfEntryCity}")) {
                        lor = lor.replace("{dateOfEntryCity}", playerData.getDateInput());
                    }
                    lore.add(lor);
                }
            } else {
                OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(nick);
                new Thread(() -> {
                    PlayerData data = PlayersManager.loadPlayerData(nick);
                    meta.setOwningPlayer(offlinePlayer);
                    meta.setDisplayName(nick);
                    for (String lor : GlobalConfig.LORESKULLCITIZEN) {
                        if (lor.contains("{player}")) {
                            lor = lor.replace("{player}", nick);
                        }
                        if (data != null) {
                            if (lor.contains("{profession}")) {
                                lor = lor.replace("{profession}", data.getPassport().getProfession());
                            }
                            if (lor.contains("{dateOfEntryCity}")) {
                                lor = lor.replace("{dateOfEntryCity}", data.getDateInput());
                            }
                        }
                        lore.add(lor);
                    }
                }).start();
            }
            meta.setLore(lore);
            skull.setItemMeta(meta);
            skullCitizens.add(skull);
        }
        return skullCitizens;
    }

    public List<String> getCitizen() {
        return citizen;
    }

    public void setCitizen(List<String> citizen) {
        this.citizen = citizen;
    }

    public void addCitizen(Player player) {
        this.citizen.add(player.getName());
    }

    public void kickCitizen(Player player) {
        this.citizen.remove(player.getName());
    }

    public String getNameCity() {
        return nameCity;
    }

    public String getMayor() {
        return mayor;
    }

    public void setMayor(String mayor) {
        this.mayor = mayor;
    }

    public String getDateOfFoundation() {
        return dateOfFoundation;
    }

    public void setDateOfFoundation(String date) {
        this.dateOfFoundation = date;
    }

    public boolean isInventorAccess() {
        Calendar instance = Calendar.getInstance();
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Date date = new Date();
        try {
            instance.setTime(dateFormat.parse(this.dateOfFoundation));
        } catch (ParseException e) {
            return false;
        }
        instance.add(Calendar.DAY_OF_MONTH, 30);
        Date newDate = instance.getTime();
        return newDate.getTime() <= date.getTime();
    }

    public CityStatusEnum getCityStatus() {
        return cityStatus;
    }

    public void setCityStatus(CityStatusEnum cityStatus) {
        this.cityStatus = cityStatus;
    }

    public boolean isValidProf(ProfessionsEnum professionsEnum) {
        AtomicInteger JUDGE = new AtomicInteger();
        AtomicInteger PASSPORTOFFICER = new AtomicInteger();
        AtomicInteger POLICEMAN = new AtomicInteger();
        AtomicInteger OFFICER = new AtomicInteger();
        AtomicInteger CARETAKER = new AtomicInteger();
        AtomicInteger DETECTIVE = new AtomicInteger();
        AtomicInteger INVENTOR = new AtomicInteger();
        AtomicInteger BLACKSMITH = new AtomicInteger();
        AtomicInteger WIZARD = new AtomicInteger();
        AtomicInteger COOK = new AtomicInteger();
        AtomicInteger BREWER = new AtomicInteger();
        for (String nick : citizen) {
            new Thread(() -> {
                PlayerData data = PlayersManager.loadPlayerData(nick);
                if (data != null) {
                    if (data.getProfession().equals(ProfessionsEnum.JUDGE)) {
                        JUDGE.addAndGet(1);
                    }
                    if (data.getProfession().equals(ProfessionsEnum.PASSPORTOFFICER)) {
                        PASSPORTOFFICER.addAndGet(1);
                    }
                    if (data.getProfession().equals(ProfessionsEnum.POLICEMAN)) {
                        POLICEMAN.addAndGet(1);
                    }
                    if (data.getProfession().equals(ProfessionsEnum.OFFICER)) {
                        OFFICER.addAndGet(1);
                    }
                    if (data.getProfession().equals(ProfessionsEnum.CARETAKER)) {
                        CARETAKER.addAndGet(1);
                    }
                    if (data.getProfession().equals(ProfessionsEnum.DETECTIVE)) {
                        DETECTIVE.addAndGet(1);
                    }
                    if (data.getProfession().equals(ProfessionsEnum.INVENTOR)) {
                        INVENTOR.addAndGet(1);
                    }
                    if (data.getProfession().equals(ProfessionsEnum.BLACKSMITH)) {
                        BLACKSMITH.addAndGet(1);
                    }
                    if (data.getProfession().equals(ProfessionsEnum.WIZARD)) {
                        WIZARD.addAndGet(1);
                    }
                    if (data.getProfession().equals(ProfessionsEnum.COOK)) {
                        COOK.addAndGet(1);
                    }
                    if (data.getProfession().equals(ProfessionsEnum.BREWER)) {
                        BREWER.addAndGet(1);
                    }
                }
            }).start();
        }
        if (professionsEnum.equals(ProfessionsEnum.JUDGE)) {
            if (cityStatus.getJUDGE() == 0)
                return false;
            if (JUDGE.get() < cityStatus.getJUDGE())
                return true;
        }
        if (professionsEnum.equals(ProfessionsEnum.PASSPORTOFFICER)) {
            if (cityStatus.getPASSPORTOFFICER() == 0)
                return false;
            if (PASSPORTOFFICER.get() < cityStatus.getPASSPORTOFFICER())
                return true;
        }
        if (professionsEnum.equals(ProfessionsEnum.POLICEMAN)) {
            if (cityStatus.getPOLICEMAN() == 0)
                return false;
            if (POLICEMAN.get() < cityStatus.getPOLICEMAN())
                return true;
        }
        if (professionsEnum.equals(ProfessionsEnum.OFFICER)) {
            if (cityStatus.getOFFICER() == 0)
                return false;
            if (OFFICER.get() < cityStatus.getOFFICER())
                return true;
        }
        if (professionsEnum.equals(ProfessionsEnum.CARETAKER)) {
            if (cityStatus.getCARETAKER() == 0)
                return false;
            if (CARETAKER.get() < cityStatus.getCARETAKER())
                return true;
        }
        if (professionsEnum.equals(ProfessionsEnum.DETECTIVE)) {
            if (cityStatus.getDETECTIVE() == 0)
                return false;
            if (DETECTIVE.get() < cityStatus.getDETECTIVE())
                return true;
        }
        if (professionsEnum.equals(ProfessionsEnum.INVENTOR)) {
            if (cityStatus.getINVENTOR() == 0)
                return false;
            if (INVENTOR.get() < cityStatus.getINVENTOR())
                return true;
        }
        if (professionsEnum.equals(ProfessionsEnum.BLACKSMITH)) {
            if (cityStatus.getBLACKSMITH() == 0)
                return false;
            if (BLACKSMITH.get() < cityStatus.getBLACKSMITH())
                return true;
        }
        if (professionsEnum.equals(ProfessionsEnum.WIZARD)) {
            if (cityStatus.getWIZARD() == 0)
                return false;
            if (WIZARD.get() < cityStatus.getWIZARD())
                return true;
        }
        if (professionsEnum.equals(ProfessionsEnum.COOK)) {
            if (cityStatus.getCOOK() == 0)
                return false;
            if (COOK.get() < cityStatus.getCOOK())
                return true;
        }
        if (professionsEnum.equals(ProfessionsEnum.BREWER)) {
            if (cityStatus.getBREWER() == 0)
                return false;
            return BREWER.get() < cityStatus.getBREWER();
        }
        return false;
    }

    public void upgrade(PlayerData data) {
        int goldOreAmount = 0;
        ItemStack[] contents = data.getPlayer().getInventory().getContents();
        for (ItemStack item : contents) {
            if (item != null && item.getType().equals(Material.GOLD_ORE))
                goldOreAmount += item.getAmount();
        }
        if (cityStatus.equals(CityStatusEnum.SETTLEMENT)) {
            if (citizen.size() != CityStatusEnum.SETTLEMENT.getMaxCitizens()) {
                int ostCitizen = CityStatusEnum.SETTLEMENT.getMaxCitizens() - citizen.size();
                data.getPlayer().sendMessage("§cВам не хватает §6" + ostCitizen + (ostCitizen == 1 ? "жителя" : "жителей") + "§c.");
                return;
            }
            if (goldOreAmount < CityStatusEnum.PROVINCE.getGoldOre()) {
                data.getPlayer().sendMessage("§cВам не хватает §6" + (CityStatusEnum.PROVINCE.getGoldOre() - goldOreAmount) + " золотой руды§c.");
                return;
            }
            int resultOre = 0;
            ItemStack[] contents1 = data.getPlayer().getInventory().getContents();
            for (ItemStack stack : contents1) {
                if (resultOre == CityStatusEnum.PROVINCE.getGoldOre()) break;
                if (!(stack == null)) {
                    if (stack.getType() == Material.GOLD_ORE) {
                        if ((stack.getAmount() + resultOre) > CityStatusEnum.PROVINCE.getGoldOre()) {
                            stack.setAmount(CityStatusEnum.PROVINCE.getGoldOre() - resultOre);
                            resultOre += (CityStatusEnum.PROVINCE.getGoldOre() - resultOre);
                        } else {
                            resultOre += stack.getAmount();
                            stack.setAmount(0);
                        }
                    }
                }
            }
            cityStatus = CityStatusEnum.PROVINCE;
            data.getPlayer().sendMessage("§fВаше поселение стало §cпровинцией");
            return;
        }
        if (cityStatus.equals(CityStatusEnum.PROVINCE)) {
            if (citizen.size() != CityStatusEnum.PROVINCE.getMaxCitizens()) {
                int ostCitizen = CityStatusEnum.PROVINCE.getMaxCitizens() - citizen.size();
                data.getPlayer().sendMessage("§cВам не хватает §6" + ostCitizen + (ostCitizen == 1 ? "жителя" : "жителей") + "§c.");
                return;
            }
            if (goldOreAmount < CityStatusEnum.CITY.getGoldOre()) {
                data.getPlayer().sendMessage("§cВам не хватает §6" + (CityStatusEnum.CITY.getGoldOre() - goldOreAmount) + " золотой руды§c.");
                return;
            }
            int resultOre = 0;
            ItemStack[] contents1 = data.getPlayer().getInventory().getContents();
            for (ItemStack stack : contents1) {
                if (resultOre == CityStatusEnum.CITY.getGoldOre()) break;
                if (!(stack == null)) {
                    if (stack.getType() == Material.GOLD_ORE) {
                        if ((stack.getAmount() + resultOre) > CityStatusEnum.CITY.getGoldOre()) {
                            stack.setAmount(CityStatusEnum.CITY.getGoldOre() - resultOre);
                            resultOre += (CityStatusEnum.CITY.getGoldOre() - resultOre);
                        } else {
                            resultOre += stack.getAmount();
                            stack.setAmount(0);
                        }
                    }
                }
            }
            cityStatus = CityStatusEnum.CITY;
            data.getPlayer().sendMessage("§fВаша провинция стала §cгородом");
            return;
        }
        if (cityStatus.equals(CityStatusEnum.CITY)) {
            if (citizen.size() != CityStatusEnum.CITY.getMaxCitizens()) {
                int ostCitizen = CityStatusEnum.CITY.getMaxCitizens() - citizen.size();
                data.getPlayer().sendMessage("§cВам не хватает §6" + ostCitizen + (ostCitizen == 1 ? "жителя" : "жителей") + "§c.");
                return;
            }
            if (goldOreAmount < CityStatusEnum.METROPOLIS.getGoldOre()) {
                data.getPlayer().sendMessage("§cВам не хватает §6" + (CityStatusEnum.METROPOLIS.getGoldOre() - goldOreAmount) + " золотой руды§c.");
                return;
            }
            int resultOre = 0;
            ItemStack[] contents1 = data.getPlayer().getInventory().getContents();
            for (ItemStack stack : contents1) {
                if (resultOre == CityStatusEnum.METROPOLIS.getGoldOre()) break;
                if (!(stack == null)) {
                    if (stack.getType() == Material.GOLD_ORE) {
                        if ((stack.getAmount() + resultOre) > CityStatusEnum.METROPOLIS.getGoldOre()) {
                            stack.setAmount(CityStatusEnum.METROPOLIS.getGoldOre() - resultOre);
                            resultOre += (CityStatusEnum.METROPOLIS.getGoldOre() - resultOre);
                        } else {
                            resultOre += stack.getAmount();
                            stack.setAmount(0);
                        }
                    }
                }
            }
            cityStatus = CityStatusEnum.METROPOLIS;
            data.getPlayer().sendMessage("§fВаш город стал §cмегаполисом");
        }
    }
}
