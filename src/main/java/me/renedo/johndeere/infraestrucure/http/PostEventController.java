package me.renedo.johndeere.infraestrucure.http;

import java.util.List;
import java.util.Set;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.tags.Tag;
import me.renedo.johndeere.application.CreateEventCommand;
import me.renedo.johndeere.application.CreateEventUseCase;

@RestController
@RequestMapping("/api/v1")
@Tag(name = "Event", description = "Add events to a session")
public class PostEventController {

    private final CreateEventUseCase createEventUseCase;

    public PostEventController(CreateEventUseCase createEventUseCase) {
        this.createEventUseCase = createEventUseCase;
    }

    @PostMapping(value = "/events")
    public void saveEvent(@RequestBody EventRequest eventRequest) {
        createEventUseCase.execute(toCommand(eventRequest));
    }

    private CreateEventCommand toCommand(EventRequest eventRequest) {
        return new CreateEventCommand(eventRequest.sessionId(), toEvents(eventRequest.events()));
    }

    private List<CreateEventCommand.Event> toEvents(Set<EventRequest.Event> events) {
        return events.stream().map(event -> new CreateEventCommand.Event(event.eventAt(), event.eventType(), event.numericEventValue())).toList();
    }
}
