package me.renedo.johndeere.infraestrucure.jpa;

import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Controller;

import me.renedo.johndeere.domain.EventSum;
import me.renedo.johndeere.domain.EventSumRepository;
import me.renedo.johndeere.infraestrucure.jpa.entity.EventSumEntity;
import me.renedo.johndeere.infraestrucure.jpa.repository.EventSumEntityRepository;

@Controller
public class JPAEventSumRepository implements EventSumRepository {

    private final EventSumEntityRepository eventSumEntityRepository;

    public JPAEventSumRepository(EventSumEntityRepository eventSumEntityRepository) {
        this.eventSumEntityRepository = eventSumEntityRepository;
    }

    public Set<EventSum> findByMachineIdAndSessionId(UUID machineId, UUID sessionId) {
        return toDomain(eventSumEntityRepository.findByMachineIdAndSessionId(machineId, sessionId));
    }

    private static Set<EventSum> toDomain(Set<EventSumEntity> entities) {
        return entities.stream().map(JPAEventSumRepository::toDomain).collect(Collectors.toSet());
    }

    private static EventSum toDomain(EventSumEntity entity) {
        return new EventSum(entity.getSessionId(), entity.getMachineId(), entity.getType(), entity.getTotal().doubleValue());
    }
}
