package com.example.bogorodskybot;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ControllerTest {

    @GetMapping("/hello")
    public String hello() {
        return "Hello user";
    }

    @GetMapping("/hello/{name}")
    public String getName(@PathVariable String name) {
        return "Hello " + name;
    }
}