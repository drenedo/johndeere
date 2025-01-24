package me.renedo.johndeere.e2e;

import static me.renedo.johndeere.util.Rounder.round;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.withPrecision;

import java.util.Arrays;

import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

import me.renedo.johndeere.application.EventRequestMother;
import me.renedo.johndeere.infraestrucure.http.EventRequest;
import me.renedo.johndeere.infraestrucure.http.SessionRequest;
import me.renedo.johndeere.infraestrucure.http.SessionRequestMother;
import me.renedo.johndeere.infraestrucure.http.TotalResponse;

public class FetchTotalEndToEndTest extends BaseEndToEndTest {

    @Test
    public void verify_simple_creation_of_totals() {
        //Given
        SessionRequest sessionRequest = SessionRequestMother.any();
        EventRequest eventRequest = EventRequestMother.of(sessionRequest.sessionId(), 5);
        restTemplate.postForEntity(String.format("http://localhost:%d/api/v1/sessions", port), sessionRequest, Void.class);
        restTemplate.postForEntity(String.format("http://localhost:%d/api/v1/events", port), eventRequest, Void.class);

        //When
        ResponseEntity<TotalResponse[]> response = restTemplate.getForEntity(
                String.format("http://localhost:%d/api/v1/machines/%s/sessions/%s/totals", port, sessionRequest.machineId(),
                        sessionRequest.sessionId()
                ), TotalResponse[].class);

        //Then
        assertThat(response.getStatusCode().is2xxSuccessful()).isTrue();
        assertThat(response.getBody()).isNotNull();
        assertThat(Arrays.stream(response.getBody()).mapToDouble(TotalResponse::value).sum())
                .isEqualTo(round(eventRequest.events().stream().mapToDouble(EventRequest.Event::numericEventValue).sum()), withPrecision(0.03D));
    }

    @Test
    public void verify_sum_of_totals() {
        //Given
        SessionRequest sessionRequest = SessionRequestMother.any();
        EventRequest eventRequest = EventRequestMother.of(sessionRequest.sessionId(), "some-type", 250);
        restTemplate.postForEntity(String.format("http://localhost:%d/api/v1/sessions", port), sessionRequest, Void.class);
        restTemplate.postForEntity(String.format("http://localhost:%d/api/v1/events", port), eventRequest, Void.class);

        //When
        ResponseEntity<TotalResponse[]> response = restTemplate.getForEntity(
                String.format("http://localhost:%d/api/v1/machines/%s/sessions/%s/totals", port, sessionRequest.machineId(),
                        sessionRequest.sessionId()
                ), TotalResponse[].class);

        //Then
        assertThat(response.getStatusCode().is2xxSuccessful()).isTrue();
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody()).hasSize(1);
        assertThat(response.getBody()[0].value())
                .isEqualTo(round(eventRequest.events().stream().mapToDouble(EventRequest.Event::numericEventValue).sum()), withPrecision(0.09D));

    }
}
