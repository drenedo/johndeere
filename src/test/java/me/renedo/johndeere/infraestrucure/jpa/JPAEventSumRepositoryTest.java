package me.renedo.johndeere.infraestrucure.jpa;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Set;
import java.util.UUID;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import me.renedo.johndeere.JPARepositoryTest;
import me.renedo.johndeere.domain.EventSum;
import me.renedo.johndeere.domain.EventSumMother;
import me.renedo.johndeere.infraestrucure.jpa.entity.EventEntity;
import me.renedo.johndeere.infraestrucure.jpa.entity.EventEntityMother;
import me.renedo.johndeere.infraestrucure.jpa.entity.SessionEntity;
import me.renedo.johndeere.infraestrucure.jpa.entity.SessionEntityMother;
import me.renedo.johndeere.infraestrucure.jpa.repository.EventEntityRepository;
import me.renedo.johndeere.infraestrucure.jpa.repository.SessionEntityRepository;

class JPAEventSumRepositoryTest extends JPARepositoryTest {

    @Autowired
    private JPAEventSumRepository jpaEventSumRepository;

    @Autowired
    private EventEntityRepository eventRepository;

    @Autowired
    private SessionEntityRepository sessionRepository;

    @Test
    void ensure_sum_of_events_are_calculated_correctly() {
        // Given
        SessionEntity session = sessionRepository.save(SessionEntityMother.any(UUID.randomUUID(), UUID.randomUUID()));
        EventEntity event1 = eventRepository.save(EventEntityMother.any(session.getId()));
        EventEntity event2 = eventRepository.save(EventEntityMother.any(session.getId()));
        EventEntity event3 = eventRepository.save(EventEntityMother.any(session.getId()));

        // When
        Set<EventSum> events = jpaEventSumRepository.findByMachineIdAndSessionId(session.getMachineId(), session.getId());

        // Then
        Assertions.assertThat(events).usingRecursiveAssertion().isEqualTo(Set.of(EventSumMother.of(session.getId(), session.getMachineId(), event1.getType(),
                round(event1.getValue().doubleValue() + event2.getValue().doubleValue() + event3.getValue().doubleValue())
        )));
    }

    private static Double round(double value) {
        BigDecimal bd = BigDecimal.valueOf(value);
        bd = bd.setScale(2, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }
}