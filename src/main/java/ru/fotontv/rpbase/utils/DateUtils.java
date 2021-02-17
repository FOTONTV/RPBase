package ru.fotontv.rpbase.utils;

import javax.annotation.Nonnull;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DateUtils {
    private static SimpleDateFormat staticDateTimeFormat = new SimpleDateFormat("dd.MM.yyyy hh:mm");

    private static final Pattern offsetPattern = Pattern.compile("\\d+[A-z]+");

    private static Map<String, Long> offsetsMills = new HashMap<>();

    public static long millsFromStatic(String input) {
        if (input.equals("*"))
            return -1L;
        try {
            return staticDateTimeFormat.parse(input).getTime();
        } catch (ParseException ex) {
            throw new RuntimeException(ex);
        }
    }

    public static String staticFromMills(long input) {
        return (input == -1L) ? "*" : staticDateTimeFormat.format(new Date(input));
    }

    public static long millsFromOffset(String input) {
        if (input.equals("*"))
            return -1L;
        long result = System.currentTimeMillis();
        Matcher m = offsetPattern.matcher(input);
        if (m.find())
            result += millsFromOffsetPart(m.group());
        return result;
    }

    private static long millsFromOffsetPart(@Nonnull String input) {
        String type = input.replaceFirst("\\d+", "");
        long value = Long.parseLong(input.substring(0, input.length() - type.length()));
        Long multiplier = offsetsMills.get(type);
        if (multiplier == null)
            throw new IllegalArgumentException("Unknown time offset \"" + input + "\"");
        return value * multiplier;
    }

    static {
        offsetsMills.put("m", 60000L);
        offsetsMills.put("h", 3600000L);
        offsetsMills.put("d", 86400000L);
        offsetsMills.put("w", 604800000L);
        offsetsMills.put("M", 2592000000L);
        offsetsMills.put("y", 31536000000L);
    }
}
