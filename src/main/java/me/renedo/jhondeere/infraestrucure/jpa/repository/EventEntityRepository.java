package me.renedo.jhondeere.infraestrucure.jpa.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import me.renedo.jhondeere.infraestrucure.jpa.entity.EventEntity;

public interface EventEntityRepository extends JpaRepository<EventEntity, UUID> {
}
