package me.renedo.jhondeere.domain;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

public class Session {
    private final UUID id;
    private final UUID machineId;
    private final LocalDateTime start;
    private final LocalDateTime stop;

    public Session(UUID id, UUID machineId, LocalDateTime start, LocalDateTime stop) {
        this.id = id;
        this.machineId = machineId;
        this.start = start;
        this.stop = stop;
    }


    public UUID getId() {
        return id;
    }

    public UUID getMachineId() {
        return machineId;
    }

    public LocalDateTime getStart() {
        return start;
    }

    public Optional<LocalDateTime> getStop() {
        return Optional.ofNullable(stop);
    }
}
