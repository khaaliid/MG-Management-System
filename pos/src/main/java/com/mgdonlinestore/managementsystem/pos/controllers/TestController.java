package com.mgdonlinestore.managementsystem.pos.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @GetMapping("/sayHello")
    public String sayHello(){
        System.out.println("one line added");
        return "Hello From POS module";
    }
}
