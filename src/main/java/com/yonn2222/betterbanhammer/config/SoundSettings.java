package com.yonn2222.betterbanhammer.config;

import java.util.List;

public record SoundSettings(boolean enabled, List<Cue> cues) {

    public record Cue(String key, float volume, float pitch) {
    }
}
