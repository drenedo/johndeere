package me.renedo.jhondeere.domain;

import java.util.UUID;

public class EventSumMother {

    public static EventSum of(UUID sessionId, UUID machineId, String type, Double total) {
        return new EventSum(sessionId, machineId, type, total);
    }

}