package me.renedo.jhondeere.infraestrucure.jpa.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import me.renedo.jhondeere.infraestrucure.jpa.entity.SessionEntity;

public interface SessionEntityRepository extends JpaRepository<SessionEntity, UUID> {
}
