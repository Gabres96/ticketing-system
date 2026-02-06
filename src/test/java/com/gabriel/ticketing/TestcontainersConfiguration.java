package com.gabriel.ticketing;

import org.springframework.boot.test.context.TestConfiguration;
import org.testcontainers.containers.KafkaContainer;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.utility.DockerImageName;

@TestConfiguration
public class TestcontainersConfiguration {

	static final PostgreSQLContainer<?> POSTGRES =
			new PostgreSQLContainer<>("postgres:16-alpine")
					.withDatabaseName("ticketing")
					.withUsername("test")
					.withPassword("test");

	static final KafkaContainer KAFKA =
			new KafkaContainer(
					DockerImageName.parse("confluentinc/cp-kafka:7.6.1")
			);

	static {
		POSTGRES.start();
		KAFKA.start();
	}
}
