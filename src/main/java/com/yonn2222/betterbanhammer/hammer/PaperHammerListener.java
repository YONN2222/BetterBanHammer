package com.yonn2222.betterbanhammer.hammer;

import io.papermc.paper.event.player.PrePlayerAttackEntityEvent;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;

public final class PaperHammerListener implements Listener {
    private final HammerHits hits;

    public PaperHammerListener(HammerHits hits) {
        this.hits = hits;
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onAttack(PrePlayerAttackEntityEvent event) {
        if (event.getAttacked() instanceof Player target && hits.handle(event.getPlayer(), target)) {
            event.setCancelled(true);
        }
    }
}
