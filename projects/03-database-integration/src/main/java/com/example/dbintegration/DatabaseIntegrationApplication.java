package com.example.dbintegration;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing // Enable JPA auditing
public class DatabaseIntegrationApplication {

    public static void main(String[] args) {
        SpringApplication.run(DatabaseIntegrationApplication.class, args);
    }
}