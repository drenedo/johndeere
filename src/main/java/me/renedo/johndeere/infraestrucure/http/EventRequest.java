package me.renedo.johndeere.infraestrucure.http;


import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

public record EventRequest  (UUID sessionId, Set<Event> events) {

    public record Event(LocalDateTime eventAt, String eventType, Double numericEventValue) {
    }
}
