package com.yonn2222.betterbanhammer.ban;

import com.yonn2222.betterbanhammer.BetterBanHammer;
import com.yonn2222.betterbanhammer.Messages;
import org.bukkit.BanList;
import org.bukkit.Bukkit;
import org.bukkit.ban.IpBanList;
import org.bukkit.ban.ProfileBanList;
import org.bukkit.entity.Player;

import java.time.Instant;
import java.util.Date;
import java.util.logging.Level;

final class NativeBans {
    private final BetterBanHammer plugin;

    NativeBans(BetterBanHammer plugin) {
        this.plugin = plugin;
    }

    void banAddress(BanRequest request) {
        if (request.address() == null) {
            return;
        }
        try {
            IpBanList list = Bukkit.getBanList(BanList.Type.IP);
            list.addBan(request.address(), request.reason(), expiry(request), request.source());
        } catch (RuntimeException | LinkageError failure) {
            plugin.getLogger().log(Level.WARNING, "Could not add the address of " + request.name() + " to the ban list", failure);
        }
    }

    private static Date expiry(BanRequest request) {
        return request.permanent() ? null : Date.from(Instant.now().plusSeconds(request.seconds()));
    }

    @SuppressWarnings("deprecation")
    boolean ban(BanRequest request) {
        try {
            ProfileBanList list = Bukkit.getBanList(BanList.Type.PROFILE);
            list.addBan(Bukkit.createPlayerProfile(request.id(), request.name()), request.reason(), expiry(request), request.source());
        } catch (RuntimeException | LinkageError failure) {
            plugin.getLogger().log(Level.WARNING, "Could not add " + request.name() + " to the ban list", failure);
            return false;
        }
        Player target = Bukkit.getPlayer(request.id());
        if (target != null) {
            String message = plugin.messages().get(
                    request.permanent() ? "kick-permanent" : "kick-temporary",
                    Messages.text("reason", request.reason()),
                    Messages.text("duration", plugin.messages().duration(request.seconds())));
            plugin.scheduler().player(target, () -> target.kickPlayer(message), 0);
        }
        return true;
    }
}
