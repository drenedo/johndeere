package me.renedo.johndeere.application;

import java.time.LocalDateTime;
import java.util.Random;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.apache.commons.lang3.RandomStringUtils;

import me.renedo.johndeere.infraestrucure.http.EventRequest;

public class EventRequestMother {

    public static EventRequest of(UUID sessionId, Set<EventRequest.Event> events) {
        return new EventRequest(sessionId, events);
    }

    public static EventRequest of(UUID sessionId, String type, int elements) {
        return new EventRequest(sessionId, anyEvents(type, elements));
    }

    public static EventRequest of(UUID sessionId, int elements) {
        return new EventRequest(sessionId, anyEvents(elements));
    }

    public static EventRequest any() {
        return new EventRequest(UUID.randomUUID(), anyEvents(new Random().nextInt(10)));
    }

    public static Set<EventRequest.Event> anyEvents(String type, int elements) {
        return IntStream.range(0, elements)
                .mapToObj(i -> new EventRequest.Event(LocalDateTime.now(), type, new Random().nextDouble()))
                .collect(Collectors.toSet());
    }

    public static Set<EventRequest.Event> anyEvents(int elements) {
        return IntStream.range(0, elements)
                .mapToObj(i -> new EventRequest.Event(LocalDateTime.now(), RandomStringUtils.secure().nextAlphanumeric(7), new Random().nextDouble()))
                .collect(Collectors.toSet());
    }

    public static EventRequest.Event ofEvent(LocalDateTime eventAt, String eventType, Double numericEventValue) {
        return new EventRequest.Event(eventAt, eventType, numericEventValue);
    }
}
