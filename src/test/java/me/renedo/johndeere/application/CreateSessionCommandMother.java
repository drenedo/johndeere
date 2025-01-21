package me.renedo.johndeere.application;

import java.time.LocalDateTime;
import java.util.UUID;

public class CreateSessionCommandMother {

    public static CreateSessionCommand any(){
        return of(UUID.randomUUID(), UUID.randomUUID(), LocalDateTime.now());
    }

    public static CreateSessionCommand of(UUID id, UUID machineId, LocalDateTime start){
        return new CreateSessionCommand(id, machineId, start);
    }
}