package me.renedo.johndeere.infraestrucure.http;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.tags.Tag;
import me.renedo.johndeere.application.CreateSessionCommand;
import me.renedo.johndeere.application.CreateSessionUseCase;

@RestController
@RequestMapping("/api/v1")
@Tag(name = "Session", description = "Save sessions")
public class PostSessionController {

    private final CreateSessionUseCase createSessionUseCase;

    public PostSessionController(CreateSessionUseCase createSessionUseCase) {
        this.createSessionUseCase = createSessionUseCase;
    }

    @PostMapping(value = "/sessions")
    public void saveSession(@RequestBody SessionRequest sessionRequest) {
        createSessionUseCase.execute(toCommand(sessionRequest));
    }

    private CreateSessionCommand toCommand(SessionRequest sessionRequest) {
        return new CreateSessionCommand(sessionRequest.machineId(), sessionRequest.sessionId(), sessionRequest.startAt());
    }

}
