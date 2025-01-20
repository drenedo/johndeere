package me.renedo.johndeere.infraestrucure.jpa.repository;

import java.util.Set;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import me.renedo.johndeere.infraestrucure.jpa.entity.EventSumEntity;

public interface EventSumEntityRepository extends JpaRepository<EventSumEntity, Long> {

    Set<EventSumEntity> findByMachineIdAndSessionId(UUID machineId, UUID sessionId);
}
