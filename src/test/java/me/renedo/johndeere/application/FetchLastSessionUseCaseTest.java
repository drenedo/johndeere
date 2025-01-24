package me.renedo.johndeere.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doReturn;

import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import me.renedo.johndeere.domain.Session;
import me.renedo.johndeere.domain.SessionMother;
import me.renedo.johndeere.domain.SessionRepository;

@ExtendWith(MockitoExtension.class)
class FetchLastSessionUseCaseTest {

    @Mock
    private SessionRepository sessionRepository;

    @InjectMocks
    private FetchLastSessionUseCase useCase;

    @Test
    public void should_raise_exception_when_machine_id_is_not_present() {
        //When
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> useCase.execute(null));

        //Then
        assertThat(exception.getMessage()).isEqualTo("MachineId are required");
    }

    @Test
    public void should_return_result() {
        // Given
        Session session = SessionMother.any();
        doReturn(Optional.of(session)).when(sessionRepository).findLastByMachineId(session.getMachineId());

        //When
        Optional<LastSession> lastSession = useCase.execute(session.getMachineId());

        //Then
        assertThat(lastSession).isPresent();
        assertThat(lastSession.get().id()).isEqualTo(session.getId());
        assertThat(lastSession.get().start()).isEqualTo(session.getStart());
        assertThat(lastSession.get().stop()).isEmpty();
    }


    @Test
    public void should_not_return_result() {
        //When
        Optional<LastSession> lastSession = useCase.execute(UUID.randomUUID());

        //Then
        assertThat(lastSession).isEmpty();
    }
}