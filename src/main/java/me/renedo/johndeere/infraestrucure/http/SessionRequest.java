package me.renedo.johndeere.infraestrucure.http;

import java.time.LocalDateTime;
import java.util.UUID;


public record SessionRequest (UUID sessionId, UUID machineId, LocalDateTime startAt) {

}
