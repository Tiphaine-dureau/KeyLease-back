package com.infeco.keylease.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloWorld {
    @GetMapping("/helloworld")
    public String getHelloWorld(){
        return "Hello World";
    }
}
