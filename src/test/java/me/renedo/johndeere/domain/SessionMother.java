package me.renedo.johndeere.domain;


import java.time.LocalDateTime;
import java.util.UUID;

public class SessionMother {

    public static Session any() {
        return new Session(UUID.randomUUID(), UUID.randomUUID(), LocalDateTime.now(), null);
    }
}