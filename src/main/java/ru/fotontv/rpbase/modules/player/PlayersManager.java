package ru.fotontv.rpbase.modules.player;

import me.yic.xconomy.data.DataFormat;
import me.yic.xconomy.data.caches.Cache;
import net.milkbowl.vault.permission.Permission;
import org.bukkit.*;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import ru.fotontv.rpbase.RPBase;
import ru.fotontv.rpbase.config.GlobalConfig;
import ru.fotontv.rpbase.enums.ProfessionsEnum;
import ru.fotontv.rpbase.modules.city.CitiesManager;
import ru.fotontv.rpbase.modules.jail.JailManager;
import ru.fotontv.rpbase.utils.LinkedSet;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.*;

public class PlayersManager implements Listener {

    private static final Set<PlayerData> playerDataList = new LinkedSet<>();
    private static final List<ItemStack> itemsRul = new ArrayList<>();
    private static final HashMap<Player, Integer> taskID = new HashMap<>();
    private static final Permission permission = RPBase.getPlugin().getPermission();
    private static File playersFile;
    private static FileConfiguration playersConfig;
    private static int timer = 0;

    public PlayersManager() {
    }

    public static void load() {
        playersFile = new File(RPBase.getPlugin().getDataFolder(), "players.yml");
        if (!(playersFile.exists())) {
            if (!(playersFile.getParentFile().mkdirs())) System.out.println();
            RPBase.getPlugin().saveResource("players.yml", false);
            playersConfig = new YamlConfiguration();
            try {
                playersConfig.load(playersFile);
            } catch (InvalidConfigurationException | IOException e) {
                e.printStackTrace();
            }
            return;
        }
        playersConfig = new YamlConfiguration();
        try {
            playersConfig.load(playersFile);
        } catch (InvalidConfigurationException | IOException e) {
            e.printStackTrace();
        }
    }

    private static void setPlayerDataList(Player player) {
        playerDataList.add(new PlayerData(player));
    }

    private static void removePlayerData(Player player) {
        for (PlayerData playerData : playerDataList) {
            if (playerData.getPlayer().getName().equals(player.getName())) {
                playerDataList.remove(playerData);
                JailManager.leavePlayer(player);
            }
        }

    }

    public static PlayerData getPlayerData(Player player) {
        for (PlayerData playerData : playerDataList) {
            if (playerData.getPlayer().getName().equals(player.getName()))
                return playerData;
        }
        return null;
    }

    public static void disbandCity(PlayerData data) {
        if (data != null) {
            data.setCity(null);
            data.setCityName("-");
            data.setProfession(ProfessionsEnum.PLAYER);
            data.getPassport().setProfession("-");
            data.setDateInput("-");
        }
    }

    public static void savePlayerData(Player player) {
        PlayerData data = getPlayerData(player);
        if (playersConfig.contains(player.getName()) && data != null) {
            playersConfig.set(player.getName() + ".professionsEnum", data.getProfession().name());
            playersConfig.set(player.getName() + ".pickUpCity", data.getPassport().getPickUpCity());
            playersConfig.set(player.getName() + ".isPickUpCity", data.getPassport().isPickUpCity());
            playersConfig.set(player.getName() + ".cityName", data.getCityName());
            playersConfig.set(player.getName() + ".cityDateInput", data.getDateInput());
            playersConfig.set(player.getName() + ".dateOfReceipt", data.getPassport().getDateOfReceipt());
            playersConfig.set(player.getName() + ".placeOfResidence", data.getPassport().getPlaceOfResidence());
            playersConfig.set(player.getName() + ".criminalRecords", data.getPassport().getCriminalRecords());
            playersConfig.set(player.getName() + ".profession", data.getPassport().getProfession());
            playersConfig.set(player.getName() + ".isChatCity", data.isChatCity());
            playersConfig.set(player.getName() + ".countUnlockSuccess", data.getCountUnlock());
            playersConfig.set(player.getName() + ".pexs", data.getPexs());
            playersConfig.set(player.getName() + ".pexsTalent", data.getPexsTalent());
        }
        try {
            playersConfig.save(playersFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
        removePlayerData(player);
    }

    public static void savePlayerData(PlayerData data) {
        playerDataList.removeIf(data1 -> data1.getNick().equals(data.getNick()));
        playerDataList.add(data);
    }

    public static void savesConfigs() {
        for (PlayerData data : PlayersManager.playerDataList) {
            PlayersManager.savePlayerDataNotCFG(data.getPlayer());
        }
    }

    public static void savePlayerDataNotCFG(Player player) {
        PlayerData data = getPlayerData(player);
        if (playersConfig.contains(player.getName()) && data != null) {
            playersConfig.set(player.getName() + ".professionsEnum", data.getProfession().name());
            playersConfig.set(player.getName() + ".pickUpCity", data.getPassport().getPickUpCity());
            playersConfig.set(player.getName() + ".isPickUpCity", data.getPassport().isPickUpCity());
            playersConfig.set(player.getName() + ".cityName", data.getCityName());
            playersConfig.set(player.getName() + ".cityDateInput", data.getDateInput());
            playersConfig.set(player.getName() + ".dateOfReceipt", data.getPassport().getDateOfReceipt());
            playersConfig.set(player.getName() + ".placeOfResidence", data.getPassport().getPlaceOfResidence());
            playersConfig.set(player.getName() + ".criminalRecords", data.getPassport().getCriminalRecords());
            playersConfig.set(player.getName() + ".profession", data.getPassport().getProfession());
            playersConfig.set(player.getName() + ".isChatCity", data.isChatCity());
            playersConfig.set(player.getName() + ".countUnlockSuccess", data.getCountUnlock());
            playersConfig.set(player.getName() + ".pexs", data.getPexs());
            playersConfig.set(player.getName() + ".pexsTalent", data.getPexsTalent());
        }
        try {
            playersConfig.save(playersFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @SuppressWarnings("deprecation")
    public static PlayerData loadPlayerData(String nick) {
        if (playersConfig.contains(nick)) {
            String professionsEnum = playersConfig.getString(nick + ".professionsEnum");
            String pickUpCity = playersConfig.getString(nick + ".pickUpCity");
            boolean isPickUpCity = playersConfig.getBoolean(nick + ".isPickUpCity");
            String cityName = playersConfig.getString(nick + ".cityName");
            String cityDateInput = playersConfig.getString(nick + ".cityDateInput");
            String dateOfReceipt = playersConfig.getString(nick + ".dateOfReceipt");
            String placeOfResidence = playersConfig.getString(nick + ".placeOfResidence");
            List<String> criminalRecords = playersConfig.getStringList(nick + ".criminalRecords");
            String profession = playersConfig.getString(nick + ".profession");
            boolean isChatCity = playersConfig.getBoolean(nick + ".isChatCity");
            int countUnlockSuccess = playersConfig.getInt(nick + ".countUnlockSuccess");
            List<String> pexs = playersConfig.getStringList(nick + ".pexs");
            List<String> pexsTalent = playersConfig.getStringList(nick + ".pexsTalent");
            ProfessionsEnum prof = ProfessionsEnum.valueOf(professionsEnum);
            OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(nick);
            PlayerData data = new PlayerData(offlinePlayer);
            data.setProfession(prof);
            data.setPexs(pexs);
            data.setPexsTalent(pexsTalent);
            Passport passport = new Passport();
            passport.setPickUpCity(pickUpCity);
            passport.setIsPickUpCity(isPickUpCity);
            data.setCityName(cityName);
            data.setDateInput(cityDateInput);
            data.setCity(CitiesManager.getCity(cityName));
            data.setChatCity(isChatCity);
            data.setCountUnlock(countUnlockSuccess);
            passport.setPlaceOfResidence(placeOfResidence);
            passport.setDateOfReceipt(dateOfReceipt);
            passport.setProfession(profession);
            passport.setCriminalRecords(criminalRecords);
            data.setPassport(passport);
            return data;
        }
        return null;
    }

    public static void loadPlayerData(Player player) {
        if (playersConfig.contains(player.getName())) {
            String professionsEnum = playersConfig.getString(player.getName() + ".professionsEnum");
            String pickUpCity = playersConfig.getString(player.getName() + ".pickUpCity");
            boolean isPickUpCity = playersConfig.getBoolean(player.getName() + ".isPickUpCity");
            String cityName = playersConfig.getString(player.getName() + ".cityName");
            String cityDateInput = playersConfig.getString(player.getName() + ".cityDateInput");
            String dateOfReceipt = playersConfig.getString(player.getName() + ".dateOfReceipt");
            String placeOfResidence = playersConfig.getString(player.getName() + ".placeOfResidence");
            List<String> criminalRecords = playersConfig.getStringList(player.getName() + ".criminalRecords");
            String profession = playersConfig.getString(player.getName() + ".profession");
            boolean isChatCity = playersConfig.getBoolean(player.getName() + ".isChatCity");
            int countUnlockSuccess = playersConfig.getInt(player.getName() + ".countUnlockSuccess");
            List<String> pexs = playersConfig.getStringList(player.getName() + ".pexs");
            List<String> pexsTalent = playersConfig.getStringList(player.getName() + ".pexsTalent");
            ProfessionsEnum prof = ProfessionsEnum.valueOf(professionsEnum);
            PlayerData data = new PlayerData(player);
            data.setProfession(prof);
            data.setPexs(pexs);
            data.setPexsTalent(pexsTalent);
            addPexs(player, pexs);
            addPexsTalent(player, pexsTalent);
            Passport passport = new Passport();
            passport.setPickUpCity(pickUpCity);
            passport.setIsPickUpCity(isPickUpCity);
            data.setCityName(cityName);
            data.setDateInput(cityDateInput);
            data.setCity(CitiesManager.getCity(cityName));
            data.setChatCity(isChatCity);
            data.setCountUnlock(countUnlockSuccess);
            passport.setPlaceOfResidence(placeOfResidence);
            passport.setDateOfReceipt(dateOfReceipt);
            passport.setProfession(profession);
            passport.setCriminalRecords(criminalRecords);
            data.setPassport(passport);
            playerDataList.add(data);
        } else {
            playersConfig.createSection(player.getName());
            setPlayerDataList(player);
        }
    }

    public static void addPexs(Player player, List<String> pexs) {
        if (permission != null) {
            for (String pex : pexs) {
                permission.playerAdd(player, pex);
            }
        }
    }

    public static void addPexsTalent(Player player, List<String> pexs) {
        if (permission != null) {
            for (String pex : pexs) {
                permission.playerAdd(player, pex);
            }
        }
    }

    public static void addPex(ProfessionsEnum prof, Player player, PlayerData data) {
        List<String> pexAll = formatPex(prof);
        if (permission != null) {
            for (String pex : pexAll) {
                permission.playerAdd(player, pex);
                data.setPexs(pexAll);
            }
        }
    }

    public static void removePex(Player player, PlayerData data) {
        List<String> pexAll = data.getPexs();
        if (permission != null) {
            for (String pex : pexAll) {
                permission.playerRemove(player, pex);
            }
        }
    }

    private static List<String> formatPex(ProfessionsEnum prof) {
        List<String> pexAll = new ArrayList<>();
        for (String pex : prof.getPermissions()) {
            if (pex.contains("parent.")) {
                String[] splitPex = pex.split("\\.");
                String profDepend = splitPex[1];
                ProfessionsEnum professionsEnum = ProfessionsEnum.getProf(profDepend);
                pexAll.addAll(professionsEnum.getPermissions());
                continue;
            }
            pexAll.add(pex);
        }
        return pexAll;
    }

    public static void openPass(Player player) {
        String namePassportGUI = GlobalConfig.NAMEPASSPORTGUI.replace("{player}", player.getName());
        Inventory pass = Bukkit.getServer().createInventory(player, 9, namePassportGUI);
        PlayerData data = getPlayerData(player);
        if (data != null)
            pass.setItem(4, data.getPassportItem());
        player.openInventory(pass);
    }

    public static void openPassOther(Player player, Player request) {
        String namePassportGUI = GlobalConfig.NAMEPASSPORTGUI.replace("{player}", player.getName());
        Inventory pass = Bukkit.getServer().createInventory(player, 9, namePassportGUI);
        PlayerData data = getPlayerData(player);
        if (data != null)
            pass.setItem(4, data.getPassportItem());
        request.openInventory(pass);
    }

    public static void openTalentGui(Player player) {
        String name = "§8Рулетка талантов";
        Inventory talent = Bukkit.getServer().createInventory(player, 36, centerTitle(name));

        //Рулетка талантов подтверждение
        ItemStack talentSuccess = new ItemStack(Material.EMERALD_BLOCK);
        ItemMeta talentSuccessMeta = talentSuccess.getItemMeta();
        talentSuccessMeta.setDisplayName("§cРулетка талантов");
        List<String> loreTalentSuccess = new ArrayList<>();
        loreTalentSuccess.add("§7Цена: 100gl");
        talentSuccessMeta.setLore(loreTalentSuccess);
        talentSuccess.setItemMeta(talentSuccessMeta);
        talent.setItem(31, talentSuccess);

        //Родство с магией
        ItemStack item1 = new ItemStack(Material.PURPLE_DYE);
        ItemMeta meta1 = item1.getItemMeta();
        meta1.setDisplayName("§7Родство с магией");
        List<String> lore1 = new ArrayList<>();
        lore1.add("§fДаёт возможность открывать чародейский стол");
        meta1.setLore(lore1);
        meta1.addEnchant(Enchantment.LURE, 0, true);
        meta1.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        item1.setItemMeta(meta1);
        talent.setItem(10, item1);
        itemsRul.add(item1);

        //Рождённый в кузне
        ItemStack item2 = new ItemStack(Material.IRON_CHESTPLATE);
        ItemMeta meta2 = item2.getItemMeta();
        meta2.setDisplayName("§7Рождённый в кузне");
        List<String> lore2 = new ArrayList<>();
        lore2.add("§fДаёт возможность крафтить железную броню");
        meta2.setLore(lore2);
        item2.setItemMeta(meta2);
        talent.setItem(11, item2);
        itemsRul.add(item2);

        //Мастер мечей
        ItemStack item3 = new ItemStack(Material.IRON_SWORD);
        ItemMeta meta3 = item3.getItemMeta();
        meta3.setDisplayName("§7Мастер мечей");
        List<String> lore3 = new ArrayList<>();
        lore3.add("§fДаёт возможность крафтить алмазный и железный мечи");
        meta3.setLore(lore3);
        item3.setItemMeta(meta3);
        talent.setItem(12, item3);
        itemsRul.add(item3);

        //Ловкие руки
        ItemStack item4 = new ItemStack(Material.TRIPWIRE_HOOK);
        ItemMeta meta4 = item4.getItemMeta();
        meta4.setDisplayName("§7Ловкие руки");
        List<String> lore4 = new ArrayList<>();
        lore4.add("§fДаёт дополнительные 5% к удачному использованию отмычки");
        meta4.setLore(lore4);
        item4.setItemMeta(meta4);
        talent.setItem(13, item4);
        itemsRul.add(item4);

        //Новая жизнь
        ItemStack item5 = new ItemStack(Material.MILK_BUCKET);
        ItemMeta meta5 = item5.getItemMeta();
        meta5.setDisplayName("§7Новая жизнь");
        List<String> lore5 = new ArrayList<>();
        lore5.add("§fСнимает с Вас все профессии кроме Мэра");
        meta5.setLore(lore5);
        item5.setItemMeta(meta5);
        talent.setItem(14, item5);
        itemsRul.add(item5);

        //Воин
        ItemStack item6 = new ItemStack(Material.MAGMA_CREAM);
        ItemMeta meta6 = item6.getItemMeta();
        meta6.setDisplayName("§7Воин");
        List<String> lore6 = new ArrayList<>();
        lore6.add("§fАктивируется только при полной алмазной");
        lore6.add("§fэкипировке. Накладывает эффект силы и");
        lore6.add("§fзамедление при полной надетой алмазной экипировки.");
        meta6.setLore(lore6);
        meta6.addEnchant(Enchantment.LURE, 0, true);
        meta6.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        item6.setItemMeta(meta6);
        talent.setItem(15, item6);
        itemsRul.add(item6);

        //Большой нос
        ItemStack item7 = new ItemStack(Material.EMERALD);
        ItemMeta meta7 = item7.getItemMeta();
        meta7.setDisplayName("§7Большой нос");
        List<String> lore7 = new ArrayList<>();
        lore7.add("§fВы отлично ладите с жителями!");
        meta7.setLore(lore7);
        item7.setItemMeta(meta7);
        talent.setItem(16, item7);
        itemsRul.add(item7);

        player.openInventory(talent);
    }

    private static String centerTitle(String title) {
        StringBuilder result = new StringBuilder();
        int spaces = (24 - ChatColor.stripColor(title).length());

        for (int i = 0; i < spaces; i++) {
            result.append(" ");
        }

        return result.append(title).toString();
    }

    private static void onRul(PlayerData data) {
        Player player = data.getPlayer();
        String name = "§8Рулетка талантов";
        Inventory inventory = Bukkit.createInventory(player, 36, centerTitle(name));
        Random r1 = new Random();
        final Random r2 = new Random();
        int i;
        for (i = 10; i < 17; i++) {
            int n1 = r1.nextInt(itemsRul.size());
            inventory.setItem(i, itemsRul.get(n1));
        }
        ItemStack itemStack = new ItemStack(Material.GRAY_STAINED_GLASS_PANE);
        ItemStack itemStack1 = new ItemStack(Material.BLACK_STAINED_GLASS_PANE);

        inventory.setItem(0, itemStack);
        inventory.setItem(1, itemStack);
        inventory.setItem(2, itemStack);
        inventory.setItem(3, itemStack);
        inventory.setItem(4, itemStack1);
        inventory.setItem(5, itemStack);
        inventory.setItem(6, itemStack);
        inventory.setItem(7, itemStack);
        inventory.setItem(8, itemStack);
        inventory.setItem(9, itemStack);
        inventory.setItem(17, itemStack);
        inventory.setItem(18, itemStack);
        inventory.setItem(19, itemStack);
        inventory.setItem(20, itemStack);
        inventory.setItem(21, itemStack);
        inventory.setItem(22, itemStack1);
        inventory.setItem(23, itemStack);
        inventory.setItem(24, itemStack);
        inventory.setItem(25, itemStack);
        inventory.setItem(26, itemStack);
        inventory.setItem(27, itemStack);
        inventory.setItem(28, itemStack);
        inventory.setItem(29, itemStack);
        inventory.setItem(30, itemStack);
        inventory.setItem(31, itemStack);
        inventory.setItem(32, itemStack);
        inventory.setItem(33, itemStack);
        inventory.setItem(34, itemStack);
        inventory.setItem(35, itemStack);

        int tid;
        tid = Bukkit.getScheduler().scheduleSyncRepeatingTask(RPBase.getPlugin(), () -> {
            PlayersManager.timer = PlayersManager.timer + 1;
            int n2 = r2.nextInt(itemsRul.size());
            inventory.setItem(10, inventory.getItem(11));
            inventory.setItem(11, inventory.getItem(12));
            inventory.setItem(12, inventory.getItem(13));
            inventory.setItem(13, inventory.getItem(14));
            inventory.setItem(14, inventory.getItem(15));
            inventory.setItem(15, inventory.getItem(16));
            inventory.setItem(16, itemsRul.get(n2));
            player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1.0F, 1.0F);
            if (PlayersManager.timer == 40) {
                PlayersManager.stopRolling(player);
                PlayersManager.timer = 0;
                player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1.0F, 1.0F);
                ItemStack stack = inventory.getItem(13);
                if (stack != null) {
                    player.sendMessage("§2Вам выпал талант: " + stack.getItemMeta().getDisplayName());
                    addTalent(stack, player);
                }
                Bukkit.getScheduler().scheduleSyncDelayedTask(RPBase.getPlugin(), player::closeInventory, 30L);
            }
        }, 0L, 2);
        taskID.put(player, tid);
        player.openInventory(inventory);
    }

    private static void addTalent(ItemStack stack, Player player) {
        List<String> pexsTalent =  new ArrayList<>();
        PlayerData data = PlayersManager.getPlayerData(player);
        if (data == null)
            return;
        switch (stack.getItemMeta().getDisplayName()) {
            case "§7Родство с магией":
                pexsTalent.add("xrp.open_enchanting_table");
                addPexsTalent(data.getPlayer(), pexsTalent);
                pexsTalent.clear();
                break;
            case "§7Рождённый в кузне":
                pexsTalent.add("craftingapi.recipe.craftingapi.iron_helmet");
                pexsTalent.add("craftingapi.recipe.craftingapi.iron_chestplate");
                pexsTalent.add("craftingapi.recipe.craftingapi.iron_leggins");
                pexsTalent.add("craftingapi.recipe.craftingapi.iron_boots");
                addPexsTalent(data.getPlayer(), pexsTalent);
                pexsTalent.clear();
                break;
            case "§7Мастер мечей":
                pexsTalent.add("craftingapi.recipe.craftingapi.diamond_sword");
                pexsTalent.add("craftingapi.recipe.craftingapi.iron_sword");
                addPexsTalent(data.getPlayer(), pexsTalent);
                pexsTalent.clear();
                break;
            case "§7Ловкие руки":
                pexsTalent.add("keylock.talentVor");
                addPexsTalent(data.getPlayer(), pexsTalent);
                pexsTalent.clear();
                break;
            case "§7Новая жизнь":
                if (!data.getProfession().equals(ProfessionsEnum.MAYOR)) {
                    data.setProfession(ProfessionsEnum.PLAYER);
                    data.getPassport().setProfession("-");
                }
                break;
            case "§7Воин":
                pexsTalent.add("rpbase.talentVoin");
                addPexsTalent(data.getPlayer(), pexsTalent);
                pexsTalent.clear();
                break;
            case "§7Большой нос":
                pexsTalent.add("rpbase.talentBigNos");
                addPexsTalent(data.getPlayer(), pexsTalent);
                pexsTalent.clear();
                break;
        }
    }

    private static void stopRolling(Player p) {
        if (taskID.containsKey(p)) {
            int tid = taskID.get(p);
            RPBase.getPlugin().getServer().getScheduler().cancelTask(tid);
            taskID.remove(p);
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void pickupItemInventory(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        PlayerData data = getPlayerData(player);
        if (data != null) {
            if (player.getGameMode() == GameMode.CREATIVE) {
                event.setCancelled(false);
                return;
            }
            Inventory inv = event.getClickedInventory();
            ItemStack itemClick = event.getCurrentItem();
            if (inv != null) {
                if (inv.contains(data.getPassportItem())) {
                    event.setCancelled(true);
                    return;
                }

                //Таланты
                if (event.getView().getTitle().equals(centerTitle("§8Рулетка талантов")) && (inv.getSize() == 36)) {
                    if (itemClick != null) {
                        if (itemClick.getItemMeta().getDisplayName().equals("§cРулетка талантов")) {
                            UUID targetUUID = Cache.translateUUID(player.getName(), null);
                            BigDecimal bal = Cache.getBalanceFromCacheOrDB(player.getUniqueId());
                            String realname = Cache.getrealname(player.getName());
                            if (bal.intValue() >= 100) {
                                String amountStr = String.valueOf(100);
                                BigDecimal amount = DataFormat.formatString(amountStr);
                                Cache.change("ADMIN_COMMAND", targetUUID, realname, amount, false, "");
                                onRul(data);
                                event.setCancelled(true);
                                return;
                            }
                            player.sendMessage("§cУ вас не хватает " + (100 - bal.intValue()) + " для прокрутки рулетки!");
                            event.setCancelled(true);
                            return;
                        }
                    }
                    event.setCancelled(true);
                }
            }
        }
    }
}
