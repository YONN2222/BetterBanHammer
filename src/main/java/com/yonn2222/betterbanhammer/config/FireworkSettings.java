package com.yonn2222.betterbanhammer.config;

import org.bukkit.Color;
import org.bukkit.FireworkEffect;

import java.util.List;

public record FireworkSettings(
        boolean enabled,
        FireworkEffect.Type type,
        List<Color> colors,
        List<Color> fades,
        boolean flicker,
        boolean trail,
        int count,
        double spread
) {
}
