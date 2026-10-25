package com.yonn2222.betterbanhammer.ban;

import com.yonn2222.betterbanhammer.BetterBanHammer;
import com.yonn2222.betterbanhammer.Messages;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public final class Bans {
    private final BetterBanHammer plugin;
    private final EssentialsBans essentials = new EssentialsBans();
    private final NativeBans natives;
    private final BanLog log;

    public Bans(BetterBanHammer plugin) {
        this.plugin = plugin;
        this.natives = new NativeBans(plugin);
        this.log = new BanLog(plugin);
    }

    public boolean essentialsActive() {
        return plugin.settings().ban().essentials() && essentials.available();
    }

    public String backend() {
        return essentialsActive() ? "essentials" : "native";
    }

    public void ban(BanRequest request, CommandSender staff) {
        ban(request, staff, false);
    }

    public void ban(BanRequest request, CommandSender staff, boolean console) {
        plugin.scheduler().global(() -> execute(request, staff, console));
    }

    private void execute(BanRequest request, CommandSender staff, boolean console) {
        boolean done = false;
        if (essentialsActive()) {
            CommandSender runner = plugin.settings().ban().essentialsAsPlayer() && !console ? staff : Bukkit.getConsoleSender();
            done = essentials.ban(request, runner);
            if (!done) {
                plugin.getLogger().warning("EssentialsX did not accept the ban of " + request.name() + ", using the server ban list");
            }
        }
        if (!done) {
            done = natives.ban(request);
        }
        if (!done) {
            staff.sendMessage(plugin.messages().get("ban-failed", Messages.text("player", request.name())));
            return;
        }
        natives.banAddress(request);
        log.append(request);
        announce(request, staff);
    }

    private void announce(BanRequest request, CommandSender staff) {
        Messages messages = plugin.messages();
        String text = messages.get(
                request.permanent() ? "broadcast-permanent" : "broadcast-temporary",
                Messages.text("player", request.name()),
                Messages.text("staff", request.source()),
                Messages.text("reason", request.reason()),
                Messages.text("duration", messages.duration(request.seconds())));
        if (plugin.settings().ban().broadcast()) {
            Bukkit.broadcastMessage(text);
        } else {
            staff.sendMessage(text);
        }
        plugin.getLogger().info(request.source() + " banned " + request.name() + " (" + messages.duration(request.seconds()) + "): " + request.reason());
    }

    public boolean exempt(Player target) {
        return target.hasPermission("betterbanhammer.exempt")
                || plugin.settings().ban().protectOperators() && target.isOp()
                || essentialsActive() && target.hasPermission("essentials.ban.exempt");
    }
}
