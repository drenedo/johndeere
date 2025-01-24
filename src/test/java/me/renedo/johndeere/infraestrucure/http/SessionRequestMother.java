package me.renedo.johndeere.infraestrucure.http;

import java.time.LocalDateTime;
import java.util.UUID;

public class SessionRequestMother {

    public static SessionRequest any() {
        return new SessionRequest(UUID.randomUUID(), UUID.randomUUID(), LocalDateTime.now());
    }

    public static SessionRequest anyWithMachineId(UUID machineID) {
        return new SessionRequest(UUID.randomUUID(), machineID, LocalDateTime.now());
    }

    public static SessionRequest of(UUID id, UUID machineID, LocalDateTime now) {
        return new SessionRequest(id, machineID, now);
    }
}