package com.yonn2222.betterbanhammer.ban;

import com.yonn2222.betterbanhammer.time.Durations;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.Plugin;

final class EssentialsBans {
    private static final String BAN = "essentials:ban";
    private static final String TEMPBAN = "essentials:tempban";

    boolean available() {
        Plugin plugin = Bukkit.getPluginManager().getPlugin("Essentials");
        return plugin != null
                && plugin.isEnabled()
                && Bukkit.getPluginCommand(BAN) != null
                && Bukkit.getPluginCommand(TEMPBAN) != null;
    }

    boolean ban(BanRequest request, CommandSender runner) {
        String line = request.permanent()
                ? BAN + " " + request.name() + " " + request.reason()
                : TEMPBAN + " " + request.name() + " " + Durations.joined(request.seconds()) + " " + request.reason();
        return Bukkit.dispatchCommand(runner, line);
    }
}
