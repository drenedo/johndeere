package me.renedo.johndeere.application;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public record CreateEventCommand(UUID sessionId, List<Event> events) {

    public record Event(LocalDateTime start, String type, Double value) {
    }
}
