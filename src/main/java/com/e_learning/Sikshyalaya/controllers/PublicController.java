package com.e_learning.Sikshyalaya.controllers;
import com.e_learning.Sikshyalaya.entities.User;
import com.e_learning.Sikshyalaya.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/public")
public class PublicController {
    UserService userService;
    public PublicController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@RequestBody User user) {
       User oldUser = userService.getByUserName(user.getUserName());
       if (oldUser!=null){
           return new ResponseEntity<String>("false",HttpStatus.OK);
       }else {
           userService.saveUser(user);
           return new ResponseEntity<String>("true",HttpStatus.CREATED);
       }
    }
    @PostMapping
    public String login(User user){
       return userService.verify(user);
    }
}
