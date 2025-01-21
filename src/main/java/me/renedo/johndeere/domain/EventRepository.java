package me.renedo.johndeere.domain;

import java.util.List;

public interface EventRepository {

    void save(List<Event> events);
}
