package com.tastemate;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class TasteMateApplication {

    public static void main(String[] args) {
        SpringApplication.run(TasteMateApplication.class, args);
    }

}
