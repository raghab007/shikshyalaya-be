package com.e_learning.Sikshyalaya.controllers;
import com.e_learning.Sikshyalaya.dtos.LoginResponse;
import com.e_learning.Sikshyalaya.dtos.RequestUser;
import com.e_learning.Sikshyalaya.dtos.RequestUserDto;
import com.e_learning.Sikshyalaya.dtos.UserResponseDto;
import com.e_learning.Sikshyalaya.entities.ResponseHaha;
import com.e_learning.Sikshyalaya.entities.TestObject;
import com.e_learning.Sikshyalaya.entities.User;
import com.e_learning.Sikshyalaya.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Optional;
@RestController
@RequestMapping()
public class PublicController {
    @Autowired
   private UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@RequestBody RequestUserDto userRequestDto) {
       Optional<User> _oldUser_ = userService.getByUserName(userRequestDto.getUserName());
        User user = new User(userRequestDto);
       if (_oldUser_.isEmpty()){
           userService.saveUser(user);
           return new ResponseEntity<String>("true",HttpStatus.CREATED);
       }else {
           return new ResponseEntity<String>("false",HttpStatus.OK);
       }
    }
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody RequestUser user){
       LoginResponse response =  userService.verify(user);
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
        UserResponseDto userResponseDto = new UserResponseDto(user);
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
    public ResponseEntity<?> test(@PathVariable  MultipartFile multipartFile) throws IOException {
        File file = new File("src/main/resources/static/images/course/image.png").getAbsoluteFile();
        try (FileInputStream fis = new FileInputStream(file)) {
            byte[] imageBytes = fis.readAllBytes(); // Reads all bytes from the image file
            ResponseHaha responseHaha = new ResponseHaha();
            responseHaha.bytes  =imageBytes;
            responseHaha.name = "Raghab";
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.MULTIPART_MIXED); // Change based on image type (JPEG, PNG, etc.)
            // Create MultiValueMap to hold both course info (JSON) and the image
            //MultiValueMap<String, Object> responseMap = new LinkedMultiValueMap<>();
           // responseMap.add("courseInfo", responseHaha); // Add course information as JSON
            //.add("courseImage", imageBytes); // Add image as a resource (will be streamed)
            return new ResponseEntity<>(responseHaha, HttpStatus.OK);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

}
