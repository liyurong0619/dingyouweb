package com.example.dweb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling  
@SpringBootApplication
@EntityScan(basePackages = "com.example.dweb.model")
public class DingyouApplication {

    public static void main(String[] args) {
        SpringApplication.run(DingyouApplication.class, args);
    }
}
