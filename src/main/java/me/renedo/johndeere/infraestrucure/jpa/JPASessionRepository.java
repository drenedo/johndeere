package me.renedo.johndeere.infraestrucure.jpa;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Controller;

import me.renedo.johndeere.domain.Session;
import me.renedo.johndeere.domain.SessionRepository;
import me.renedo.johndeere.infraestrucure.jpa.entity.SessionEntity;
import me.renedo.johndeere.infraestrucure.jpa.repository.SessionEntityRepository;

@Controller
public class JPASessionRepository implements SessionRepository {

    private final SessionEntityRepository repository;

    public JPASessionRepository(SessionEntityRepository repository) {
        this.repository = repository;
    }

    @Override
    public void save(Session session) {
        repository.save(toEntity(session));
    }

    @Override
    public void closeAllSessions(UUID machineId) {
        repository.updateAllOpenedSessionsAndSetDate(LocalDateTime.now(), machineId);
    }

    @Override
    public Optional<Session> findLastByMachineId(UUID machineId) {
        return repository.findLastByMachineId(machineId).map(JPASessionRepository::toDomain);
    }

    private static Session toDomain(SessionEntity sessionEntity) {
        return new Session(
                sessionEntity.getId(), sessionEntity.getMachineId(), sessionEntity.getStart(), sessionEntity.getStop());
    }

    private static SessionEntity toEntity(Session session) {
        return new SessionEntity(session.getId(), session.getMachineId(), session.getStart(), session.getStop().orElse(null));
    }
}
