package com.yonn2222.betterbanhammer.platform;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

final class ClassicScheduler implements Scheduler {
    private final JavaPlugin plugin;

    ClassicScheduler(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public void global(Runnable action) {
        Bukkit.getScheduler().runTask(plugin, action);
    }

    @Override
    public void globalLater(Runnable action, long delayTicks) {
        Bukkit.getScheduler().runTaskLater(plugin, action, Math.max(1, delayTicks));
    }

    @Override
    public void region(Location location, Runnable action) {
        Bukkit.getScheduler().runTask(plugin, action);
    }

    @Override
    public void player(Player player, Runnable action, long delayTicks) {
        if (delayTicks <= 0) {
            Bukkit.getScheduler().runTask(plugin, action);
        } else {
            Bukkit.getScheduler().runTaskLater(plugin, action, delayTicks);
        }
    }
}
