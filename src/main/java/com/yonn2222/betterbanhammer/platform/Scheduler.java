package com.yonn2222.betterbanhammer.platform;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public interface Scheduler {
    void global(Runnable action);

    void globalLater(Runnable action, long delayTicks);

    void region(Location location, Runnable action);

    void player(Player player, Runnable action, long delayTicks);

    static Scheduler create(JavaPlugin plugin) {
        try {
            Class.forName("io.papermc.paper.threadedregions.scheduler.GlobalRegionScheduler");
            return new RegionScheduler(plugin);
        } catch (ClassNotFoundException missing) {
            return new ClassicScheduler(plugin);
        }
    }
}
