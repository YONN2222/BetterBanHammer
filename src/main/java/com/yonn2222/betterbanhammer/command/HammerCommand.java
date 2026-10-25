package com.yonn2222.betterbanhammer.command;

import com.yonn2222.betterbanhammer.BetterBanHammer;
import com.yonn2222.betterbanhammer.Messages;
import com.yonn2222.betterbanhammer.time.Durations;
import com.yonn2222.betterbanhammer.time.Suggestions;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.OptionalLong;

public final class HammerCommand implements TabExecutor {
    private final BetterBanHammer plugin;

    public HammerCommand(BetterBanHammer plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        Messages messages = plugin.messages();
        if (args.length > 0 && args[0].equalsIgnoreCase("reload")) {
            if (!sender.hasPermission("betterbanhammer.reload")) {
                sender.sendMessage(messages.get("no-permission"));
                return true;
            }
            plugin.reload();
            sender.sendMessage(plugin.messages().get("reloaded"));
            return true;
        }
        if (!sender.hasPermission("betterbanhammer.give")) {
            sender.sendMessage(messages.get("no-permission"));
            return true;
        }
        boolean aimed = args.length > 0 && args[0].equalsIgnoreCase("target");
        boolean give = args.length > 0 && args[0].equalsIgnoreCase("give");
        Player target = null;
        Player victim = null;
        int offset = 0;
        if (aimed) {
            if (!sender.hasPermission("betterbanhammer.target")) {
                sender.sendMessage(messages.get("no-permission"));
                return true;
            }
            if (!plugin.settings().ban().targeted()) {
                sender.sendMessage(messages.get("target-disabled"));
                return true;
            }
            if (args.length < 2) {
                sender.sendMessage(messages.get("usage", Messages.text("label", label)));
                return true;
            }
            victim = Bukkit.getPlayerExact(args[1]);
            if (victim == null) {
                sender.sendMessage(messages.get("player-not-found", Messages.text("player", args[1])));
                return true;
            }
            offset = 2;
            Player named = args.length > 2 ? Bukkit.getPlayerExact(args[2]) : null;
            if (named != null) {
                target = named;
                offset = 3;
            }
        } else if (give) {
            if (args.length < 2) {
                sender.sendMessage(messages.get("usage", Messages.text("label", label)));
                return true;
            }
            target = Bukkit.getPlayerExact(args[1]);
            if (target == null) {
                sender.sendMessage(messages.get("player-not-found", Messages.text("player", args[1])));
                return true;
            }
            offset = 2;
        }
        if (target == null) {
            if (sender instanceof Player player) {
                target = player;
            } else {
                sender.sendMessage(messages.get("player-only", Messages.text("label", label)));
                return true;
            }
        }
        String[] rest = Arrays.copyOfRange(args, offset, args.length);
        long seconds = plugin.settings().defaults().seconds();
        if (rest.length > 0) {
            OptionalLong parsed = Durations.parse(rest[0]);
            if (parsed.isEmpty()) {
                sender.sendMessage(messages.get("invalid-duration", Messages.text("input", rest[0])));
                return true;
            }
            seconds = parsed.getAsLong();
        }
        String reason = rest.length > 1 ? String.join(" ", Arrays.copyOfRange(rest, 1, rest.length)) : plugin.settings().defaults().reason();
        deliver(sender, target, seconds, reason, victim);
        return true;
    }

    private void deliver(CommandSender sender, Player target, long seconds, String reason, Player victim) {
        ItemStack hammer = victim == null
                ? plugin.items().create(seconds, reason)
                : plugin.items().create(seconds, reason, victim.getUniqueId(), victim.getName());
        plugin.scheduler().player(target, () -> {
            target.getInventory().addItem(hammer).values()
                    .forEach(leftover -> target.getWorld().dropItemNaturally(target.getLocation(), leftover));
            Messages messages = plugin.messages();
            target.sendMessage(messages.get("hammer-received",
                    Messages.text("duration", messages.duration(seconds)),
                    Messages.text("reason", reason)));
            if (sender != target) {
                sender.sendMessage(victim == null
                        ? messages.get("hammer-given", Messages.text("player", target.getName()))
                        : messages.get("target-given", Messages.text("player", target.getName()), Messages.text("target", victim.getName())));
            }
        }, 0);
    }

    private boolean canTarget(CommandSender sender) {
        return plugin.settings().ban().targeted() && sender.hasPermission("betterbanhammer.target");
    }

    @Override
    public @NotNull List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, @NotNull String[] args) {
        List<String> out = new ArrayList<>();
        String typed = args[args.length - 1];
        boolean canGive = sender.hasPermission("betterbanhammer.give");
        if (args.length == 1) {
            if (sender.hasPermission("betterbanhammer.reload")) {
                out.addAll(Suggestions.filter(List.of("reload"), typed));
            }
            if (canGive) {
                out.addAll(Suggestions.filter(List.of("give"), typed));
            }
            if (canTarget(sender)) {
                out.addAll(Suggestions.filter(List.of("target"), typed));
            }
        }
        if (!canGive) {
            return out;
        }
        boolean aimed = args[0].equalsIgnoreCase("target") && canTarget(sender);
        boolean give = args[0].equalsIgnoreCase("give");
        if ((give || aimed) && args.length == 2 || aimed && args.length == 3) {
            Bukkit.getOnlinePlayers().stream()
                    .map(Player::getName)
                    .filter(name -> name.toLowerCase(Locale.ROOT).startsWith(typed.toLowerCase(Locale.ROOT)))
                    .forEach(out::add);
            if (!aimed || args.length == 2) {
                return out;
            }
        }
        boolean named = aimed && args.length > 2 && Bukkit.getPlayerExact(args[2]) != null;
        int position = args.length - 1 - (aimed ? (named ? 3 : 2) : give ? 2 : 0);
        if (position == 0) {
            out.addAll(Suggestions.filter(plugin.settings().suggestions().durations(), typed));
        } else if (position > 0) {
            out.addAll(Suggestions.filter(plugin.settings().suggestions().reasons(), typed));
        }
        return out;
    }
}
