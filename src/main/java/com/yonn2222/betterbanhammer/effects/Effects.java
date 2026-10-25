package com.yonn2222.betterbanhammer.effects;

import com.yonn2222.betterbanhammer.config.EffectSettings;
import com.yonn2222.betterbanhammer.config.FireworkSettings;
import com.yonn2222.betterbanhammer.config.ParticleSettings;
import com.yonn2222.betterbanhammer.config.SoundSettings;
import org.bukkit.FireworkEffect;
import org.bukkit.Location;
import org.bukkit.SoundCategory;
import org.bukkit.World;
import org.bukkit.entity.Firework;
import org.bukkit.inventory.meta.FireworkMeta;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ThreadLocalRandom;
import java.util.logging.Logger;

public final class Effects {
    private final EffectSettings settings;
    private final Logger log;
    private final Set<String> rejected = ConcurrentHashMap.newKeySet();

    public Effects(EffectSettings settings, Logger log) {
        this.settings = settings;
        this.log = log;
    }

    public void play(Location origin) {
        World world = origin.getWorld();
        if (world == null) {
            return;
        }
        Location center = origin.clone().add(0, 1, 0);
        if (settings.lightning()) {
            world.strikeLightningEffect(origin);
        }
        sounds(world, center);
        particles(world, center);
        fireworks(world, center);
    }

    private void sounds(World world, Location center) {
        SoundSettings sounds = settings.sounds();
        if (!sounds.enabled()) {
            return;
        }
        for (SoundSettings.Cue cue : sounds.cues()) {
            world.playSound(center, cue.key(), SoundCategory.MASTER, cue.volume(), cue.pitch());
        }
    }

    private void particles(World world, Location center) {
        ParticleSettings particles = settings.particles();
        if (!particles.enabled()) {
            return;
        }
        for (ParticleSettings.Burst burst : particles.bursts()) {
            String name = String.valueOf(burst.particle());
            if (rejected.contains(name)) {
                continue;
            }
            try {
                world.spawnParticle(burst.particle(), center, burst.count(), burst.spread(), burst.spread(), burst.spread(), burst.speed());
            } catch (IllegalArgumentException failure) {
                rejected.add(name);
                log.warning("The particle " + name + " needs extra data and cannot be used in config.yml");
            }
        }
    }

    private void fireworks(World world, Location center) {
        FireworkSettings firework = settings.firework();
        if (!firework.enabled()) {
            return;
        }
        FireworkEffect effect = FireworkEffect.builder()
                .with(firework.type())
                .withColor(firework.colors())
                .withFade(firework.fades())
                .flicker(firework.flicker())
                .trail(firework.trail())
                .build();
        ThreadLocalRandom random = ThreadLocalRandom.current();
        for (int index = 0; index < firework.count(); index++) {
            double spread = firework.spread();
            Location at = center.clone().add(
                    (random.nextDouble() - 0.5) * 2 * spread,
                    random.nextDouble() * spread,
                    (random.nextDouble() - 0.5) * 2 * spread);
            Firework spawned = world.spawn(at, Firework.class);
            FireworkMeta meta = spawned.getFireworkMeta();
            meta.clearEffects();
            meta.addEffect(effect);
            spawned.setFireworkMeta(meta);
            spawned.detonate();
        }
    }
}
