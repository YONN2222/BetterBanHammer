package com.yonn2222.betterbanhammer.hammer;

import com.yonn2222.betterbanhammer.BetterBanHammer;
import com.yonn2222.betterbanhammer.Messages;
import com.yonn2222.betterbanhammer.config.HammerSettings;
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public final class HammerItems {
    private static final String RESET = "§r";

    private final BetterBanHammer plugin;
    private final NamespacedKey marker;
    private final NamespacedKey duration;
    private final NamespacedKey reason;
    private final NamespacedKey target;
    private final NamespacedKey targetName;

    public HammerItems(BetterBanHammer plugin) {
        this.plugin = plugin;
        this.marker = new NamespacedKey(plugin, "hammer");
        this.duration = new NamespacedKey(plugin, "duration");
        this.reason = new NamespacedKey(plugin, "reason");
        this.target = new NamespacedKey(plugin, "target");
        this.targetName = new NamespacedKey(plugin, "target_name");
    }

    public ItemStack create(long seconds, String banReason) {
        return create(seconds, banReason, null, null);
    }

    public ItemStack create(long seconds, String banReason, UUID victim, String victimName) {
        HammerSettings settings = plugin.settings().hammer();
        Messages messages = plugin.messages();
        ItemStack stack = new ItemStack(settings.material());
        ItemMeta meta = stack.getItemMeta();
        if (meta == null) {
            return stack;
        }
        TagResolver[] placeholders = {
                Messages.text("duration", messages.duration(seconds)),
                Messages.text("reason", banReason)
        };
        meta.setDisplayName(RESET + messages.get("hammer-name", placeholders));
        List<String> lore = new ArrayList<>(messages.lines("hammer-lore", placeholders));
        if (victim != null) {
            lore.add(messages.get("hammer-lore-target", Messages.text("target", victimName)));
        }
        meta.setLore(lore.stream().map(line -> RESET + line).toList());
        settings.enchantments().forEach((enchantment, level) -> meta.addEnchant(enchantment, level, true));
        if (settings.glint()) {
            meta.setEnchantmentGlintOverride(true);
        }
        meta.setUnbreakable(settings.unbreakable());
        PersistentDataContainer data = meta.getPersistentDataContainer();
        data.set(marker, PersistentDataType.BYTE, (byte) 1);
        data.set(duration, PersistentDataType.LONG, seconds);
        data.set(reason, PersistentDataType.STRING, banReason);
        if (victim != null) {
            data.set(target, PersistentDataType.STRING, victim.toString());
            data.set(targetName, PersistentDataType.STRING, victimName);
        }
        stack.setItemMeta(meta);
        return stack;
    }

    public Optional<Hammer> read(ItemStack stack) {
        if (stack == null || !stack.hasItemMeta()) {
            return Optional.empty();
        }
        PersistentDataContainer data = stack.getItemMeta().getPersistentDataContainer();
        if (!data.has(marker, PersistentDataType.BYTE)) {
            return Optional.empty();
        }
        long seconds = data.getOrDefault(duration, PersistentDataType.LONG, plugin.settings().defaults().seconds());
        String text = data.getOrDefault(reason, PersistentDataType.STRING, plugin.settings().defaults().reason());
        UUID victim = null;
        String victimName = null;
        String stored = data.get(target, PersistentDataType.STRING);
        if (stored != null) {
            try {
                victim = UUID.fromString(stored);
                victimName = data.getOrDefault(targetName, PersistentDataType.STRING, stored);
            } catch (IllegalArgumentException invalid) {
                return Optional.empty();
            }
        }
        return Optional.of(new Hammer(seconds, text, victim, victimName));
    }
}
