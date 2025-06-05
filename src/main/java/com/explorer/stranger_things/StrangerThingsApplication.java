package com.explorer.stranger_things;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class StrangerThingsApplication {

    public static void main(String[] args) {
        SpringApplication.run(StrangerThingsApplication.class, args);
    }

}