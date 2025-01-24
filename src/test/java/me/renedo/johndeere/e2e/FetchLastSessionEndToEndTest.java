package me.renedo.johndeere.e2e;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.temporal.ChronoUnit;
import java.util.UUID;
import java.util.stream.IntStream;

import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;

import me.renedo.johndeere.application.LastSession;
import me.renedo.johndeere.infraestrucure.http.LastSessionResponse;
import me.renedo.johndeere.infraestrucure.http.SessionRequest;
import me.renedo.johndeere.infraestrucure.http.SessionRequestMother;

public class FetchLastSessionEndToEndTest extends BaseEndToEndTest {

    @Test
    public void verify_last_session_when_there_are_not_sessions() {
        //When
        assertThrows(
                HttpClientErrorException.NotFound.class,
                () -> restTemplate.getForEntity(
                        String.format("http://localhost:%d/api/v1/machines/%s/last-session", port, UUID.randomUUID()), LastSession.class)
        );
    }

    @Test
    public void verify_fetch_correct_session() {
        //Given
        UUID machineId = UUID.randomUUID();
        IntStream.range(0, 10).forEach(i -> restTemplate.postForEntity(
                String.format("http://localhost:%d/api/v1/sessions", port), SessionRequestMother.anyWithMachineId(machineId), Void.class));
        SessionRequest lastSession = SessionRequestMother.anyWithMachineId(machineId);
        restTemplate.postForEntity(
                String.format("http://localhost:%d/api/v1/sessions", port), lastSession, Void.class);

        //When;
        ResponseEntity<LastSessionResponse> sessionReturned = restTemplate.getForEntity(
                String.format("http://localhost:%d/api/v1/machines/%s/last-session", port, machineId), LastSessionResponse.class);

        //Then
        assertThat(sessionReturned.getBody()).isNotNull();
        assertThat(sessionReturned.getBody().id()).isEqualTo(lastSession.sessionId());
        assertThat(sessionReturned.getBody().start().truncatedTo(ChronoUnit.MILLIS)).isEqualTo(lastSession.startAt().truncatedTo(ChronoUnit.MILLIS));
    }
}
