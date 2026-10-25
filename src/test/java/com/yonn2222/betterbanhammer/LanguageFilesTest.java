package com.yonn2222.betterbanhammer;

import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder;
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver;
import org.bukkit.configuration.file.YamlConfiguration;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class LanguageFilesTest {
    private static final List<String> NAMES = List.of("duration", "reason", "player", "staff", "label", "input", "seconds", "target", "prefix");
    private static final Pattern PLACEHOLDER = Pattern.compile("<(duration|reason|player|staff|label|input|seconds|target|prefix)>");

    private static YamlConfiguration load(String code) {
        var stream = LanguageFilesTest.class.getResourceAsStream("/lang/" + code + ".yml");
        assertNotNull(stream, code);
        return YamlConfiguration.loadConfiguration(new InputStreamReader(stream, StandardCharsets.UTF_8));
    }

    private static List<String> texts(YamlConfiguration file, String key) {
        return file.isList(key) ? file.getStringList(key) : List.of(file.getString(key, ""));
    }

    private static Set<String> placeholders(List<String> texts) {
        Set<String> found = new HashSet<>();
        for (String text : texts) {
            Matcher matcher = PLACEHOLDER.matcher(text);
            while (matcher.find()) {
                found.add(matcher.group(1));
            }
        }
        return found;
    }

    @Test
    void bundledListMatchesTheFilesOnDisk() {
        for (String code : Messages.BUNDLED) {
            assertNotNull(LanguageFilesTest.class.getResourceAsStream("/lang/" + code + ".yml"), code);
        }
        assertEquals(10, Messages.BUNDLED.size());
    }

    @ParameterizedTest
    @ValueSource(strings = {"zh", "es", "hi", "fr", "ar", "pt", "ru", "de", "ja"})
    void everyLanguageHasTheSameKeysAndPlaceholdersAsEnglish(String code) {
        YamlConfiguration english = load("en");
        YamlConfiguration other = load(code);
        assertEquals(english.getKeys(false), other.getKeys(false));
        for (String key : english.getKeys(false)) {
            assertEquals(english.isList(key), other.isList(key), code + ":" + key);
            assertEquals(texts(english, key).size(), texts(other, key).size(), code + ":" + key);
            assertEquals(placeholders(texts(english, key)), placeholders(texts(other, key)), code + ":" + key);
        }
    }

    @ParameterizedTest
    @ValueSource(strings = {"en", "zh", "es", "hi", "fr", "ar", "pt", "ru", "de", "ja"})
    void everyMessageParsesAsMiniMessage(String code) {
        YamlConfiguration file = load(code);
        List<TagResolver> resolvers = new ArrayList<>();
        for (String name : NAMES) {
            resolvers.add(Placeholder.unparsed(name, "x"));
        }
        for (String key : file.getKeys(false)) {
            for (String text : texts(file, key)) {
                if (!key.equals("hammer-lore")) {
                    assertFalse(text.isBlank(), code + ":" + key);
                }
                MiniMessage.builder().strict(false).build().deserialize(text, TagResolver.resolver(resolvers));
            }
        }
    }

    @Test
    void messagesStartWithThePrefixTag() {
        YamlConfiguration english = load("en");
        assertTrue(english.getString("usage").startsWith("<prefix>"));
        assertTrue(english.getString("prefix").endsWith(" "));
    }
}
