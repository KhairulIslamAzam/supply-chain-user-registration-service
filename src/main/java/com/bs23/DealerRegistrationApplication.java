package com.bs23;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class DealerRegistrationApplication {

    public static void main(String[] args) {
        SpringApplication.run(DealerRegistrationApplication.class, args);
    }

}
