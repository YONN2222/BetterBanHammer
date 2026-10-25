package com.yonn2222.betterbanhammer.platform;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

final class RegionScheduler implements Scheduler {
    private final JavaPlugin plugin;

    RegionScheduler(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public void global(Runnable action) {
        Bukkit.getGlobalRegionScheduler().run(plugin, task -> action.run());
    }

    @Override
    public void globalLater(Runnable action, long delayTicks) {
        Bukkit.getGlobalRegionScheduler().runDelayed(plugin, task -> action.run(), Math.max(1, delayTicks));
    }

    @Override
    public void region(Location location, Runnable action) {
        Bukkit.getRegionScheduler().run(plugin, location, task -> action.run());
    }

    @Override
    public void player(Player player, Runnable action, long delayTicks) {
        if (delayTicks <= 0) {
            player.getScheduler().run(plugin, task -> action.run(), null);
        } else {
            player.getScheduler().runDelayed(plugin, task -> action.run(), null, delayTicks);
        }
    }
}
