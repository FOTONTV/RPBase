package ru.fotontv.rpbase.config;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Keyed;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.inventory.Recipe;
import ru.fotontv.rpbase.RPBase;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.*;

public class ChatConfig {
    private final RPBase plugin;

    public static short noRpLocalChatDistance;
    public static String noRpGlobalChatFormat = "";
    public static String noRpLocalChatFormat = "";
    public static String msgNotWhitelisted = "";
    public static String globalChatFormatDetector = "";
    public static String doChatUsage = "";
    public static Integer doChatRadius = 0;
    public static String doChatFormat = "";
    public static String meChatUsage = "";
    public static Integer meChatRadius = 0;
    public static String meChatFormat = "";
    public static String tryChatUsage = "";
    public static Integer tryChatRadius = 0;
    public static String tryChatFormat = "";
    public static List<String> tryResults = new ArrayList<>();

    public ChatConfig(RPBase plugin) {
        this.plugin = plugin;
        load();
    }

    public void load() {
        FileConfiguration fileConfiguration = plugin.getConfig();
        ConfigurationSection chatConfig = fileConfiguration.getConfigurationSection("chat");
        if (chatConfig != null) {
            noRpLocalChatDistance = (short)chatConfig.getInt("noRpLocalChatDistance");
            noRpGlobalChatFormat = colorize(chatConfig.getString("noRpGlobalChatFormat"));
            noRpLocalChatFormat = colorize(chatConfig.getString("noRpLocalChatFormat"));
            globalChatFormatDetector = colorize(chatConfig.getString("globalChatFormatDetector"));
            doChatUsage = chatConfig.getString("do.usage");
            doChatRadius = chatConfig.getInt("do.radius");
            doChatFormat = chatConfig.getString("do.format");
            meChatUsage = chatConfig.getString("me.usage");
            meChatRadius = chatConfig.getInt("me.radius");
            meChatFormat = chatConfig.getString("me.format");
            tryChatUsage = chatConfig.getString("try.usage");
            tryChatRadius = chatConfig.getInt("try.radius");
            tryChatFormat = chatConfig.getString("try.format");
            tryResults = chatConfig.getStringList("results");
        }
        ConfigurationSection whitelistConfig = fileConfiguration.getConfigurationSection("whitelist");
        if (whitelistConfig != null) {
            msgNotWhitelisted = colorize(whitelistConfig.getString("msgNotWhitelisted"));
        }
        Set<Material> removedRecipesMaterial = new HashSet<>();
        for (String materialName : fileConfiguration.getStringList("removeRecipes")) {
            Material material = Material.matchMaterial(materialName.toUpperCase().replace(" ", "_"));
            if (material == null) {
                plugin.getLogger().warning("Неизвестный материал:" + materialName);
                continue;
            }
            removedRecipesMaterial.add(material);
        }
        int removedRecipesCount = 0;
        Iterator<Recipe> recipeIterator = Bukkit.getServer().recipeIterator();
        while (recipeIterator.hasNext()) {
            Recipe recipe = recipeIterator.next();
            if (removedRecipesMaterial.contains(recipe.getResult().getType()) && recipe instanceof Keyed && ((Keyed)recipe).getKey().getNamespace().equals("minecraft")) {
                recipeIterator.remove();
                removedRecipesCount++;
            }
        }
        plugin.getLogger().info("Удалено рецептов:" + removedRecipesCount);
    }

    @Nonnull
    private static String colorize(@Nullable String input) {
        if (input == null)
            throw new NullPointerException();
        return ChatColor.translateAlternateColorCodes('&', input);
    }
}
