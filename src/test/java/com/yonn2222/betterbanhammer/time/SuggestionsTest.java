package com.yonn2222.betterbanhammer.time;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SuggestionsTest {
    private static final List<String> OPTIONS = List.of("30m", "1h", "1d", "14d", "permanent");

    @Test
    void emptyInputReturnsEverything() {
        assertEquals(OPTIONS, Suggestions.filter(OPTIONS, ""));
    }

    @Test
    void filtersByPrefix() {
        assertEquals(List.of("1h", "1d", "14d"), Suggestions.filter(OPTIONS, "1"));
        assertEquals(List.of("14d"), Suggestions.filter(OPTIONS, "14"));
    }

    @Test
    void ignoresCase() {
        assertEquals(List.of("permanent"), Suggestions.filter(OPTIONS, "PERM"));
    }

    @Test
    void noMatchIsEmpty() {
        assertEquals(List.of(), Suggestions.filter(OPTIONS, "x"));
    }
}
