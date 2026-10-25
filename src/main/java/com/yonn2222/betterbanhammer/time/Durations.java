package com.yonn2222.betterbanhammer.time;

import java.util.Locale;
import java.util.OptionalLong;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class Durations {
    public static final long PERMANENT = 0;

    private static final Set<String> PERMANENT_WORDS = Set.of("permanent", "perm", "forever", "never");
    private static final Pattern WHOLE = Pattern.compile("(?:\\d{1,6}[a-z]+)+");
    private static final Pattern PART = Pattern.compile("(\\d{1,6})([a-z]+)");

    private Durations() {
    }

    public static OptionalLong parse(String input) {
        if (input == null) {
            return OptionalLong.empty();
        }
        String text = input.toLowerCase(Locale.ROOT).trim();
        if (PERMANENT_WORDS.contains(text)) {
            return OptionalLong.of(PERMANENT);
        }
        if (!WHOLE.matcher(text).matches()) {
            return OptionalLong.empty();
        }
        Matcher matcher = PART.matcher(text);
        long total = 0;
        while (matcher.find()) {
            long factor = factorOf(matcher.group(2));
            if (factor == 0) {
                return OptionalLong.empty();
            }
            total += Long.parseLong(matcher.group(1)) * factor;
        }
        return total > 0 ? OptionalLong.of(total) : OptionalLong.empty();
    }

    private static long factorOf(String unit) {
        return switch (unit) {
            case "s", "sec", "secs", "second", "seconds" -> 1;
            case "m", "min", "mins", "minute", "minutes" -> 60;
            case "h", "hr", "hrs", "hour", "hours" -> 3600;
            case "d", "day", "days" -> 86400;
            case "w", "week", "weeks" -> 604800;
            case "mo", "month", "months" -> 2592000;
            case "y", "yr", "yrs", "year", "years" -> 31536000;
            default -> 0;
        };
    }

    public static String compact(long seconds) {
        long safe = Math.max(0, seconds);
        if (safe == 0) {
            return "0s";
        }
        StringBuilder out = new StringBuilder();
        append(out, safe / 86400, "d", " ");
        append(out, safe % 86400 / 3600, "h", " ");
        append(out, safe % 3600 / 60, "m", " ");
        append(out, safe % 60, "s", " ");
        return out.toString();
    }

    public static String joined(long seconds) {
        return compact(seconds).replace(" ", "");
    }

    private static void append(StringBuilder out, long value, String unit, String separator) {
        if (value == 0) {
            return;
        }
        if (!out.isEmpty()) {
            out.append(separator);
        }
        out.append(value).append(unit);
    }
}
