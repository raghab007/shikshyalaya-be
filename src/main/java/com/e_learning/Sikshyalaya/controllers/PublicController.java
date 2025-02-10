package com.e_learning.Sikshyalaya.controllers;

import com.e_learning.Sikshyalaya.dtos.RequestUser;
import com.e_learning.Sikshyalaya.dtos.UserResponseDto;
import com.e_learning.Sikshyalaya.entities.TestObject;
import com.e_learning.Sikshyalaya.entities.User;
import com.e_learning.Sikshyalaya.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
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
    public ResponseEntity<?> login(@RequestBody RequestUser user){
       String response =  userService.verify(user);
       if (response==null)
       {
           return new ResponseEntity<>(null,HttpStatus.NOT_FOUND);
       }
       return  new ResponseEntity<>(response, HttpStatus.OK);

    }

    @GetMapping("/user")
    public UserResponseDto getUser(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();String username = auth.getName();
        System.out.println(username);
        System.out.println("Hello -------");
        Optional< User> optionalUser = userService.getByUserName(username);
        User user = optionalUser.get();
        UserResponseDto userResponseDto = new UserResponseDto();
        userResponseDto.setUserName(user.getUserName());
        userResponseDto.setEmail(user.getEmail());
        userResponseDto.setFirstName(user.getFirstName());
        userResponseDto.setLastName(user.getLastName());
        userResponseDto.setContactNumber(user.getContactNumber());
        return userResponseDto;
    }

    @GetMapping("/testAPI")
    public String testAPI(@ModelAttribute TestObject testObject ){
        System.out.println(testObject);
        if (testObject==null){
            return  null;
        }
        return testObject.toString();

    }

    @GetMapping("/raghab")
    public void test(@PathVariable  MultipartFile multipartFile) throws IOException {
        System.out.println(multipartFile);
        File file = new File("src/main/resources/static/images").getAbsoluteFile();
        if (!file.exists()){
            file.mkdirs();
        }
        File uploadFile = new File(file,multipartFile.getOriginalFilename());
        multipartFile.transferTo(uploadFile);
        System.out.println(file.getPath());
    }
}
