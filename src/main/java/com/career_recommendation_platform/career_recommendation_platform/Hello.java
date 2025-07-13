package com.career_recommendation_platform.career_recommendation_platform;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Hello {

    @GetMapping("/")
    public String greet(){
        return "hello";
    }
}
