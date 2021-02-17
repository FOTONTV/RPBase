package ru.fotontv.rpbase.modules.player;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import ru.fotontv.rpbase.config.GlobalConfig;
import ru.fotontv.rpbase.enums.ProfessionsEnum;
import ru.fotontv.rpbase.modules.city.City;

import java.util.ArrayList;
import java.util.List;

public class PlayerData {
    public Passport passport;
    private Player player;
    private String nick;
    private long startTimeImp;
    private String timeImp = "";
    private ProfessionsEnum profession;
    private ItemStack cityInfoItem;
    private String nickPassportRequest = "";
    private String nickCityRequest = "";
    private City city;
    private String cityName = "-";
    private String dateInput = "-";
    private boolean isChatCity = false;
    private Integer countUnlock = 0;
    private List<String> pexs;
    private List<String> pexsTalent = new ArrayList<>();

    public PlayerData(Player player) {
        this.player = player;
        this.nick = player.getName();
        this.profession = ProfessionsEnum.PLAYER;
        this.passport = new Passport();
        this.pexs = ProfessionsEnum.PLAYER.getPermissions();
    }

    public PlayerData(OfflinePlayer player) {
        this.nick = player.getName();
        this.profession = ProfessionsEnum.PLAYER;
        this.passport = new Passport();
        this.pexs = ProfessionsEnum.PLAYER.getPermissions();
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }

    public ItemStack getPassportItem() {
        ItemStack passportItem = new ItemStack(Material.BOOK);
        ItemMeta metBook = passportItem.getItemMeta();
        List<String> lore = new ArrayList<>();
        for (String lor : GlobalConfig.LOREPASSPORT) {
            if (lor.contains("{player}")) {
                String[] splitStr = lor.split(": ");
                String oneStr = ChatColor.translateAlternateColorCodes('&', splitStr[0]);
                String twoStr = splitStr[1].replace("{player}", player.getName());
                twoStr = ChatColor.translateAlternateColorCodes('&', twoStr);
                lor = oneStr + ": " + twoStr;
            }
            if (lor.contains("{pickUpCity}")) {
                String[] splitStr = lor.split(": ");
                String oneStr = ChatColor.translateAlternateColorCodes('&', splitStr[0]);
                String twoStr = splitStr[1].replace("{pickUpCity}", passport.getPickUpCity());
                twoStr = ChatColor.translateAlternateColorCodes('&', twoStr);
                lor = oneStr + ": " + twoStr;
            }
            if (lor.contains("{dateOfReceipt}")) {
                String[] splitStr = lor.split(": ");
                String oneStr = ChatColor.translateAlternateColorCodes('&', splitStr[0]);
                String twoStr = splitStr[1].replace("{dateOfReceipt}", passport.getDateOfReceipt());
                twoStr = ChatColor.translateAlternateColorCodes('&', twoStr);
                lor = oneStr + ": " + twoStr;
            }
            if (lor.contains("{placeOfResidence}")) {
                String[] splitStr = lor.split(": ");
                String oneStr = ChatColor.translateAlternateColorCodes('&', splitStr[0]);
                String twoStr = splitStr[1].replace("{placeOfResidence}", passport.getPlaceOfResidence());
                twoStr = ChatColor.translateAlternateColorCodes('&', twoStr);
                lor = oneStr + ": " + twoStr;
            }
            if (lor.contains("{criminalRecords}")) {
                String[] splitCriminal = lor.split(": ");
                lor = splitCriminal[1].replace("{criminalRecords}", "");
                String oneStr = ChatColor.translateAlternateColorCodes('&', splitCriminal[0]);
                lor = ChatColor.translateAlternateColorCodes('&', lor);
                lore.add(oneStr);
                for (String criminal : passport.getCriminalRecords()) {
                    lore.add(lor + criminal);
                }
                continue;
            }
            if (lor.contains("{profession}")) {
                String[] splitStr = lor.split(": ");
                String oneStr = ChatColor.translateAlternateColorCodes('&', splitStr[0]);
                String twoStr = splitStr[1].replace("{profession}", passport.getProfession());
                twoStr = ChatColor.translateAlternateColorCodes('&', twoStr);
                lor = oneStr + ": " + twoStr;
            }
            lore.add(lor);
        }
        String nameBook = GlobalConfig.NAMEPASSPORT.replace("{player}", player.getName());
        metBook.setDisplayName(nameBook);
        metBook.setLore(lore);
        passportItem.setItemMeta(metBook);
        return passportItem;
    }

    public ItemStack getCityInfoItem() {
        if (cityInfoItem == null) {
            cityInfoItem = new ItemStack(Material.BOOK);
            ItemMeta metBook = cityInfoItem.getItemMeta();
            List<String> lore = new ArrayList<>();
            for (String lor : GlobalConfig.LORECITYINFO) {
                if (lor.contains("{city}")) {
                    lor = lor.replace("{city}", getCityName());
                }
                if (lor.contains("{mayor}")) {
                    lor = lor.replace("{mayor}", getCity().getMayor());
                }
                if (lor.contains("{dateOfFoundation}")) {
                    lor = lor.replace("{dateOfFoundation}", getCity().getDateOfFoundation());
                }
                lore.add(lor);
            }
            String nameBook = GlobalConfig.CITYINFOBOOKNAME.replace("{city}", getCityName());
            metBook.setDisplayName(nameBook);
            metBook.setLore(lore);
            cityInfoItem.setItemMeta(metBook);
        }
        return cityInfoItem;
    }

    public String getNick() {
        return this.nick;
    }

    public void setOfflinePlayer(OfflinePlayer offlinePlayer) {
        this.nick = offlinePlayer.getName();
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
        this.nick = player.getName();
    }

    public void setStartTimeImp() {
        startTimeImp = System.currentTimeMillis();
    }

    public long getStartTimeImp() {
        return startTimeImp;
    }

    public void setStartTimeImp(long time) {
        startTimeImp = time;
    }

    public String getTimeImp() {
        return this.timeImp;
    }

    public void setTimeImp(String timeImp) {
        this.timeImp = timeImp;
    }

    public ProfessionsEnum getProfession() {
        return profession;
    }

    public void setProfession(ProfessionsEnum profession) {
        this.profession = profession;
    }

    public Passport getPassport() {
        return passport;
    }

    public void setPassport(Passport passport) {
        this.passport = passport;
    }

    public String getNickPassportRequest() {
        return nickPassportRequest;
    }

    public void setNickPassportRequest(String nickPassport) {
        this.nickPassportRequest = nickPassport;
    }

    public String getDateInput() {
        return dateInput;
    }

    public void setDateInput(String dateInput) {
        this.dateInput = dateInput;
    }

    public String getNickCityRequest() {
        return nickCityRequest;
    }

    public void setNickCityRequest(String nickCityRequest) {
        this.nickCityRequest = nickCityRequest;
    }

    public boolean isChatCity() {
        return isChatCity;
    }

    public void setChatCity(boolean chatCity) {
        isChatCity = chatCity;
    }

    public Integer getCountUnlock() {
        return countUnlock;
    }

    public void setCountUnlock(int count) {
        countUnlock = count;
    }

    public void addCountUnlock() {
        countUnlock += 1;
    }

    public List<String> getPexs() {
        return pexs;
    }

    public void setPexs(List<String> pexs) {
        this.pexs = pexs;
    }

    public List<String> getPexsTalent() {
        return pexsTalent;
    }

    public void setPexsTalent(List<String> pexsTalent) {
        this.pexsTalent = pexsTalent;
    }

    public void addPexsTalent(List<String> pexsTalent) {
        this.pexsTalent.addAll(pexsTalent);
    }
}
