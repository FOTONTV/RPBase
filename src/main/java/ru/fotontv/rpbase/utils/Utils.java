package ru.fotontv.rpbase.utils;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import ru.fotontv.rpbase.config.ChatConfig;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class Utils {
    private static final Random RANDOM = new Random();

    private static final List<String> RESULTS = ChatConfig.tryResults;

    public static String extractMessage(String[] args, int start) {
        return String.join(" ", Arrays.copyOfRange((CharSequence[])args, start, args.length));
    }

    public static void sendMessage(Player player, int radius, String message) {
        if (radius > -1) {
            player.getWorld().getPlayers().stream()
                    .filter(p -> (player.getLocation().distanceSquared(p.getLocation()) <= Math.pow(radius, 2.0D)))
                    .forEach(p -> p.sendMessage(message));
            return;
        }
        Bukkit.getOnlinePlayers().forEach(p -> p.sendMessage(message));
    }

    public static String randomResult() {
        return RESULTS.get(RANDOM.nextInt(RESULTS.size()));
    }
}
