package me.renedo.johndeere.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertThrows;
import static org.mockito.Mockito.doReturn;

import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import me.renedo.johndeere.domain.EventSum;
import me.renedo.johndeere.domain.EventSumMother;
import me.renedo.johndeere.domain.EventSumRepository;

@ExtendWith(MockitoExtension.class)
class FetchTotalsUseCaseTest {

    @Mock
    private EventSumRepository eventSumRepository;

    @InjectMocks
    private FetchTotalsUseCase fetchTotalsUseCase;

    @Test
    public void verify_correct_mapping() {
        //Given
        UUID machineId = UUID.randomUUID();
        UUID sessionId = UUID.randomUUID();
        Set<EventSum> givenEvents = Set.of(EventSumMother.any(), EventSumMother.any());
        doReturn(givenEvents).when(eventSumRepository).findByMachineIdAndSessionId(machineId, sessionId);

        //When
        Set<Total> events = fetchTotalsUseCase.execute(machineId, sessionId);

        //Then
        assertThat(events).usingRecursiveAssertion()
                .isEqualTo(givenEvents.stream().map(eventSum -> new Total(eventSum.getType(), eventSum.getTotal()))
                        .collect(Collectors.toSet()));
    }

    @Test
    public void should_raise_exception_when_machine_id_is_not_present() {
        //When
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> fetchTotalsUseCase.execute(null, UUID.randomUUID()));

        //Then
        assertThat(exception.getMessage()).isEqualTo("MachineId and sessionId are required");
    }
    @Test
    public void should_raise_exception_when_session_id_is_not_present() {
        //When
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> fetchTotalsUseCase.execute(UUID.randomUUID(), null));

        //Then
        assertThat(exception.getMessage()).isEqualTo("MachineId and sessionId are required");
    }
}