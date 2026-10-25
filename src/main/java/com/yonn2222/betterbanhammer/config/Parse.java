package com.yonn2222.betterbanhammer.config;

import org.bukkit.Color;
import org.bukkit.Keyed;
import org.bukkit.NamespacedKey;
import org.bukkit.Registry;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.logging.Logger;

final class Parse {
    private Parse() {
    }

    static ConfigurationSection section(ConfigurationSection parent, String name) {
        ConfigurationSection found = parent.getConfigurationSection(name);
        return found != null ? found : new YamlConfiguration();
    }

    static NamespacedKey key(String input) {
        String text = input.trim().toLowerCase(Locale.ROOT);
        return text.contains(":") ? NamespacedKey.fromString(text) : NamespacedKey.minecraft(text);
    }

    static List<Color> colors(List<String> raw, Logger log, String path) {
        List<Color> out = new ArrayList<>();
        for (String entry : raw) {
            String hex = entry.trim().replaceFirst("^#", "");
            try {
                if (hex.length() != 6) {
                    throw new IllegalArgumentException(hex);
                }
                out.add(Color.fromRGB(Integer.parseInt(hex, 16)));
            } catch (IllegalArgumentException e) {
                log.warning("Ignoring invalid color '" + entry + "' in " + path + ", expected a hex color like #FFAA00");
            }
        }
        return out;
    }

    static <T extends Keyed> T lookup(Registry<T> registry, String name) {
        NamespacedKey key = key(name);
        return key == null ? null : registry.get(key);
    }

    static double number(Object value, double fallback) {
        return value instanceof Number number ? number.doubleValue() : fallback;
    }
}
