package com.yonn2222.betterbanhammer.config;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;

import java.util.Map;

public record HammerSettings(Material material, boolean glint, boolean unbreakable, boolean staffOnly, Map<Enchantment, Integer> enchantments) {
}
