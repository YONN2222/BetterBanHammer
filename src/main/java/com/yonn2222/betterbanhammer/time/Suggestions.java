package com.yonn2222.betterbanhammer.time;

import java.util.List;
import java.util.Locale;

public final class Suggestions {
    private Suggestions() {
    }

    public static List<String> filter(List<String> options, String typed) {
        String prefix = typed.toLowerCase(Locale.ROOT);
        return options.stream()
                .filter(option -> option.toLowerCase(Locale.ROOT).startsWith(prefix))
                .toList();
    }
}
