package com.mgdonlinestore.managementsystem.vendor;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author Khaled Elgohary
 */

@ComponentScan({"com.mgdonlinestore.managementsystem"})
@SpringBootApplication
public class VendorApplication {


    public static void main(String[] args) {
        SpringApplication.run(VendorApplication.class, args);
    }

}

