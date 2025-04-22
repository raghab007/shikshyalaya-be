package com.e_learning.Sikshyalaya.controllers;

import com.e_learning.Sikshyalaya.dtos.CourseResponseDto;
import com.e_learning.Sikshyalaya.dtos.UserResponseDto;
import com.e_learning.Sikshyalaya.entities.Course;
import com.e_learning.Sikshyalaya.entities.User;
import com.e_learning.Sikshyalaya.service.CourseService;
import com.e_learning.Sikshyalaya.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/admin")
public class AdminController {

    private final CourseService courseService;

    private final UserService userService;

    public AdminController(CourseService courseService, UserService userService) {
        this.courseService = courseService;
        this.userService = userService;
    }

    @GetMapping("/courses")
    public List<CourseResponseDto> getAllCourses() {
        return courseService.findAll().stream().map(course -> new CourseResponseDto(course)).toList();
    }


    @GetMapping("/users")
    public List<UserResponseDto> getAllUsers() {
        return userService.findAll().stream().filter(user -> user.getRole().equals("USER")).map(user -> new UserResponseDto(user)).toList();
    }

    @DeleteMapping("/users/{userName}")
    public void deleteUserByUserName(@PathVariable String userName) {
        userService.deleteUserByUserName(userName);
    }

    @PutMapping("/users/{userName}")
    public ResponseEntity<?> updateLoginStatus(@RequestBody  BlockStatusRequest  blockStatusRequest , @PathVariable String userName  ){
        Optional<User> byUserName = userService.getByUserName(userName);
        User userNotFound = byUserName.orElseThrow(() -> new RuntimeException("User not found"));
        userNotFound.setBlocked(blockStatusRequest.isBlocked());
        userService.saveUser(userNotFound);
        return  new ResponseEntity<>(HttpStatus.OK);
    }




    // Define a DTO to receive the request body
    public static class BlockStatusRequest {
        private boolean blocked;

        public boolean isBlocked() {
            return blocked;
        }

        public void setBlocked(boolean blocked) {
            this.blocked = blocked;
        }
    }


}
