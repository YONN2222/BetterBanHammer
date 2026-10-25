package com.yonn2222.betterbanhammer.time;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.OptionalLong;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class DurationsTest {

    @Test
    void parsesSingleUnits() {
        assertEquals(OptionalLong.of(30), Durations.parse("30s"));
        assertEquals(OptionalLong.of(1800), Durations.parse("30m"));
        assertEquals(OptionalLong.of(3600), Durations.parse("1h"));
        assertEquals(OptionalLong.of(604800), Durations.parse("7d"));
        assertEquals(OptionalLong.of(1209600), Durations.parse("2w"));
        assertEquals(OptionalLong.of(2592000), Durations.parse("1mo"));
        assertEquals(OptionalLong.of(31536000), Durations.parse("1y"));
    }

    @Test
    void parsesCombinedUnits() {
        assertEquals(OptionalLong.of(5400), Durations.parse("1h30m"));
        assertEquals(OptionalLong.of(93784), Durations.parse("1d2h3m4s"));
    }

    @Test
    void isCaseInsensitiveAndTrims() {
        assertEquals(OptionalLong.of(3600), Durations.parse(" 1H "));
    }

    @ParameterizedTest
    @ValueSource(strings = {"permanent", "PERM", "forever", "never"})
    void permanentWords(String word) {
        assertEquals(OptionalLong.of(Durations.PERMANENT), Durations.parse(word));
    }

    @ParameterizedTest
    @ValueSource(strings = {"", "abc", "10", "0m", "1x", "m5", "-5m", "1 d", "7tage", "1d2"})
    void rejectsInvalidInput(String input) {
        assertTrue(Durations.parse(input).isEmpty(), input);
    }

    @Test
    void rejectsNull() {
        assertTrue(Durations.parse(null).isEmpty());
    }

    @Test
    void formatsCompactAndJoined() {
        assertEquals("1d 2h 3m 4s", Durations.compact(93784));
        assertEquals("1d2h3m4s", Durations.joined(93784));
        assertEquals("30m", Durations.compact(1800));
        assertEquals("0s", Durations.compact(0));
    }
}
