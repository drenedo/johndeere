package me.renedo.johndeere.infraestrucure.jpa;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Controller;

import me.renedo.johndeere.domain.Event;
import me.renedo.johndeere.domain.EventRepository;
import me.renedo.johndeere.infraestrucure.jpa.entity.EventEntity;
import me.renedo.johndeere.infraestrucure.jpa.repository.EventEntityRepository;

@Controller
public class JPAEventRepository implements EventRepository {

    private final EventEntityRepository repository;

    public JPAEventRepository(EventEntityRepository eventEntityRepository) {
        this.repository = eventEntityRepository;
    }

    @Override
    public void save(List<Event> events) {
        repository.saveAll(toEntity(events));
    }

    private static List<EventEntity> toEntity(List<Event> events) {
        return events.stream().map(JPAEventRepository::toEntity).toList();
    }

    private static EventEntity toEntity(Event event) {
        return new EventEntity(UUID.randomUUID(), event.getSessionId(), event.getType(), BigDecimal.valueOf(event.getValue()));
    }
}
