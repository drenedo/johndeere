package me.renedo.johndeere.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDateTime;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import me.renedo.johndeere.domain.SessionRepository;

@ExtendWith(MockitoExtension.class)
public class CreateSessionUseCaseTest {

    @Mock
    private SessionRepository sessionRepository;

    @InjectMocks
    private CreateSessionUseCase createSessionUseCase;

    @Test
    public void should_return_exception_when_command_is_null() {
        // When
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> createSessionUseCase.execute(null));

        // Then
        assertThat(exception.getMessage()).isEqualTo("Command is required");
    }

    @Test
    public void should_return_exception_when_start_is_null() {
        // When
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> createSessionUseCase.execute(CreateSessionCommandMother.of(UUID.randomUUID(), UUID.randomUUID(), null))
        );

        // Then
        assertThat(exception.getMessage()).isEqualTo("Start date is required");
    }

    @Test
    public void should_return_exception_when_machine_id_is_null() {
        // When
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> createSessionUseCase.execute(CreateSessionCommandMother.of(UUID.randomUUID(), null, LocalDateTime.now()))
        );

        // Then
        assertThat(exception.getMessage()).isEqualTo("Machine id is required");
    }

    @Test
    public void should_return_exception_when_session_id_is_null() {
        // When
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> createSessionUseCase.execute(CreateSessionCommandMother.of(null, UUID.randomUUID(), LocalDateTime.now()))
        );

        // Then
        assertThat(exception.getMessage()).isEqualTo("Session id is required");
    }

    @Test
    public void use_case_on_creation_should_close_other_sessions() {
        CreateSessionCommand command = CreateSessionCommandMother.any();

        createSessionUseCase.execute(command);

        Mockito.verify(sessionRepository).closeAllSessions(command.machineId());
    }
}