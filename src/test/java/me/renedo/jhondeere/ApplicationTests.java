package me.renedo.jhondeere;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import com.github.dockerjava.api.model.ExposedPort;
import com.github.dockerjava.api.model.HostConfig;
import com.github.dockerjava.api.model.PortBinding;
import com.github.dockerjava.api.model.Ports;


@Testcontainers
@SpringBootTest
class ApplicationTests {

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

	@Test
	void contextLoads() {
	}

}
