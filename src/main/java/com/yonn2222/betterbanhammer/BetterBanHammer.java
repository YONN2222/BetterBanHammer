package com.yonn2222.betterbanhammer;

import com.yonn2222.betterbanhammer.ban.Bans;
import com.yonn2222.betterbanhammer.command.HammerCommand;
import com.yonn2222.betterbanhammer.config.Settings;
import com.yonn2222.betterbanhammer.effects.Effects;
import com.yonn2222.betterbanhammer.hammer.HammerGuard;
import com.yonn2222.betterbanhammer.hammer.HammerHits;
import com.yonn2222.betterbanhammer.hammer.HammerItems;
import com.yonn2222.betterbanhammer.hammer.HammerListener;
import com.yonn2222.betterbanhammer.hammer.PaperHammerListener;
import com.yonn2222.betterbanhammer.metrics.Stats;
import com.yonn2222.betterbanhammer.platform.Scheduler;
import org.bukkit.command.PluginCommand;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public final class BetterBanHammer extends JavaPlugin {
    private volatile Settings settings;
    private volatile Messages messages;
    private volatile Effects effects;
    private Scheduler scheduler;
    private HammerItems items;
    private Bans bans;

    @Override
    public void onEnable() {
        saveDefaultConfig();
        scheduler = Scheduler.create(this);
        load();
        items = new HammerItems(this);
        bans = new Bans(this);
        getServer().getPluginManager().registerEvents(listener(new HammerHits(this)), this);
        getServer().getPluginManager().registerEvents(new HammerGuard(this), this);
        PluginCommand command = getCommand("banhammer");
        if (command != null) {
            HammerCommand executor = new HammerCommand(this);
            command.setExecutor(executor);
            command.setTabCompleter(executor);
        }
        Stats.start(this);
        getLogger().info("Using " + bans.backend() + " bans");
    }

    private static Listener listener(HammerHits hits) {
        try {
            Class.forName("io.papermc.paper.event.player.PrePlayerAttackEntityEvent");
            return new PaperHammerListener(hits);
        } catch (ClassNotFoundException missing) {
            return new HammerListener(hits);
        }
    }

    private void load() {
        reloadConfig();
        settings = Settings.read(getConfig(), getLogger());
        messages = new Messages(this, settings.language());
        effects = new Effects(settings.effects(), getLogger());
    }

    public void reload() {
        load();
    }

    public Settings settings() {
        return settings;
    }

    public Messages messages() {
        return messages;
    }

    public Effects effects() {
        return effects;
    }

    public Scheduler scheduler() {
        return scheduler;
    }

    public HammerItems items() {
        return items;
    }

    public Bans bans() {
        return bans;
    }
}
