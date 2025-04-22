package com.e_learning.Sikshyalaya.controllers;

import com.e_learning.Sikshyalaya.dtos.LoginResponse;
import com.e_learning.Sikshyalaya.dtos.RequestUser;
import com.e_learning.Sikshyalaya.dtos.RequestUserDto;
import com.e_learning.Sikshyalaya.dtos.UserResponseDto;
import com.e_learning.Sikshyalaya.entities.TestObject;
import com.e_learning.Sikshyalaya.entities.User;
import com.e_learning.Sikshyalaya.service.InstructorService;
import com.e_learning.Sikshyalaya.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

@RestController
public class PublicController {
    @Autowired
    private UserService userService;

    @Autowired
    InstructorService instructorService;

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@RequestBody RequestUserDto userRequestDto) {
        Optional<User> _oldUser_ = userService.getByUserName(userRequestDto.getUserName());
        User user = new User(userRequestDto);
        if (_oldUser_.isEmpty()) {
            userService.saveUser(user);
            return new ResponseEntity<String>("true", HttpStatus.CREATED);
        } else {
            return new ResponseEntity<String>("false", HttpStatus.OK);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody RequestUser user) {
        Optional<User> byUserName = userService.getByUserName(user.getUserName());
        if (byUserName.get().isBlocked()){
            LoginResponse loginResponse = new LoginResponse();
            loginResponse.setMessage("You have been blocked by the admin!");
            return new ResponseEntity<>(loginResponse,HttpStatus.OK);
        }
        if (byUserName.isEmpty()) {
            LoginResponse loginResponse = new LoginResponse();
            loginResponse.setMessage("Username not found");
            return new ResponseEntity<>(loginResponse, HttpStatus.OK);
        }
        LoginResponse response = userService.verify(user);
        if (response.getToken() == null) {
            response.setMessage("Login password incorrect...");
            return new ResponseEntity<>(response, HttpStatus.OK);
        }

        return new ResponseEntity<>(response, HttpStatus.OK);

    }

    @GetMapping("/user")
    public UserResponseDto getUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        System.out.println(username);
        System.out.println("Hello -------");
        Optional<User> optionalUser = userService.getByUserName(username);
        User user = optionalUser.get();
        return new UserResponseDto(user);
    }

    @GetMapping("/testAPI")
    public String testAPI(@ModelAttribute TestObject testObject) {
        System.out.println(testObject);
        if (testObject == null) {
            return null;
        }
        return testObject.toString();

    }

    @GetMapping("/raghab")
    public ResponseEntity<?> test(@RequestParam MultipartFile file) throws IOException {
        String s = instructorService.saveVideo(file);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
