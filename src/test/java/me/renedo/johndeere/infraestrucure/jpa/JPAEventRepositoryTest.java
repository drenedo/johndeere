package me.renedo.johndeere.infraestrucure.jpa;

import static me.renedo.johndeere.util.Rounder.round;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import me.renedo.johndeere.JPARepositoryTest;
import me.renedo.johndeere.domain.Event;
import me.renedo.johndeere.domain.EventMother;
import me.renedo.johndeere.infraestrucure.jpa.entity.EventEntity;
import me.renedo.johndeere.infraestrucure.jpa.entity.SessionEntity;
import me.renedo.johndeere.infraestrucure.jpa.entity.SessionEntityMother;
import me.renedo.johndeere.infraestrucure.jpa.repository.EventEntityRepository;
import me.renedo.johndeere.infraestrucure.jpa.repository.SessionEntityRepository;

class JPAEventRepositoryTest extends JPARepositoryTest {

    @Autowired
    private EventEntityRepository eventEntityRepository;

    @Autowired
    private JPAEventRepository eventRepository;

    @Autowired
    private SessionEntityRepository sessionEntityRepository;

    @Test
    public void ensure_events_store_right() {
        // Given
        SessionEntity session = SessionEntityMother.any();
        sessionEntityRepository.save(session);
        List<Event> events = List.of(EventMother.any(session.getId()), EventMother.any(session.getId()));

        // When
        eventRepository.save(events);

        // Then
        List<EventEntity> all = eventEntityRepository.findAll();
        assertThat(all).hasSize(2);
        assertThat(all.getFirst().getType()).isEqualTo(events.getFirst().getType());
        assertThat(all.getFirst().getValue().doubleValue()).isEqualTo(round(events.getFirst().getValue()));
        assertThat(all.get(1).getType()).isEqualTo(events.get(1).getType());
        assertThat(all.get(1).getValue().doubleValue()).isEqualTo(round(events.get(1).getValue()));
    }
}