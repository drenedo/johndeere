package me.renedo.johndeere.e2e;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;

import me.renedo.johndeere.infraestrucure.http.SessionRequest;
import me.renedo.johndeere.infraestrucure.http.SessionRequestMother;
import me.renedo.johndeere.infraestrucure.jpa.entity.SessionEntity;
import me.renedo.johndeere.infraestrucure.jpa.repository.SessionEntityRepository;

public class PutSessionEndToEndTest extends BaseEndToEndTest {

    @Autowired
    private SessionEntityRepository sessionEntityRepository;

    @Test
    public void verify_bad_request_when_creation_is_null() {
        //When
        Assertions.assertThrows(HttpClientErrorException.BadRequest.class,
                () -> restTemplate.postForEntity(String.format("http://localhost:%d/api/v1/sessions", port), null, Void.class));
    }

    @Test
    public void verify_bad_request_when_creation_has_not_id() {
        //Given
        SessionRequest sessionRequest = SessionRequestMother.of(null, UUID.randomUUID(), LocalDateTime.now());

        //When
        Assertions.assertThrows(HttpClientErrorException.BadRequest.class,
                () -> restTemplate.postForEntity(String.format("http://localhost:%d/api/v1/sessions", port), sessionRequest, Void.class));
    }

    @Test
    public void verify_bad_request_when_creation_has_not_machine_id() {
        //Given
        SessionRequest sessionRequest = SessionRequestMother.of(UUID.randomUUID(), null, LocalDateTime.now());

        //When
        Assertions.assertThrows(HttpClientErrorException.BadRequest.class,
                () -> restTemplate.postForEntity(String.format("http://localhost:%d/api/v1/sessions", port), sessionRequest, Void.class));
    }

    @Test
    public void verify_bad_request_when_creation_has_not_start() {
        //Given
        SessionRequest sessionRequest = SessionRequestMother.of(UUID.randomUUID(), UUID.randomUUID(), null);

        //When
        Assertions.assertThrows(HttpClientErrorException.BadRequest.class,
                () -> restTemplate.postForEntity(String.format("http://localhost:%d/api/v1/sessions", port), sessionRequest, Void.class));
    }

    @Test
    public void verify_session_creation() {
        //Given
        SessionRequest sessionRequest = SessionRequestMother.any();

        //When
        ResponseEntity<Void> response =
                restTemplate.postForEntity(String.format("http://localhost:%d/api/v1/sessions", port), sessionRequest, Void.class);

        //Then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        Optional<SessionEntity> sessionEntity = sessionEntityRepository.findById(sessionRequest.sessionId());
        assertThat(sessionEntity).isPresent();
        assertThat(sessionEntity.get().getMachineId()).isEqualTo(sessionRequest.machineId());
        assertThat(sessionEntity.get().getStart().truncatedTo(ChronoUnit.MILLIS)).isEqualTo(sessionRequest.startAt().truncatedTo(ChronoUnit.MILLIS));
    }
}
