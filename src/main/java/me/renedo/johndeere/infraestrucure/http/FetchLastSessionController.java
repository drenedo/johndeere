package me.renedo.johndeere.infraestrucure.http;

import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.tags.Tag;
import me.renedo.johndeere.application.FetchLastSessionUseCase;
import me.renedo.johndeere.application.LastSession;

@RestController
@RequestMapping("/api/v1")
@Tag(name = "Last session", description = "Retrieve the last session of a given machine")
public class FetchLastSessionController {

    private final FetchLastSessionUseCase fetchLastSessionUseCase;

    public FetchLastSessionController(FetchLastSessionUseCase fetchLastSessionUseCase) {
        this.fetchLastSessionUseCase = fetchLastSessionUseCase;
    }

    @GetMapping(value = "/machines/{machineId}/last-session")
    public ResponseEntity<LastSessionResponse> fetchTotal(@PathVariable("machineId") UUID machineId) {
        return fetchLastSessionUseCase.execute(machineId)
                .map(FetchLastSessionController::toResponse)
                .orElse(ResponseEntity.notFound().build());
    }

    private static ResponseEntity<LastSessionResponse> toResponse(LastSession lastSession) {
        return ResponseEntity.ok(new LastSessionResponse(lastSession.id(), lastSession.start(), lastSession.stop().orElse(null)));
    }
}
