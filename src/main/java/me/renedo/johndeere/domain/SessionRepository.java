package me.renedo.johndeere.domain;

import java.util.Optional;
import java.util.UUID;

public interface SessionRepository {

    void save(Session session);

    void closeAllSessions(UUID machineId);

    Optional<Session> findLastByMachineId(UUID machineId);
}
