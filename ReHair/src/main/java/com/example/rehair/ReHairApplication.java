package com.example.rehair;

import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import java.util.logging.Logger;

@SpringBootApplication
@ComponentScan (basePackages = {"com.example.rehair.controller", "com.example.rehair.dao", "com.example.rehair.model", "com.example.rehair.service", "com.example.rehair.service.impl"})
public class ReHairApplication {

    public static void main(String[] args) {

        SpringApplication.run(ReHairApplication.class, args);
    }

}
