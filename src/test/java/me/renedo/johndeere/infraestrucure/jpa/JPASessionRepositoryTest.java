package me.renedo.johndeere.infraestrucure.jpa;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import me.renedo.johndeere.JPARepositoryTest;
import me.renedo.johndeere.infraestrucure.jpa.entity.SessionEntity;
import me.renedo.johndeere.infraestrucure.jpa.entity.SessionEntityMother;
import me.renedo.johndeere.infraestrucure.jpa.repository.SessionEntityRepository;

class JPASessionRepositoryTest extends JPARepositoryTest {

    @Autowired
    private SessionEntityRepository sessionRepository;

    @Autowired
    private JPASessionRepository repository;

    @Test
    void ensure_close_all_opened_sessions_by_machine() {
        // Given
        UUID machineId = UUID.randomUUID();
        UUID anotherMachineID = UUID.randomUUID();
        sessionRepository.save(SessionEntityMother.any(UUID.randomUUID(), machineId));
        sessionRepository.save(SessionEntityMother.any(UUID.randomUUID(), machineId));
        sessionRepository.save(SessionEntityMother.any(UUID.randomUUID(), anotherMachineID));

        // When
        repository.closeAllSessions(machineId);

        // Then
        List<SessionEntity> all = sessionRepository.findAll();
        assertThat(all.stream().filter(s -> s.getMachineId().equals(machineId))).allMatch(session -> session.getStop() != null);
        assertThat(all.stream().filter(s -> !s.getMachineId().equals(machineId))).allMatch(session -> session.getStop() == null);
    }


}