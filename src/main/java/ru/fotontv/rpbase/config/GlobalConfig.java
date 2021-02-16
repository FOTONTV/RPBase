package ru.fotontv.rpbase.config;

import org.bukkit.ChatColor;
import ru.fotontv.rpbase.RPBase;

import java.util.ArrayList;
import java.util.List;

public class GlobalConfig {

    public static List<String> PLAYER = new ArrayList<>();
    public static List<String> JUDGE = new ArrayList<>();
    public static List<String> PASSPORTOFFICER = new ArrayList<>();
    public static List<String> MAYOR = new ArrayList<>();
    public static List<String> POLICEMAN = new ArrayList<>();
    public static List<String> OFFICER = new ArrayList<>();
    public static List<String> CARETAKER = new ArrayList<>();
    public static List<String> DETECTIVE = new ArrayList<>();
    public static List<String> INVENTOR = new ArrayList<>();
    public static List<String> BLACKSMITH = new ArrayList<>();
    public static List<String> WIZARD = new ArrayList<>();
    public static List<String> COOK = new ArrayList<>();
    public static List<String> BREWER = new ArrayList<>();
    public static List<String> THIEF = new ArrayList<>();
    public static int MAX_PLAYER_IN_CELL = 2;
    public static String NAMEPASSPORTGUI = "";
    public static String NAMEPASSPORT = "";
    public static List<String> LOREPASSPORT = new ArrayList<>();
    public static String CITYINFOGUINAME = "";
    public static String CITYINFOBOOKNAME = "";
    public static List<String> LORECITYINFO = new ArrayList<>();
    public static String CITIZENSCITYGUINAME = "";
    public static String CITIZENSCITYSKULLNAME = "";
    public static List<String> LORESKULLCITIZEN = new ArrayList<>();
    public static String POLICE_WANTED_REMOVE = "";
    public static String PLAYER_WANTED_REMOVE = "";
    public static String POLICE_WANTEDLIST = "";
    public static String POLICE_WANTEDLIST_NICK = "";
    public static String POLICE_WANTEDLIST_ADD = "";
    public static String PLAYER_WANTEDLIST_ADD = "";
    public static String POLICE_JAIL_CREATE = "";
    public static String POLICE_JAIL_REMOVE = "";
    public static String POLICE_JAIL_LIST = "";
    public static String POLICE_JAIL_LIST_NAME = "";
    public static String POLICE_CELL_CREATE = "";
    public static String POLICE_CELL_REMOVE = "";
    public static String POLICE_CELL_LIST = "";
    public static String POLICE_CELL_LIST_NAME = "";
    public static String POLICE_CELL_ADDPLAYER = "";
    public static String PLAYER_CELL_ADD = "";
    public static String PASSPORTOFFICER_PLACEEDIT = "";
    public static String PLAYER_PASSPORT_PLACEEDIT = "";
    public static String PASSPORTOFFICER_PICKUPCITY = "";
    public static String PLAYER_PASSPORT_PICKUPCITY = "";
    public static String JUDGE_CRIMINALRECORDS = "";
    public static String PLAYER_PASSPORT_CRIMINALRECORDS = "";
    public static String PLAYER_PASSPORT_REQUEST = "";
    public static String PLAYER_PASSPORT_REQUESTOPEN = "";
    public static String PLAYER_CREATECITY = "";
    public static String PLAYERS_CREATECITY = "";
    public static String PLAYER_INVITECITY = "";
    public static String MAYOR_INVITECITY = "";
    public static String PLAYER_KICKCITY = "";
    public static String MAYOR_KICKCITY = "";
    public static String PLAYER_TRANSFERMAYOR = "";
    public static String MAYOR_TRANSFERMAYOR = "";
    public static String PLAYER_ADDPROF = "";
    public static String MAYOR_ADDPROF = "";
    public static String PLAYER_REMPROF = "";
    public static String MAYOR_REMPROF = "";
    public static String PLAYER_LEAVEINCITY = "";
    public static String PLAYERS_DISBANDCITY = "";
    public static String MAYOR_DISBANDCITI = "";
    public static String PLAYER_INVITECITYACCEPT = "";
    public static String MAYOR_INVITECITYACCEPT = "";
    public static String PLAYER_INVITECITYDENY = "";
    public static String MAYOR_INVITECITYDENY = "";
    //Ошибки
    public static String NOTPLAYER_WANTEDLIST = "";
    public static String PLAYER_NOWWANTEDLIST = "";
    public static String NOTPLAYER_REMOVEWANTEDLIST = "";
    public static String NOTPLAYER_ADDWANTEDLIST = "";
    public static String PLAYER_NOTFOUND = "";
    public static String PLAYER_NOPERMISSION = "";
    public static String JAIL_NOWCREATE = "";
    public static String JAIL_NOTCREATE = "";
    public static String CELL_NOWCREATE = "";
    public static String CELL_NOTCREATE = "";
    public static String NOTTIME = "";
    public static String JAIL_NOTCELL = "";
    public static String NOTPLAYER_IN_CELL = "";
    public static String PASSPORTOFFICERINVALIDDISTANCE = "";
    public static String ISPICKUPCITYISTRUE = "";
    public static String CITYNAMENOTVALID = "";
    public static String CITYNOWANDORE = "";
    public static String PLAYERNOWCITY = "";
    public static String PLAYERNOTCITY = "";
    public static String PROFFESSIONNOTMAYOR = "";
    public static String NOTONLINEPLAYERREQUESTCITY = "";
    public static String CITYNOWACCEPT = "";
    private final RPBase plugin;

    public GlobalConfig(RPBase plugin) {
        this.plugin = plugin;
        load();
    }

    public void load() {
        PLAYER = plugin.getConfig().getStringList("PLAYER");
        JUDGE = plugin.getConfig().getStringList("JUDGE");
        PASSPORTOFFICER = plugin.getConfig().getStringList("PASSPORTOFFICER");
        MAYOR = plugin.getConfig().getStringList("MAYOR");
        POLICEMAN = plugin.getConfig().getStringList("POLICEMAN");
        OFFICER = plugin.getConfig().getStringList("OFFICER");
        CARETAKER = plugin.getConfig().getStringList("CARETAKER");
        DETECTIVE = plugin.getConfig().getStringList("DETECTIVE");
        INVENTOR = plugin.getConfig().getStringList("INVENTOR");
        BLACKSMITH = plugin.getConfig().getStringList("BLACKSMITH");
        WIZARD = plugin.getConfig().getStringList("WIZARD");
        COOK = plugin.getConfig().getStringList("COOK");
        BREWER = plugin.getConfig().getStringList("BREWER");
        THIEF = plugin.getConfig().getStringList("THIEF");

        MAX_PLAYER_IN_CELL = plugin.getConfig().getInt("maxplayerincell");

        NAMEPASSPORTGUI = plugin.getConfig().getString("namePassportGUI");
        if (NAMEPASSPORTGUI != null) {
            NAMEPASSPORTGUI = ChatColor.translateAlternateColorCodes('&', NAMEPASSPORTGUI);
        }
        NAMEPASSPORT = plugin.getConfig().getString("namePassport");
        if (NAMEPASSPORT != null) {
            NAMEPASSPORT = ChatColor.translateAlternateColorCodes('&', NAMEPASSPORT);
        }
        LOREPASSPORT = plugin.getConfig().getStringList("lorePassport");
        List<String> modifLore = new ArrayList<>();
        for (String lore : LOREPASSPORT) {
            lore = ChatColor.translateAlternateColorCodes('&', lore);
            modifLore.add(lore);
        }
        LOREPASSPORT = modifLore;

        CITYINFOGUINAME = plugin.getConfig().getString("cityInfoGUIName");
        if (CITYINFOGUINAME != null) {
            CITYINFOGUINAME = ChatColor.translateAlternateColorCodes('&', CITYINFOGUINAME);
        }
        CITYINFOBOOKNAME = plugin.getConfig().getString("cityInfoBookName");
        if (CITYINFOBOOKNAME != null) {
            CITYINFOBOOKNAME = ChatColor.translateAlternateColorCodes('&', CITYINFOBOOKNAME);
        }
        LORECITYINFO = plugin.getConfig().getStringList("loreCityInfo");
        List<String> modifLore1 = new ArrayList<>();
        for (String lore : LORECITYINFO) {
            lore = ChatColor.translateAlternateColorCodes('&', lore);
            modifLore1.add(lore);
        }
        LORECITYINFO = modifLore1;

        CITIZENSCITYGUINAME = plugin.getConfig().getString("citizensCityGUIName");
        if (CITIZENSCITYGUINAME != null) {
            CITIZENSCITYGUINAME = ChatColor.translateAlternateColorCodes('&', CITIZENSCITYGUINAME);
        }
        CITIZENSCITYSKULLNAME = plugin.getConfig().getString("citizensCitySkullName");
        if (CITIZENSCITYSKULLNAME != null) {
            CITIZENSCITYSKULLNAME = ChatColor.translateAlternateColorCodes('&', CITIZENSCITYSKULLNAME);
        }
        LORESKULLCITIZEN = plugin.getConfig().getStringList("loreSkullCityzen");
        List<String> modifLore2 = new ArrayList<>();
        for (String lore : LORESKULLCITIZEN) {
            lore = ChatColor.translateAlternateColorCodes('&', lore);
            modifLore2.add(lore);
        }
        LORESKULLCITIZEN = modifLore2;

        POLICE_WANTED_REMOVE = plugin.getConfig().getString("police.wantedlist.remove");
        if (POLICE_WANTED_REMOVE != null) {
            POLICE_WANTED_REMOVE = ChatColor.translateAlternateColorCodes('&', POLICE_WANTED_REMOVE);
        }
        PLAYER_WANTED_REMOVE = plugin.getConfig().getString("player.wantedlist.remove");
        if (PLAYER_WANTED_REMOVE != null) {
            PLAYER_WANTED_REMOVE = ChatColor.translateAlternateColorCodes('&', PLAYER_WANTED_REMOVE);
        }
        POLICE_WANTEDLIST = plugin.getConfig().getString("police.wantedlist.list");
        if (POLICE_WANTEDLIST != null) {
            POLICE_WANTEDLIST = ChatColor.translateAlternateColorCodes('&', POLICE_WANTEDLIST);
        }
        POLICE_WANTEDLIST_NICK = plugin.getConfig().getString("police.wantedlist.nick");
        if (POLICE_WANTEDLIST_NICK != null) {
            POLICE_WANTEDLIST_NICK = ChatColor.translateAlternateColorCodes('&', POLICE_WANTEDLIST_NICK);
        }
        POLICE_WANTEDLIST_ADD = plugin.getConfig().getString("police.wantedlist.add");
        if (POLICE_WANTEDLIST_ADD != null) {
            POLICE_WANTEDLIST_ADD = ChatColor.translateAlternateColorCodes('&', POLICE_WANTEDLIST_ADD);
        }
        PLAYER_WANTEDLIST_ADD = plugin.getConfig().getString("player.wantedlist.add");
        if (PLAYER_WANTEDLIST_ADD != null) {
            PLAYER_WANTEDLIST_ADD = ChatColor.translateAlternateColorCodes('&', PLAYER_WANTEDLIST_ADD);
        }

        POLICE_JAIL_CREATE = plugin.getConfig().getString("police.jail.create");
        if (POLICE_JAIL_CREATE != null) {
            POLICE_JAIL_CREATE = ChatColor.translateAlternateColorCodes('&', POLICE_JAIL_CREATE);
        }
        POLICE_JAIL_REMOVE = plugin.getConfig().getString("police.jail.remove");
        if (POLICE_JAIL_REMOVE != null) {
            POLICE_JAIL_REMOVE = ChatColor.translateAlternateColorCodes('&', POLICE_JAIL_REMOVE);
        }
        POLICE_JAIL_LIST = plugin.getConfig().getString("police.jaillist.list");
        if (POLICE_JAIL_LIST != null) {
            POLICE_JAIL_LIST = ChatColor.translateAlternateColorCodes('&', POLICE_JAIL_LIST);
        }
        POLICE_JAIL_LIST_NAME = plugin.getConfig().getString("police.jail.list.name");
        if (POLICE_JAIL_LIST_NAME != null) {
            POLICE_JAIL_LIST_NAME = ChatColor.translateAlternateColorCodes('&', POLICE_JAIL_LIST_NAME);
        }

        POLICE_CELL_CREATE = plugin.getConfig().getString("police.cell.spawn");
        if (POLICE_CELL_CREATE != null) {
            POLICE_CELL_CREATE = ChatColor.translateAlternateColorCodes('&', POLICE_CELL_CREATE);
        }
        POLICE_CELL_REMOVE = plugin.getConfig().getString("police.cell.remove");
        if (POLICE_CELL_REMOVE != null) {
            POLICE_CELL_REMOVE = ChatColor.translateAlternateColorCodes('&', POLICE_CELL_REMOVE);
        }
        POLICE_CELL_LIST = plugin.getConfig().getString("police.celllist.list");
        if (POLICE_CELL_LIST != null) {
            POLICE_CELL_LIST = ChatColor.translateAlternateColorCodes('&', POLICE_CELL_LIST);
        }
        POLICE_CELL_LIST_NAME = plugin.getConfig().getString("police.cell.list.name");
        if (POLICE_CELL_LIST_NAME != null) {
            POLICE_CELL_LIST_NAME = ChatColor.translateAlternateColorCodes('&', POLICE_CELL_LIST_NAME);
        }

        POLICE_CELL_ADDPLAYER = plugin.getConfig().getString("police.cell.addPlayer");
        if (POLICE_CELL_ADDPLAYER != null) {
            POLICE_CELL_ADDPLAYER = ChatColor.translateAlternateColorCodes('&', POLICE_CELL_ADDPLAYER);
        }
        PLAYER_CELL_ADD = plugin.getConfig().getString("player.cell.add");
        if (PLAYER_CELL_ADD != null) {
            PLAYER_CELL_ADD = ChatColor.translateAlternateColorCodes('&', PLAYER_CELL_ADD);
        }

        PASSPORTOFFICER_PLACEEDIT = plugin.getConfig().getString("passportofficer.placeEdit");
        if (PASSPORTOFFICER_PLACEEDIT != null) {
            PASSPORTOFFICER_PLACEEDIT = ChatColor.translateAlternateColorCodes('&', PASSPORTOFFICER_PLACEEDIT);
        }
        PLAYER_PASSPORT_PLACEEDIT = plugin.getConfig().getString("player.passport.placeEdit");
        if (PLAYER_PASSPORT_PLACEEDIT != null) {
            PLAYER_PASSPORT_PLACEEDIT = ChatColor.translateAlternateColorCodes('&', PLAYER_PASSPORT_PLACEEDIT);
        }

        PASSPORTOFFICER_PICKUPCITY = plugin.getConfig().getString("passportofficer.pickUpCity");
        if (PASSPORTOFFICER_PICKUPCITY != null) {
            PASSPORTOFFICER_PICKUPCITY = ChatColor.translateAlternateColorCodes('&', PASSPORTOFFICER_PICKUPCITY);
        }
        PLAYER_PASSPORT_PICKUPCITY = plugin.getConfig().getString("player.passport.pickUpCity");
        if (PLAYER_PASSPORT_PICKUPCITY != null) {
            PLAYER_PASSPORT_PICKUPCITY = ChatColor.translateAlternateColorCodes('&', PLAYER_PASSPORT_PICKUPCITY);
        }

        JUDGE_CRIMINALRECORDS = plugin.getConfig().getString("judge.criminalsRercords");
        if (JUDGE_CRIMINALRECORDS != null) {
            JUDGE_CRIMINALRECORDS = ChatColor.translateAlternateColorCodes('&', JUDGE_CRIMINALRECORDS);
        }
        PLAYER_PASSPORT_CRIMINALRECORDS = plugin.getConfig().getString("player.passport.criminalsRercords");
        if (PLAYER_PASSPORT_CRIMINALRECORDS != null) {
            PLAYER_PASSPORT_CRIMINALRECORDS = ChatColor.translateAlternateColorCodes('&', PLAYER_PASSPORT_CRIMINALRECORDS);
        }

        PLAYER_PASSPORT_REQUEST = plugin.getConfig().getString("player.passport.request");
        if (PLAYER_PASSPORT_REQUEST != null) {
            PLAYER_PASSPORT_REQUEST = ChatColor.translateAlternateColorCodes('&', PLAYER_PASSPORT_REQUEST);
        }
        PLAYER_PASSPORT_REQUESTOPEN = plugin.getConfig().getString("player.passport.requestOpen");
        if (PLAYER_PASSPORT_REQUESTOPEN != null) {
            PLAYER_PASSPORT_REQUESTOPEN = ChatColor.translateAlternateColorCodes('&', PLAYER_PASSPORT_REQUESTOPEN);
        }

        PLAYER_CREATECITY = plugin.getConfig().getString("player.createCity");
        if (PLAYER_CREATECITY != null) {
            PLAYER_CREATECITY = ChatColor.translateAlternateColorCodes('&', PLAYER_CREATECITY);
        }
        PLAYERS_CREATECITY = plugin.getConfig().getString("players.createCity");
        if (PLAYERS_CREATECITY != null) {
            PLAYERS_CREATECITY = ChatColor.translateAlternateColorCodes('&', PLAYERS_CREATECITY);
        }

        PLAYER_INVITECITY = plugin.getConfig().getString("player.inviteCity");
        if (PLAYER_INVITECITY != null) {
            PLAYER_INVITECITY = ChatColor.translateAlternateColorCodes('&', PLAYER_INVITECITY);
        }
        MAYOR_INVITECITY = plugin.getConfig().getString("mayor.inviteCity");
        if (MAYOR_INVITECITY != null) {
            MAYOR_INVITECITY = ChatColor.translateAlternateColorCodes('&', MAYOR_INVITECITY);
        }

        PLAYER_KICKCITY = plugin.getConfig().getString("player.kickCity");
        if (PLAYER_KICKCITY != null) {
            PLAYER_KICKCITY = ChatColor.translateAlternateColorCodes('&', PLAYER_KICKCITY);
        }
        MAYOR_KICKCITY = plugin.getConfig().getString("mayor.kickCity");
        if (MAYOR_KICKCITY != null) {
            MAYOR_KICKCITY = ChatColor.translateAlternateColorCodes('&', MAYOR_KICKCITY);
        }

        PLAYER_TRANSFERMAYOR = plugin.getConfig().getString("player.transferMayor");
        if (PLAYER_TRANSFERMAYOR != null) {
            PLAYER_TRANSFERMAYOR = ChatColor.translateAlternateColorCodes('&', PLAYER_TRANSFERMAYOR);
        }
        MAYOR_TRANSFERMAYOR = plugin.getConfig().getString("mayor.transferMayor");
        if (MAYOR_TRANSFERMAYOR != null) {
            MAYOR_TRANSFERMAYOR = ChatColor.translateAlternateColorCodes('&', MAYOR_TRANSFERMAYOR);
        }

        PLAYER_ADDPROF = plugin.getConfig().getString("player.addProf");
        if (PLAYER_ADDPROF != null) {
            PLAYER_ADDPROF = ChatColor.translateAlternateColorCodes('&', PLAYER_ADDPROF);
        }
        MAYOR_ADDPROF = plugin.getConfig().getString("mayor.addProf");
        if (MAYOR_ADDPROF != null) {
            MAYOR_ADDPROF = ChatColor.translateAlternateColorCodes('&', MAYOR_ADDPROF);
        }

        PLAYER_REMPROF = plugin.getConfig().getString("player.remProf");
        if (PLAYER_REMPROF != null) {
            PLAYER_REMPROF = ChatColor.translateAlternateColorCodes('&', PLAYER_REMPROF);
        }
        MAYOR_REMPROF = plugin.getConfig().getString("mayor.remProf");
        if (MAYOR_REMPROF != null) {
            MAYOR_REMPROF = ChatColor.translateAlternateColorCodes('&', MAYOR_REMPROF);
        }

        PLAYER_LEAVEINCITY = plugin.getConfig().getString("player.leaveInCity");
        if (PLAYER_LEAVEINCITY != null) {
            PLAYER_LEAVEINCITY = ChatColor.translateAlternateColorCodes('&', PLAYER_LEAVEINCITY);
        }

        PLAYERS_DISBANDCITY = plugin.getConfig().getString("players.disbandCity");
        if (PLAYERS_DISBANDCITY != null) {
            PLAYERS_DISBANDCITY = ChatColor.translateAlternateColorCodes('&', PLAYERS_DISBANDCITY);
        }
        MAYOR_DISBANDCITI = plugin.getConfig().getString("mayor.disbandCity");
        if (MAYOR_DISBANDCITI != null) {
            MAYOR_DISBANDCITI = ChatColor.translateAlternateColorCodes('&', MAYOR_DISBANDCITI);
        }

        PLAYER_INVITECITYACCEPT = plugin.getConfig().getString("player.inviteCityAccept");
        if (PLAYER_INVITECITYACCEPT != null) {
            PLAYER_INVITECITYACCEPT = ChatColor.translateAlternateColorCodes('&', PLAYER_INVITECITYACCEPT);
        }
        MAYOR_INVITECITYACCEPT = plugin.getConfig().getString("mayor.inviteCityAccept");
        if (MAYOR_INVITECITYACCEPT != null) {
            MAYOR_INVITECITYACCEPT = ChatColor.translateAlternateColorCodes('&', MAYOR_INVITECITYACCEPT);
        }


        PLAYER_INVITECITYDENY = plugin.getConfig().getString("player.inviteCityDeny");
        if (PLAYER_INVITECITYDENY != null) {
            PLAYER_INVITECITYDENY = ChatColor.translateAlternateColorCodes('&', PLAYER_INVITECITYDENY);
        }
        MAYOR_INVITECITYDENY = plugin.getConfig().getString("mayor.inviteCityDeny");
        if (MAYOR_INVITECITYDENY != null) {
            MAYOR_INVITECITYDENY = ChatColor.translateAlternateColorCodes('&', MAYOR_INVITECITYDENY);
        }

        //Ошибки
        NOTPLAYER_WANTEDLIST = plugin.getConfig().getString("notplayer.wantedlist");
        if (NOTPLAYER_WANTEDLIST != null) {
            NOTPLAYER_WANTEDLIST = ChatColor.translateAlternateColorCodes('&', NOTPLAYER_WANTEDLIST);
        }
        PLAYER_NOWWANTEDLIST = plugin.getConfig().getString("player.nowwantedlist");
        if (PLAYER_NOWWANTEDLIST != null) {
            PLAYER_NOWWANTEDLIST = ChatColor.translateAlternateColorCodes('&', PLAYER_NOWWANTEDLIST);
        }
        NOTPLAYER_REMOVEWANTEDLIST = plugin.getConfig().getString("notplayer.removewantedlist");
        if (NOTPLAYER_REMOVEWANTEDLIST != null) {
            NOTPLAYER_REMOVEWANTEDLIST = ChatColor.translateAlternateColorCodes('&', NOTPLAYER_REMOVEWANTEDLIST);
        }
        NOTPLAYER_ADDWANTEDLIST = plugin.getConfig().getString("notplayer.addwantedlist");
        if (NOTPLAYER_ADDWANTEDLIST != null) {
            NOTPLAYER_ADDWANTEDLIST = ChatColor.translateAlternateColorCodes('&', NOTPLAYER_ADDWANTEDLIST);
        }
        PLAYER_NOTFOUND = plugin.getConfig().getString("player.notfound");
        if (PLAYER_NOTFOUND != null) {
            PLAYER_NOTFOUND = ChatColor.translateAlternateColorCodes('&', PLAYER_NOTFOUND);
        }
        PLAYER_NOPERMISSION = plugin.getConfig().getString("player.notpermission");
        if (PLAYER_NOPERMISSION != null) {
            PLAYER_NOPERMISSION = ChatColor.translateAlternateColorCodes('&', PLAYER_NOPERMISSION);
        }
        JAIL_NOWCREATE = plugin.getConfig().getString("jail.nowcreate");
        if (JAIL_NOWCREATE != null) {
            JAIL_NOWCREATE = ChatColor.translateAlternateColorCodes('&', JAIL_NOWCREATE);
        }
        JAIL_NOTCREATE = plugin.getConfig().getString("jail.notcreate");
        if (JAIL_NOTCREATE != null) {
            JAIL_NOTCREATE = ChatColor.translateAlternateColorCodes('&', JAIL_NOTCREATE);
        }
        CELL_NOWCREATE = plugin.getConfig().getString("cell.nowcreate");
        if (CELL_NOWCREATE != null) {
            CELL_NOWCREATE = ChatColor.translateAlternateColorCodes('&', CELL_NOWCREATE);
        }
        CELL_NOTCREATE = plugin.getConfig().getString("cell.notcreate");
        if (CELL_NOTCREATE != null) {
            CELL_NOTCREATE = ChatColor.translateAlternateColorCodes('&', CELL_NOTCREATE);
        }
        NOTTIME = plugin.getConfig().getString("nottime");
        if (NOTTIME != null) {
            NOTTIME = ChatColor.translateAlternateColorCodes('&', NOTTIME);
        }
        JAIL_NOTCELL = plugin.getConfig().getString("jail.notcell");
        if (JAIL_NOTCELL != null) {
            JAIL_NOTCELL = ChatColor.translateAlternateColorCodes('&', JAIL_NOTCELL);
        }
        NOTPLAYER_IN_CELL = plugin.getConfig().getString("notplayer.addcell");
        if (NOTPLAYER_IN_CELL != null) {
            NOTPLAYER_IN_CELL = ChatColor.translateAlternateColorCodes('&', NOTPLAYER_IN_CELL);
        }
        PASSPORTOFFICERINVALIDDISTANCE = plugin.getConfig().getString("passportOfficeInvalidDistance");
        if (PASSPORTOFFICERINVALIDDISTANCE != null) {
            PASSPORTOFFICERINVALIDDISTANCE = ChatColor.translateAlternateColorCodes('&', PASSPORTOFFICERINVALIDDISTANCE);
        }
        ISPICKUPCITYISTRUE = plugin.getConfig().getString("isPickUpCityIsTrue");
        if (ISPICKUPCITYISTRUE != null) {
            ISPICKUPCITYISTRUE = ChatColor.translateAlternateColorCodes('&', ISPICKUPCITYISTRUE);
        }
        CITYNAMENOTVALID = plugin.getConfig().getString("cityNameNotValid");
        if (CITYNAMENOTVALID != null) {
            CITYNAMENOTVALID = ChatColor.translateAlternateColorCodes('&', CITYNAMENOTVALID);
        }
        CITYNOWANDORE = plugin.getConfig().getString("cityNowAndNotOre");
        if (CITYNOWANDORE != null) {
            CITYNOWANDORE = ChatColor.translateAlternateColorCodes('&', CITYNOWANDORE);
        }
        PLAYERNOWCITY = plugin.getConfig().getString("playerNowCity");
        if (PLAYERNOWCITY != null) {
            PLAYERNOWCITY = ChatColor.translateAlternateColorCodes('&', PLAYERNOWCITY);
        }
        PLAYERNOTCITY = plugin.getConfig().getString("playerNotCity");
        if (PLAYERNOTCITY != null) {
            PLAYERNOTCITY = ChatColor.translateAlternateColorCodes('&', PLAYERNOTCITY);
        }
        PROFFESSIONNOTMAYOR = plugin.getConfig().getString("proffessionNotMayor");
        if (PROFFESSIONNOTMAYOR != null) {
            PROFFESSIONNOTMAYOR = ChatColor.translateAlternateColorCodes('&', PROFFESSIONNOTMAYOR);
        }
        NOTONLINEPLAYERREQUESTCITY = plugin.getConfig().getString("notOnlinePlayerRequestCity");
        if (NOTONLINEPLAYERREQUESTCITY != null) {
            NOTONLINEPLAYERREQUESTCITY = ChatColor.translateAlternateColorCodes('&', NOTONLINEPLAYERREQUESTCITY);
        }
        CITYNOWACCEPT = plugin.getConfig().getString("cityNowAccept");
        if (CITYNOWACCEPT != null) {
            CITYNOWACCEPT = ChatColor.translateAlternateColorCodes('&', CITYNOWACCEPT);
        }
    }
}
