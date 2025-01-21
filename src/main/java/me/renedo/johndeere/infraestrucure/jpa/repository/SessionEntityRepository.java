package me.renedo.johndeere.infraestrucure.jpa.repository;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import me.renedo.johndeere.infraestrucure.jpa.entity.SessionEntity;

@Transactional
public interface SessionEntityRepository extends JpaRepository<SessionEntity, UUID> {

    @Modifying
    @Query("update session s set s.stop = ?1 where s.stop is null and s.machineId = ?2")
    void updateAllOpenedSessionsAndSetDate(LocalDateTime date, UUID machineId);
}
