package me.renedo.jhondeere;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;


@Testcontainers
@SpringBootTest
class ApplicationTests {

    @Container
    static PostgreSQLContainer<?> postgreSQLContainer = new PostgreSQLContainer<>("postgres:17.2")
            .withUsername("test")
            .withPassword("test")
            .withDatabaseName("main")
            .withInitScript("sql/schema.sql")
            .withNetworkAliases("pg");

	@Test
	void contextLoads() {
	}

}
