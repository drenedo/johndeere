package me.renedo.johndeere.application;

import java.util.Optional;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import me.renedo.johndeere.domain.Session;
import me.renedo.johndeere.domain.SessionRepository;

@Component
public class FetchLastSessionUseCase {
    private static final Logger log = LoggerFactory.getLogger(FetchLastSessionUseCase.class);

    private final SessionRepository sessionRepository;

    public FetchLastSessionUseCase(SessionRepository sessionRepository) {
        this.sessionRepository = sessionRepository;
    }

    public Optional<LastSession> execute(UUID machineId) {
        if (machineId == null) {
            throw new IllegalArgumentException("MachineId are required");
        }
        log.info("Fetching last session for machine {}", machineId);
        return sessionRepository.findLastByMachineId(machineId).map(FetchLastSessionUseCase::toResponse);
    }

    private static LastSession toResponse(Session session) {
        return new LastSession(session.getId(), session.getStart(), session.getStop());
    }
}
