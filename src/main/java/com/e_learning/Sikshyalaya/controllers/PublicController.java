package com.e_learning.Sikshyalaya.controllers;
import com.e_learning.Sikshyalaya.entities.User;
import com.e_learning.Sikshyalaya.service.UserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PublicController {
    UserService userService;
    public PublicController(UserService userService) {
        this.userService = userService;
    }
    @GetMapping("/health-check")
    public String healthCheck() {
        return "OK";
    }

    @PostMapping("/signup")
    public void registerUser(@RequestBody User user) {
       userService.saveUser(user);
    }

    @GetMapping
    public String login(){
        return "OK";
    }
}
