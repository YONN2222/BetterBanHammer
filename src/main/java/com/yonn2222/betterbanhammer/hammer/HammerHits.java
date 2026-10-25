package com.yonn2222.betterbanhammer.hammer;

import com.yonn2222.betterbanhammer.BetterBanHammer;
import com.yonn2222.betterbanhammer.Messages;
import com.yonn2222.betterbanhammer.ban.BanRequest;
import com.yonn2222.betterbanhammer.config.BanSettings;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public final class HammerHits {
    private static final long PENDING_TICKS = 100L;

    private final BetterBanHammer plugin;
    private final Set<UUID> pending = ConcurrentHashMap.newKeySet();
    private final Map<UUID, Long> lastBan = new ConcurrentHashMap<>();

    public HammerHits(BetterBanHammer plugin) {
        this.plugin = plugin;
    }

    public boolean handle(Player staff, Player target) {
        Optional<Hammer> held = plugin.items().read(staff.getInventory().getItemInMainHand());
        if (held.isEmpty() || Bukkit.getPlayer(target.getUniqueId()) != target) {
            return false;
        }
        BanSettings settings = plugin.settings().ban();
        Messages messages = plugin.messages();
        Hammer hammer = held.get();
        if (hammer.targeted()) {
            if (!settings.targeted()) {
                staff.sendMessage(messages.get("target-disabled"));
                return true;
            }
            if (!hammer.target().equals(target.getUniqueId())) {
                staff.sendMessage(messages.get("target-only", Messages.text("target", hammer.targetName())));
                return true;
            }
        } else if (!staff.hasPermission("betterbanhammer.use")) {
            staff.sendMessage(messages.get("no-permission"));
            return true;
        }
        if (settings.disabledWorlds().contains(staff.getWorld().getName())) {
            staff.sendMessage(messages.get("world-disabled"));
            return true;
        }
        if (settings.requireSneak() && !staff.isSneaking()) {
            return false;
        }
        if (plugin.bans().exempt(target)) {
            staff.sendMessage(messages.get("exempt", Messages.text("player", target.getName())));
            return settings.cancelDamage();
        }
        long wait = cooldownLeft(staff, settings);
        if (wait > 0) {
            staff.sendMessage(messages.get("cooldown", Messages.text("seconds", Long.toString(wait))));
            return true;
        }
        if (!pending.add(target.getUniqueId())) {
            return settings.cancelDamage();
        }
        plugin.scheduler().globalLater(() -> pending.remove(target.getUniqueId()), PENDING_TICKS);
        lastBan.put(staff.getUniqueId(), System.currentTimeMillis());
        Location location = target.getLocation();
        plugin.scheduler().region(location, () -> plugin.effects().play(location));
        if (hammer.targeted() ? settings.targetedConsume() : settings.consume()) {
            consume(staff);
        }
        plugin.bans().ban(new BanRequest(target.getUniqueId(), target.getName(), hammer.seconds(), hammer.reason(), staff.getName(), address(target, settings)), staff, hammer.targeted());
        return settings.cancelDamage();
    }

    private long cooldownLeft(Player staff, BanSettings settings) {
        if (settings.cooldownSeconds() == 0) {
            return 0;
        }
        Long last = lastBan.get(staff.getUniqueId());
        if (last == null) {
            return 0;
        }
        long remaining = settings.cooldownSeconds() * 1000L - (System.currentTimeMillis() - last);
        return remaining <= 0 ? 0 : (remaining + 999) / 1000;
    }

    private static void consume(Player staff) {
        ItemStack hand = staff.getInventory().getItemInMainHand();
        if (hand.getAmount() > 1) {
            hand.setAmount(hand.getAmount() - 1);
        } else {
            staff.getInventory().setItemInMainHand(null);
        }
    }

    private static InetAddress address(Player target, BanSettings settings) {
        if (!settings.ip()) {
            return null;
        }
        InetSocketAddress socket = target.getAddress();
        return socket == null ? null : socket.getAddress();
    }
}
