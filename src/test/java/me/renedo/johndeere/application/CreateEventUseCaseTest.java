package me.renedo.johndeere.application;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import me.renedo.johndeere.domain.EventRepository;

@ExtendWith(MockitoExtension.class)
class CreateEventUseCaseTest {

    @Mock
    private EventRepository eventRepository;

    @InjectMocks
    private CreateEventUseCase createEventUseCase;

    @Test
    public void should_return_exception_when_command_is_null() {
        // When
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> createEventUseCase.execute(null));

        // Then
        assertEquals("Command is required", exception.getMessage());
    }

    @Test
    public void should_return_exception_when_session_id_is_null() {
        // When
        IllegalArgumentException exception =
                assertThrows(
                        IllegalArgumentException.class,
                        () -> createEventUseCase.execute(CreateEventCommandMother.of(null, List.of(CreateEventCommandMother.anyEvent())))
                );

        // Then
        assertEquals("Session id is required", exception.getMessage());
    }

    @Test
    public void should_return_exception_when_events_is_null() {
        // When
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> createEventUseCase.execute(
                CreateEventCommandMother.of(UUID.randomUUID(), null)));

        // Then
        assertEquals("Events are required", exception.getMessage());
    }

    @Test
    public void should_return_exception_when_events_is_empty() {
        // When
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> createEventUseCase.execute(new CreateEventCommand(
                UUID.randomUUID(), List.of())));

        // Then
        assertEquals("Events are required", exception.getMessage());
    }

    @Test
    public void should_return_exception_when_events_has_not_() {
        // When
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> createEventUseCase.execute(new CreateEventCommand(
                UUID.randomUUID(), List.of(CreateEventCommandMother.eventOf(null, "type", 1D)))));

        // Then
        assertEquals("Start date is required", exception.getMessage());
    }

    @Test
    public void should_return_exception_when_events_has_not_type() {
        // When
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> createEventUseCase.execute(new CreateEventCommand(
                UUID.randomUUID(), List.of(CreateEventCommandMother.eventOf(LocalDateTime.now(), null, 1D)))));

        // Then
        assertEquals("Type is required", exception.getMessage());
    }

    @Test
    public void should_return_exception_when_events_has_not_value() {
        // When
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> createEventUseCase.execute(new CreateEventCommand(
                UUID.randomUUID(), List.of(CreateEventCommandMother.eventOf(LocalDateTime.now(), "type", null)))));

        // Then
        assertEquals("Value is required", exception.getMessage());
    }
}