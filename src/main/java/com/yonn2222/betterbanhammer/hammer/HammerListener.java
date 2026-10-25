package com.yonn2222.betterbanhammer.hammer;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public final class HammerListener implements Listener {
    private final HammerHits hits;

    public HammerListener(HammerHits hits) {
        this.hits = hits;
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onHit(EntityDamageByEntityEvent event) {
        if (event.getDamager() instanceof Player staff && event.getEntity() instanceof Player target && hits.handle(staff, target)) {
            event.setCancelled(true);
        }
    }
}
