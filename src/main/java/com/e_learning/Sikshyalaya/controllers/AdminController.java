package com.e_learning.Sikshyalaya.controllers;

import com.e_learning.Sikshyalaya.entities.Course;
import com.e_learning.Sikshyalaya.entities.User;
import com.e_learning.Sikshyalaya.service.CourseService;
import com.e_learning.Sikshyalaya.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    CourseService courseService;

    UserService userService;

    public List<Course> getAllCourses() {
      return  courseService.findAll();
    }

    public List<User> getAllUsers() {
      return   userService.findAll();
    }


    public void deleteUserByUserName(String userName) {
        userService.deleteUserByUserName(userName);
    }

}
