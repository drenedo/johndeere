package me.renedo.johndeere.application;

import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import me.renedo.johndeere.domain.Session;
import me.renedo.johndeere.domain.SessionRepository;

@Component
public class CreateSessionUseCase {
    private static final Logger log = LoggerFactory.getLogger(CreateSessionUseCase.class);

    private final SessionRepository sessionRepository;

    public CreateSessionUseCase(SessionRepository sessionRepository) {
        this.sessionRepository = sessionRepository;
    }

    public void execute(CreateSessionCommand command) {
        verify(command);
        log.info("Close opened sessions for machine {}", command.machineId());
        closeAllOpenedPreviousSessions(command.machineId());
        log.info("Creating session {}", command);
        sessionRepository.save(toDomain(command));
    }

    private void closeAllOpenedPreviousSessions(UUID machineId) {
        sessionRepository.closeAllSessions(machineId);
    }

    private static Session toDomain(CreateSessionCommand command) {
        return new Session(command.id(), command.machineId(), command.start(), null);
    }

    private static void verify(CreateSessionCommand command) {
        log.info("Validating create session command {}", command);
        if(command == null) {
            throw new IllegalArgumentException("Command is required");
        }
        if(command.id() == null) {
            throw new IllegalArgumentException("Session id is required");
        }
        if(command.machineId() == null) {
            throw new IllegalArgumentException("Machine id is required");
        }
        if(command.start() == null) {
            throw new IllegalArgumentException("Start date is required");
        }
    }
}
