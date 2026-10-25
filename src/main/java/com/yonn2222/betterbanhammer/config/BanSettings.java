package com.yonn2222.betterbanhammer.config;

import java.util.Set;

public record BanSettings(
        boolean essentials,
        boolean essentialsAsPlayer,
        boolean broadcast,
        boolean cancelDamage,
        boolean requireSneak,
        boolean ip,
        boolean consume,
        int cooldownSeconds,
        boolean protectOperators,
        Set<String> disabledWorlds,
        boolean log,
        boolean targeted,
        boolean targetedConsume
) {
}
