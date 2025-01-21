package me.renedo.johndeere.infraestrucure.http;

import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.tags.Tag;
import me.renedo.johndeere.application.FetchTotalsUseCase;
import me.renedo.johndeere.application.Total;

@RestController
@RequestMapping("/api/v1")
@Tag(name = "Totals", description = "Retrieve totals for a given machine and session")
public class FetchTotalController {

    private final FetchTotalsUseCase fetchTotalsUseCase;

    public FetchTotalController(FetchTotalsUseCase fetchTotalsUseCase) {
        this.fetchTotalsUseCase = fetchTotalsUseCase;
    }

    @GetMapping(value = "/total/machines/{machineId}/sessions/{sessionId}")
    public Set<TotalResponse> fetchTotal(@PathVariable("machineId") UUID machineId, @PathVariable("sessionId") UUID sessionId) {
        return toResponse(fetchTotalsUseCase.execute(machineId, sessionId));
    }

    private static Set<TotalResponse> toResponse(Set<Total> execute) {
        return execute.stream().map(total -> new TotalResponse(total.type(), total.value())).collect(Collectors.toSet());
    }
}
