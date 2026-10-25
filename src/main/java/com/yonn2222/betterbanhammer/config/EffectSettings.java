package com.yonn2222.betterbanhammer.config;

public record EffectSettings(
        FireworkSettings firework,
        ParticleSettings particles,
        SoundSettings sounds,
        boolean lightning
) {
}
