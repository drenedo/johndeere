package me.renedo.johndeere.infraestrucure.jpa.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import me.renedo.johndeere.infraestrucure.jpa.entity.SessionEntity;

public interface SessionEntityRepository extends JpaRepository<SessionEntity, UUID> {
}
