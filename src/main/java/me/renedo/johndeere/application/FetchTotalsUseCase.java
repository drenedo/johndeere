package me.renedo.johndeere.application;

import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import me.renedo.johndeere.domain.EventSum;
import me.renedo.johndeere.domain.EventSumRepository;

@Component
public class FetchTotalsUseCase {
    private static final Logger log = LoggerFactory.getLogger(FetchTotalsUseCase.class);

    private final EventSumRepository eventSumRepository;

    public FetchTotalsUseCase(EventSumRepository eventSumRepository) {
        this.eventSumRepository = eventSumRepository;
    }

    public Set<Total> execute(UUID machineId, UUID sessionId) {
        if(machineId == null || sessionId == null) {
            throw new IllegalArgumentException("MachineId and sessionId are required");
        }
        log.info("Fetching totals for machine {} and session {}", machineId, sessionId);
        return toResponse(eventSumRepository.findByMachineIdAndSessionId(machineId, sessionId));
    }

    private static Set<Total> toResponse(Set<EventSum> eventSums) {
        return eventSums.stream().map(eventSum -> new Total(eventSum.getType(), eventSum.getTotal())).collect(Collectors.toSet());
    }
}
