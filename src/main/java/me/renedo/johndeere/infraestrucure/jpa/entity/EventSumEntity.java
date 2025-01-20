package me.renedo.johndeere.infraestrucure.jpa.entity;

import java.math.BigDecimal;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity(name = "event_sum")
public class EventSumEntity {

    @Id
    private Long id;

    @Column(name = "session_id")
    private UUID sessionId;

    @Column(name = "machine_id")
    private UUID machineId;

    private String type;

    @Column(precision = 10, scale = 2)
    private BigDecimal total;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UUID getSessionId() {
        return sessionId;
    }

    public void setSessionId(UUID sessionId) {
        this.sessionId = sessionId;
    }

    public UUID getMachineId() {
        return machineId;
    }

    public void setMachineId(UUID machineId) {
        this.machineId = machineId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }
}
