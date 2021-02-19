package ru.fotontv.rpbase.utils;

import com.google.common.collect.Lists;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import org.apache.commons.lang.Validate;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import javax.annotation.Nonnull;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.reflect.Field;
import java.util.Base64;
import java.util.UUID;

public class SkullUtils {
    protected static final MethodHandle GAME_PROFILE;

    static {
        MethodHandles.Lookup lookup = MethodHandles.lookup();
        MethodHandle gameProfile = null;
        try {
            Class<?> craftSkull = ReflectionUtils.getCraftClass("inventory.CraftMetaSkull");
            Field profileField;
            if (craftSkull != null) {
                profileField = craftSkull.getDeclaredField("profile");
                profileField.setAccessible(true);
                gameProfile = lookup.unreflectSetter(profileField);
            }
        } catch (NoSuchFieldException|IllegalAccessException e) {
            e.printStackTrace();
        }
        GAME_PROFILE = gameProfile;
    }

    @SuppressWarnings("deprecation")
    @Nonnull
    public static SkullMeta applySkin(@Nonnull ItemMeta head, @Nonnull String identifier) {
        SkullMeta meta = (SkullMeta)head;
        if (isUsername(identifier))
            return applySkin(head, Bukkit.getOfflinePlayer(identifier));
        if (identifier.contains("textures.minecraft.net"))
            return getValueFromTextures(meta, identifier);
        if (identifier.length() > 100 && isBase64(identifier))
            return getSkullByValue(meta, identifier);
        return getTexturesFromUrlValue(meta, identifier);
    }

    @Nonnull
    private static SkullMeta getTexturesFromUrlValue(@Nonnull SkullMeta head, @Nonnull String urlValue) {
        return getValueFromTextures(head, "https://textures.minecraft.net/texture/" + urlValue);
    }

    @Nonnull
    private static SkullMeta getValueFromTextures(@Nonnull SkullMeta head, @Nonnull String url) {
        return getSkullByValue(head, encodeBase64("{\"textures\":{\"SKIN\":{\"url\":\"" + url + "\"}}}"));
    }

    @Nonnull
    private static String encodeBase64(@Nonnull String str) {
        return Base64.getEncoder().encodeToString(str.getBytes());
    }


    @Nonnull
    private static SkullMeta getSkullByValue(@Nonnull SkullMeta head, @Nonnull String value) {
        Validate.notEmpty(value, "Skull value cannot be null or empty");
        GameProfile profile = new GameProfile(UUID.randomUUID(), null);
        profile.getProperties().put("textures", new Property("textures", value));
        try {
            GAME_PROFILE.invoke(head, profile);
        } catch (Throwable ex) {
            ex.printStackTrace();
        }
        return head;
    }

    private static boolean isBase64(@Nonnull String base64) {
        try {
            Base64.getDecoder().decode(base64);
            return true;
        } catch (IllegalArgumentException ignored) {
            return false;
        }
    }

    @Nonnull
    public static SkullMeta applySkin(@Nonnull ItemMeta head, @Nonnull OfflinePlayer identifier) {
        SkullMeta meta = (SkullMeta)head;
        meta.setOwningPlayer(identifier);
        return meta;
    }

    private static boolean isUsername(@Nonnull String name) {
        int len = name.length();
        if (len < 3 || len > 16)
            return false;
        for (char ch : Lists.charactersOf(name)) {
            if (ch != '_' && (ch < 'A' || ch > 'Z') && (ch < 'a' || ch > 'z') && (ch < '0' || ch > '9'))
                return false;
        }
        return true;
    }
}
