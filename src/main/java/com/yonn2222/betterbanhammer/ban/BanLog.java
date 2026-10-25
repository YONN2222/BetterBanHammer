package com.yonn2222.betterbanhammer.ban;

import com.yonn2222.betterbanhammer.BetterBanHammer;
import com.yonn2222.betterbanhammer.time.Durations;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.logging.Level;

final class BanLog {
    private final BetterBanHammer plugin;

    BanLog(BetterBanHammer plugin) {
        this.plugin = plugin;
    }

    void append(BanRequest request) {
        if (!plugin.settings().ban().log()) {
            return;
        }
        String length = request.permanent() ? "permanent" : Durations.joined(request.seconds());
        String line = String.join(" | ",
                OffsetDateTime.now().format(DateTimeFormatter.ISO_OFFSET_DATE_TIME),
                request.source(),
                request.name(),
                request.id().toString(),
                length,
                request.reason().replace('\n', ' ')) + System.lineSeparator();
        Path file = plugin.getDataFolder().toPath().resolve("bans.log");
        try {
            Files.createDirectories(file.getParent());
            Files.writeString(file, line, StandardCharsets.UTF_8, StandardOpenOption.CREATE, StandardOpenOption.APPEND);
        } catch (IOException failure) {
            plugin.getLogger().log(Level.WARNING, "Could not write bans.log", failure);
        }
    }
}
