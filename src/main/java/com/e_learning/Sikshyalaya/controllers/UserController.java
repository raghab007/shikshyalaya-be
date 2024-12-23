package com.e_learning.Sikshyalaya.controllers;

import com.e_learning.Sikshyalaya.entities.User;
import com.e_learning.Sikshyalaya.service.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {
    UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/health")
    public String healthCheck(){
        return "health is ok";
    }

//    @GetMapping("/login")
//    public String login(){
//        Authentication authentication =  SecurityContextHolder.getContext().getAuthentication();
//        return "OK";
//    }
}
