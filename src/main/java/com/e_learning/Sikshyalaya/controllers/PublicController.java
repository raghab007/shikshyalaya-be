package com.e_learning.Sikshyalaya.controllers;
import com.e_learning.Sikshyalaya.entities.User;
import com.e_learning.Sikshyalaya.service.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/public")
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
}
