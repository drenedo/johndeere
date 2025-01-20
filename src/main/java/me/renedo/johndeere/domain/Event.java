package me.renedo.johndeere.domain;

import java.time.LocalDateTime;
import java.util.UUID;

public class Event {
    private final UUID sessionId;
    private final LocalDateTime date;
    private final String type;
    private final Double value;

    public Event(UUID sessionId, LocalDateTime date, String type, Double value) {
        this.sessionId = sessionId;
        this.date = date;
        this.type = type;
        this.value = value;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public UUID getSessionId() {
        return sessionId;
    }

    public String getType() {
        return type;
    }

    public Double getValue() {
        return value;
    }
}
