package me.renedo.johndeere.infraestrucure.jpa.entity;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Random;
import java.util.UUID;

public class EventEntityMother {

    public static EventEntity any(UUID sessionId) {
        return new EventEntity(UUID.randomUUID(), sessionId, "some-type",
                BigDecimal.valueOf(new Random().nextFloat() * (200) + 0).setScale(2, RoundingMode.HALF_UP)
        );
    }
}