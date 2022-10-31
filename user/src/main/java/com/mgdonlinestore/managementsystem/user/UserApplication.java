package com.mgdonlinestore.managementsystem.user;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author Khaled Elgohary
 */

@ComponentScan({"com.mgdonlinestore.managementsystem"})
@SpringBootApplication
public class UserApplication {


    public static void main(String[] args) {
        SpringApplication.run(UserApplication.class, args);
    }

}

