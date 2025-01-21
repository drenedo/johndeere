package me.renedo.johndeere.domain;

import java.time.LocalDateTime;
import java.util.Random;
import java.util.UUID;

import org.apache.commons.lang3.RandomStringUtils;

public class EventMother {

    public static Event any(UUID sessionId) {
        return new Event(sessionId, LocalDateTime.now(), RandomStringUtils.secure().nextAlphanumeric(7), Math.abs(new Random().nextDouble()));
    }
}