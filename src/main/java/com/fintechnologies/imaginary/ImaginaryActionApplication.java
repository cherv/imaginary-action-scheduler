package com.fintechnologies.imaginary;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class ImaginaryActionApplication {

    public static void main(String[] args) {
        SpringApplication.run(ImaginaryActionApplication.class, args);
    }
}
