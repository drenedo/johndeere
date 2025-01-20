package me.renedo.johndeere.infraestrucure.jpa.entity;

import java.math.BigDecimal;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity(name = "event")
public class EventEntity {

    @Id
    private UUID id;

    @Column(name = "session_id")
    private UUID sessionId;

    private String type;

    @Column(precision = 10, scale = 2)
    private BigDecimal value;

    public EventEntity() {
    }

    public EventEntity(UUID id, UUID sessionId, String type, BigDecimal value) {
        this.id = id;
        this.sessionId = sessionId;
        this.type = type;
        this.value = value;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getSessionId() {
        return sessionId;
    }

    public void setSessionId(UUID sessionId) {
        this.sessionId = sessionId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public BigDecimal getValue() {
        return value;
    }

    public void setValue(BigDecimal value) {
        this.value = value;
    }
}
