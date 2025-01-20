package me.renedo.jhondeere.infraestrucure.jpa;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Set;
import java.util.UUID;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import com.github.dockerjava.api.model.ExposedPort;
import com.github.dockerjava.api.model.HostConfig;
import com.github.dockerjava.api.model.PortBinding;
import com.github.dockerjava.api.model.Ports;
import me.renedo.jhondeere.domain.EventSum;
import me.renedo.jhondeere.domain.EventSumMother;
import me.renedo.jhondeere.infraestrucure.jpa.entity.EventEntity;
import me.renedo.jhondeere.infraestrucure.jpa.entity.EventEntityMother;
import me.renedo.jhondeere.infraestrucure.jpa.entity.SessionEntity;
import me.renedo.jhondeere.infraestrucure.jpa.entity.SessionEntityMother;
import me.renedo.jhondeere.infraestrucure.jpa.repository.EventEntityRepository;
import me.renedo.jhondeere.infraestrucure.jpa.repository.SessionEntityRepository;

@Testcontainers
@SpringBootTest
class JPAEventSumRepositoryTest {

    @Container
    static PostgreSQLContainer<?> postgreSQLContainer = new PostgreSQLContainer<>("postgres:17.2")
            .withUsername("test")
            .withPassword("test")
            .withDatabaseName("main")
            .withInitScript("sql/schema.sql")
            .withNetworkAliases("pg")
            .withExposedPorts(5432)
            .withCreateContainerCmdModifier(cmd -> cmd.withHostConfig(
                    new HostConfig().withPortBindings(new PortBinding(Ports.Binding.bindPort(15432), new ExposedPort(5432)))
            ));

    @Autowired
    private JPAEventSumRepository jpaEventSumRepository;

    @Autowired
    private EventEntityRepository eventRepository;

    @Autowired
    private SessionEntityRepository sessionRepository;

    @Test
    void ensure_sum_of_events_are_calculated_correctly() {
        // Given
        SessionEntity session = sessionRepository.save(SessionEntityMother.any(UUID.randomUUID(), UUID.randomUUID()));
        EventEntity event1 = eventRepository.save(EventEntityMother.any(session.getId()));
        EventEntity event2 = eventRepository.save(EventEntityMother.any(session.getId()));
        EventEntity event3 = eventRepository.save(EventEntityMother.any(session.getId()));

        // When
        Set<EventSum> events = jpaEventSumRepository.findByMachineIdAndSessionId(session.getMachineId(), session.getId());

        // Then
        Assertions.assertThat(events).usingRecursiveAssertion().isEqualTo(Set.of(EventSumMother.of(session.getId(), session.getMachineId(), event1.getType(),
                round(event1.getValue().doubleValue() + event2.getValue().doubleValue() + event3.getValue().doubleValue())
        )));
    }

    private static Double round(double value) {
        BigDecimal bd = BigDecimal.valueOf(value);
        bd = bd.setScale(2, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }
}