package me.renedo.johndeere.e2e;

import static me.renedo.johndeere.util.Rounder.round;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.withPrecision;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;

import me.renedo.johndeere.application.EventRequestMother;
import me.renedo.johndeere.infraestrucure.http.EventRequest;
import me.renedo.johndeere.infraestrucure.http.SessionRequest;
import me.renedo.johndeere.infraestrucure.http.SessionRequestMother;
import me.renedo.johndeere.infraestrucure.jpa.entity.EventEntity;
import me.renedo.johndeere.infraestrucure.jpa.repository.EventEntityRepository;

public class PutEventEndToEndTest extends BaseEndToEndTest {

    @Autowired
    private EventEntityRepository EventEntityRepository;

    @Test
    public void verify_bad_request_when_creation_is_null() {
        //When
        Assertions.assertThrows(
                HttpClientErrorException.BadRequest.class,
                () -> restTemplate.postForEntity(String.format("http://localhost:%d/api/v1/events", port), null, Void.class)
        );
    }

    @Test
    public void verify_bad_request_when_creation_has_not_id() {
        //Given
        EventRequest EventRequest = EventRequestMother.of(null, 1);

        //When
        Assertions.assertThrows(
                HttpClientErrorException.BadRequest.class,
                () -> restTemplate.postForEntity(String.format("http://localhost:%d/api/v1/events", port), EventRequest, Void.class)
        );
    }

    @Test
    public void verify_bad_request_when_creation_has_not_elements() {
        //Given
        EventRequest EventRequest = EventRequestMother.of(UUID.randomUUID(), 0);

        //When
        Assertions.assertThrows(
                HttpClientErrorException.BadRequest.class,
                () -> restTemplate.postForEntity(String.format("http://localhost:%d/api/v1/events", port), EventRequest, Void.class)
        );
    }

    @Test
    public void verify_bad_request_when_creation_has_a_wrong_element_without_type() {
        //Given
        EventRequest EventRequest = EventRequestMother.of(UUID.randomUUID(), Set.of(EventRequestMother.ofEvent(LocalDateTime.now(), null, 2.0D)));

        //When
        Assertions.assertThrows(
                HttpClientErrorException.BadRequest.class,
                () -> restTemplate.postForEntity(String.format("http://localhost:%d/api/v1/events", port), EventRequest, Void.class)
        );
    }

    @Test
    public void verify_bad_request_when_creation_has_a_wrong_element_without_date() {
        //Given
        EventRequest EventRequest = EventRequestMother.of(UUID.randomUUID(), Set.of(EventRequestMother.ofEvent(null, "some-type", 2.0D)));

        //When
        Assertions.assertThrows(
                HttpClientErrorException.BadRequest.class,
                () -> restTemplate.postForEntity(String.format("http://localhost:%d/api/v1/events", port), EventRequest, Void.class)
        );
    }

    @Test
    public void verify_bad_request_when_creation_has_a_wrong_element_without_value() {
        //Given
        EventRequest EventRequest =
                EventRequestMother.of(UUID.randomUUID(), Set.of(EventRequestMother.ofEvent(LocalDateTime.now(), "some-type", null)));

        //When
        Assertions.assertThrows(
                HttpClientErrorException.BadRequest.class,
                () -> restTemplate.postForEntity(String.format("http://localhost:%d/api/v1/events", port), EventRequest, Void.class)
        );
    }

    @Test
    public void verify_event_creation() {
        //Given
        SessionRequest sessionRequest = SessionRequestMother.any();
        EventRequest eventRequest = EventRequestMother.of(sessionRequest.sessionId(), 5);
        restTemplate.postForEntity(String.format("http://localhost:%d/api/v1/sessions", port), sessionRequest, Void.class);

        //When
        ResponseEntity<Void> response =
                restTemplate.postForEntity(String.format("http://localhost:%d/api/v1/events", port), eventRequest, Void.class);

        //Then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        Set<EventEntity> eventEntity = EventEntityRepository.findAll().stream()
                .filter(e -> e.getSessionId().equals(eventRequest.sessionId())).collect(Collectors.toSet());
        assertThat(eventEntity).hasSize(eventRequest.events().size());
        assertThat(round(eventEntity.stream().mapToDouble(e -> e.getValue().doubleValue()).sum()))
                .isEqualTo(round(eventRequest.events().stream()
                        .mapToDouble(EventRequest.Event::numericEventValue).sum()), withPrecision(0.03D));
    }
}
