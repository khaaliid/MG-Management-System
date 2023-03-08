package com.mgdonlinestore.managementsystem.product;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author Khaled Elgohary
 */

@ComponentScan({"com.mgdonlinestore.managementsystem"})
@SpringBootApplication
public class ProductApplication {


    public static void main(String[] args) {
        SpringApplication.run(ProductApplication.class, args);
    }

}

