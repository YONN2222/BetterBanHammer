package com.yonn2222.betterbanhammer.config;

import com.yonn2222.betterbanhammer.time.Durations;
import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Registry;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.enchantments.Enchantment;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.OptionalLong;
import java.util.Set;
import java.util.logging.Logger;

public record Settings(
        String language,
        HammerSettings hammer,
        DefaultSettings defaults,
        BanSettings ban,
        SuggestionSettings suggestions,
        EffectSettings effects
) {

    public static Settings read(FileConfiguration config, Logger log) {
        return new Settings(
                config.getString("language", "en"),
                hammer(Parse.section(config, "hammer"), log),
                defaults(Parse.section(config, "defaults"), log),
                ban(Parse.section(config, "ban")),
                new SuggestionSettings(
                        config.getStringList("suggestions.durations"),
                        config.getStringList("suggestions.reasons")),
                effects(Parse.section(config, "effects"), log));
    }

    private static HammerSettings hammer(ConfigurationSection section, Logger log) {
        String name = section.getString("material", "MACE");
        Material material = Material.matchMaterial(name);
        if (material == null || !material.isItem()) {
            log.warning("Unknown hammer material '" + name + "' in config.yml, using MACE");
            material = Material.MACE;
        }
        Map<Enchantment, Integer> enchantments = new LinkedHashMap<>();
        ConfigurationSection listed = Parse.section(section, "enchantments");
        for (String key : listed.getKeys(false)) {
            Enchantment enchantment = Parse.lookup(Registry.ENCHANTMENT, key);
            int level = listed.getInt(key, 0);
            if (enchantment == null) {
                log.warning("Unknown enchantment '" + key + "' in config.yml");
            } else if (level > 0) {
                enchantments.put(enchantment, level);
            }
        }
        return new HammerSettings(material, section.getBoolean("glint", true), section.getBoolean("unbreakable", true), section.getBoolean("staff-only", true), Map.copyOf(enchantments));
    }

    private static DefaultSettings defaults(ConfigurationSection section, Logger log) {
        String raw = section.getString("duration", "permanent");
        OptionalLong parsed = Durations.parse(raw);
        if (parsed.isEmpty()) {
            log.warning("Ignoring invalid default duration '" + raw + "' in config.yml, using permanent");
        }
        return new DefaultSettings(parsed.orElse(Durations.PERMANENT), section.getString("reason", "Banned with the ban hammer"));
    }

    private static BanSettings ban(ConfigurationSection section) {
        ConfigurationSection essentials = Parse.section(section, "essentials");
        return new BanSettings(
                essentials.getBoolean("enabled", true),
                !essentials.getString("run-as", "player").equalsIgnoreCase("console"),
                section.getBoolean("broadcast", true),
                section.getBoolean("cancel-damage", true),
                section.getBoolean("require-sneak", false),
                section.getBoolean("ip", false),
                section.getBoolean("consume", false),
                Math.max(0, section.getInt("cooldown", 0)),
                section.getBoolean("protect-operators", false),
                Set.copyOf(section.getStringList("disabled-worlds")),
                section.getBoolean("log", true),
                section.getBoolean("targeted.enabled", false),
                section.getBoolean("targeted.consume", true));
    }

    private static EffectSettings effects(ConfigurationSection section, Logger log) {
        return new EffectSettings(
                firework(Parse.section(section, "firework"), log),
                particles(Parse.section(section, "particles"), log),
                sounds(Parse.section(section, "sounds")),
                section.getBoolean("lightning", false));
    }

    private static FireworkSettings firework(ConfigurationSection section, Logger log) {
        String typeName = section.getString("type", "BALL_LARGE");
        FireworkEffect.Type type;
        try {
            type = FireworkEffect.Type.valueOf(typeName.trim().toUpperCase(Locale.ROOT));
        } catch (IllegalArgumentException e) {
            log.warning("Unknown firework type '" + typeName + "' in config.yml, using BALL_LARGE");
            type = FireworkEffect.Type.BALL_LARGE;
        }
        List<Color> colors = Parse.colors(section.getStringList("colors"), log, "effects.firework.colors");
        if (colors.isEmpty()) {
            colors = List.of(Color.fromRGB(0xDFAB40), Color.fromRGB(0xDF6440));
        }
        return new FireworkSettings(
                section.getBoolean("enabled", true),
                type,
                colors,
                Parse.colors(section.getStringList("fade"), log, "effects.firework.fade"),
                section.getBoolean("flicker", true),
                section.getBoolean("trail", true),
                Math.max(1, Math.min(10, section.getInt("count", 2))),
                Math.max(0, section.getDouble("spread", 0.6)));
    }

    private static ParticleSettings particles(ConfigurationSection section, Logger log) {
        List<ParticleSettings.Burst> bursts = new ArrayList<>();
        for (Map<?, ?> entry : section.getMapList("bursts")) {
            Object type = entry.get("type");
            Particle particle = type == null ? null : Parse.lookup(Registry.PARTICLE_TYPE, type.toString());
            if (particle == null) {
                log.warning("Unknown particle '" + type + "' in config.yml");
                continue;
            }
            bursts.add(new ParticleSettings.Burst(
                    particle,
                    (int) Math.max(1, Math.min(500, Parse.number(entry.get("count"), 10))),
                    Math.max(0, Parse.number(entry.get("spread"), 0.5)),
                    Math.max(0, Parse.number(entry.get("speed"), 0.1))));
        }
        return new ParticleSettings(section.getBoolean("enabled", true), List.copyOf(bursts));
    }

    private static SoundSettings sounds(ConfigurationSection section) {
        List<SoundSettings.Cue> cues = new ArrayList<>();
        for (Map<?, ?> entry : section.getMapList("cues")) {
            Object key = entry.get("key");
            if (key == null) {
                continue;
            }
            cues.add(new SoundSettings.Cue(
                    key.toString(),
                    (float) Parse.number(entry.get("volume"), 1.0),
                    (float) Parse.number(entry.get("pitch"), 1.0)));
        }
        return new SoundSettings(section.getBoolean("enabled", true), List.copyOf(cues));
    }
}
