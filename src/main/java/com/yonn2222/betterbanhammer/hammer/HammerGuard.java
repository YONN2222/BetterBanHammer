package com.yonn2222.betterbanhammer.hammer;

import com.yonn2222.betterbanhammer.BetterBanHammer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;

public final class HammerGuard implements Listener {
    private final BetterBanHammer plugin;

    public HammerGuard(BetterBanHammer plugin) {
        this.plugin = plugin;
    }

    @EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
    public void onPickup(EntityPickupItemEvent event) {
        if (!(event.getEntity() instanceof Player player) || !guarded(player)) {
            return;
        }
        if (plugin.items().read(event.getItem().getItemStack()).filter(hammer -> !hammer.targeted()).isPresent()) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        if (!guarded(player)) {
            return;
        }
        plugin.scheduler().player(player, () -> strip(player), 20L);
    }

    private void strip(Player player) {
        ItemStack[] contents = player.getInventory().getContents();
        for (int slot = 0; slot < contents.length; slot++) {
            ItemStack stack = contents[slot];
            if (stack != null && plugin.items().read(stack).filter(hammer -> !hammer.targeted()).isPresent()) {
                player.getInventory().setItem(slot, null);
            }
        }
    }

    private boolean guarded(Player player) {
        return plugin.settings().hammer().staffOnly() && !player.hasPermission("betterbanhammer.use");
    }
}
