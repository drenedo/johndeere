package me.renedo.johndeere.application;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import me.renedo.johndeere.domain.Event;
import me.renedo.johndeere.domain.EventRepository;

@Component
public class CreateEventUseCase {
    private static final Logger log = LoggerFactory.getLogger(CreateEventUseCase.class);

    private final EventRepository eventRepository;

    public CreateEventUseCase(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    public void execute(CreateEventCommand command) {
        verify(command);
        log.info("Creating event {}", command);
        eventRepository.save(toDomain(command));
    }

    private static List<Event> toDomain(CreateEventCommand command) {
        return command.events().stream()
                .map(e -> new Event(command.sessionId(), e.start(), e.type(), e.value()))
                .collect(Collectors.toList());
    }

    private static void verify (CreateEventCommand command){
        log.info("Validating create event command {}", command);
        if (command == null) {
            throw new IllegalArgumentException("Command is required");
        }
        if(command.sessionId() == null) {
            throw new IllegalArgumentException("Session id is required");
        }
        if(command.events() == null || command.events().isEmpty()) {
            throw new IllegalArgumentException("Events are required");
        }
        command.events().forEach(CreateEventUseCase::verify);
    }

    private static void verify (CreateEventCommand.Event event){
        if(event.start() == null) {
            throw new IllegalArgumentException("Start date is required");
        }
        if(event.type() == null || event.type().isBlank()) {
            throw new IllegalArgumentException("Type is required");
        }
        if(event.value() == null) {
            throw new IllegalArgumentException("Value is required");
        }
    }
}
