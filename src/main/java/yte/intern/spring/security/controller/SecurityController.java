package yte.intern.spring.security.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SecurityController {

    @GetMapping("/hello")
    public String hello(){
        return "Hellooooooo";
    }

    @GetMapping("/admin")
    public String admin(){
        return "Hi, I am Admin";
    }

    @GetMapping("/user")
    public String user(){
        return "Hello, I am User";
    }
}
