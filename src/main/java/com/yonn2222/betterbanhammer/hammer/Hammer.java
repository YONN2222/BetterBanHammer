package com.yonn2222.betterbanhammer.hammer;

import java.util.UUID;

public record Hammer(long seconds, String reason, UUID target, String targetName) {
    public boolean targeted() {
        return target != null;
    }
}
