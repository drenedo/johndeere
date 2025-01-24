package me.renedo.johndeere.application;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

public record LastSession(UUID id, LocalDateTime start, Optional<LocalDateTime> stop) {

}
