package com.e_learning.Sikshyalaya.controllers;

import com.e_learning.Sikshyalaya.dtos.RequestUser;
import com.e_learning.Sikshyalaya.entities.User;
import com.e_learning.Sikshyalaya.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping()
public class PublicController {
    @Autowired
   private UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@RequestBody User user) {
       Optional<User> _oldUser_ = userService.getByUserName(user.getUserName());
       if (_oldUser_.isEmpty()){
           userService.saveUser(user);
           return new ResponseEntity<String>("true",HttpStatus.CREATED);
       }else {
           return new ResponseEntity<String>("false",HttpStatus.OK);
       }
    }
    @PostMapping("/login")
    public String login(@RequestBody RequestUser user){
       return userService.verify(user);
    }
}
