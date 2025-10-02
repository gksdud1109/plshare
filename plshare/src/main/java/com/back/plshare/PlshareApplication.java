package com.back.plshare;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class PlshareApplication {

    public static void main(String[] args) {
        SpringApplication.run(PlshareApplication.class, args);
    }

}
