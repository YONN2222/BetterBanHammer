package com.yonn2222.betterbanhammer.ban;

import java.net.InetAddress;
import java.util.UUID;

public record BanRequest(UUID id, String name, long seconds, String reason, String source, InetAddress address) {

    public boolean permanent() {
        return seconds == 0;
    }
}
