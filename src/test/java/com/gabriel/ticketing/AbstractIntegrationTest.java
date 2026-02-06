package com.gabriel.ticketing;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;

@SpringBootTest
@Import(TestcontainersConfiguration.class)
public abstract class AbstractIntegrationTest {

    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {

        registry.add(
                "spring.datasource.url",
                TestcontainersConfiguration.POSTGRES::getJdbcUrl
        );
        registry.add(
                "spring.datasource.username",
                TestcontainersConfiguration.POSTGRES::getUsername
        );
        registry.add(
                "spring.datasource.password",
                TestcontainersConfiguration.POSTGRES::getPassword
        );

        registry.add(
                "spring.kafka.bootstrap-servers",
                TestcontainersConfiguration.KAFKA::getBootstrapServers
        );
    }
}
