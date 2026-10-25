package com.yonn2222.betterbanhammer.metrics;

import com.yonn2222.betterbanhammer.BetterBanHammer;
import org.bstats.bukkit.Metrics;
import org.bstats.charts.SimplePie;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public final class Stats {
    private Stats() {
    }

    public static void start(BetterBanHammer plugin) {
        int id = pluginId(plugin);
        if (id <= 0) {
            return;
        }
        Metrics metrics = new Metrics(plugin, id);
        metrics.addCustomChart(new SimplePie("language", () -> plugin.settings().language()));
        metrics.addCustomChart(new SimplePie("ban_backend", () -> plugin.bans().backend()));
    }

    private static int pluginId(BetterBanHammer plugin) {
        Properties properties = new Properties();
        try (InputStream in = plugin.getResource("metrics.properties")) {
            if (in == null) {
                return 0;
            }
            properties.load(in);
        } catch (IOException e) {
            return 0;
        }
        try {
            return Integer.parseInt(properties.getProperty("bstats-id", "0").trim());
        } catch (NumberFormatException e) {
            return 0;
        }
    }
}
