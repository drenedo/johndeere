package me.renedo.johndeere.application;

import java.time.LocalDateTime;
import java.util.UUID;

public record CreateSessionCommand(UUID id, UUID machineId, LocalDateTime start) {
}
