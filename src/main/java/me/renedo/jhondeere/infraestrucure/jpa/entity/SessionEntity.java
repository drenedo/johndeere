package me.renedo.jhondeere.infraestrucure.jpa.entity;

import java.time.LocalDateTime;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity(name = "session")
public class SessionEntity {

    @Id
    private UUID id;

    @Column(name = "machine_id", updatable = false, nullable = false)
    private UUID machineId;

    private LocalDateTime start;

    private LocalDateTime stop;

    public SessionEntity() {
    }

    public SessionEntity(UUID id, UUID machineId, LocalDateTime start, LocalDateTime stop) {
        this.id = id;
        this.machineId = machineId;
        this.start = start;
        this.stop = stop;
    }


    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getMachineId() {
        return machineId;
    }

    public void setMachineId(UUID machineId) {
        this.machineId = machineId;
    }

    public LocalDateTime getStart() {
        return start;
    }

    public void setStart(LocalDateTime start) {
        this.start = start;
    }

    public LocalDateTime getStop() {
        return stop;
    }

    public void setStop(LocalDateTime stop) {
        this.stop = stop;
    }
}
