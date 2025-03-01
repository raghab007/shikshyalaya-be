package com.e_learning.Sikshyalaya.controllers;

import com.e_learning.Sikshyalaya.entities.Course;
import com.e_learning.Sikshyalaya.entities.User;
import com.e_learning.Sikshyalaya.service.CourseService;
import com.e_learning.Sikshyalaya.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {

    private final CourseService courseService;

    private final UserService userService;

   public AdminController (CourseService courseService, UserService userService){
       this.courseService = courseService;
       this.userService = userService;
   }


    @GetMapping("/courses")
    public List<Course> getAllCourses() {
      return  courseService.findAll();
    }


    @GetMapping("/users")
    public List<User> getAllUsers() {
      return   userService.findAll();
    }

    @DeleteMapping("/users/{userName}")
    public void deleteUserByUserName(@PathVariable String userName
    ) {
        userService.deleteUserByUserName(userName);
    }

}
