package com.rk;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
public class ProjectIntershipApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProjectIntershipApplication.class, args);
    }

}
