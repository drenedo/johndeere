package me.renedo.johndeere.infraestrucure.jpa.entity;

import java.time.LocalDateTime;
import java.util.UUID;

public class SessionEntityMother {

    public static SessionEntity any(UUID id, UUID machineId) {
        return new SessionEntity(id, machineId, LocalDateTime.now(), null);
    }
}