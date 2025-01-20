package me.renedo.jhondeere.domain;

import java.util.Set;
import java.util.UUID;

public interface EventSumRepository {

    Set<EventSum> findByMachineIdAndSessionId(UUID machineId, UUID sessionId);
}
