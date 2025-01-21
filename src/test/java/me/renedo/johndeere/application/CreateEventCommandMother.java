package me.renedo.johndeere.application;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import org.apache.commons.lang3.RandomStringUtils;

class CreateEventCommandMother {

    public static CreateEventCommand any() {
        return of(UUID.randomUUID(), List.of(anyEvent()));
    }

    public static CreateEventCommand of(UUID id, List<CreateEventCommand.Event> events) {
        return new CreateEventCommand(id, events);
    }

    public static CreateEventCommand.Event anyEvent() {
        return eventOf(LocalDateTime.now(), RandomStringUtils.secure().nextAlphanumeric(7), new Random().nextDouble());
    }

    public static CreateEventCommand.Event eventOf(LocalDateTime start, String type, Double value) {
        return new CreateEventCommand.Event(start, type, value);
    }
}