package com.yonn2222.betterbanhammer.config;

import org.bukkit.Particle;

import java.util.List;

public record ParticleSettings(boolean enabled, List<Burst> bursts) {

    public record Burst(Particle particle, int count, double spread, double speed) {
    }
}
