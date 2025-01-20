package me.renedo.jhondeere.domain;

import java.util.Random;
import java.util.UUID;

import org.apache.commons.lang3.RandomStringUtils;

public class EventSumMother {


    public static EventSum any() {
        return new EventSum(UUID.randomUUID(), UUID.randomUUID(), RandomStringUtils.secure().nextAlphanumeric(7), new Random().nextDouble());
    }

    public static EventSum of(UUID sessionId, UUID machineId, String type, Double total) {
        return new EventSum(sessionId, machineId, type, total);
    }

}