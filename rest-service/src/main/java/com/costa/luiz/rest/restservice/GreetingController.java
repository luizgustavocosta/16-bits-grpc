package com.costa.luiz.rest.restservice;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/rest/api/greeting")
public class GreetingController {

    @GetMapping("sayHello")
    public String sayHello() {
        return "Hello there at " + LocalDateTime.now();
    }
}
