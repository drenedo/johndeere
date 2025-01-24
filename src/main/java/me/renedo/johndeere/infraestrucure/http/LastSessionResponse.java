package me.renedo.johndeere.infraestrucure.http;

import java.time.LocalDateTime;
import java.util.UUID;

public record LastSessionResponse(UUID id, LocalDateTime start, LocalDateTime stop) {
}
