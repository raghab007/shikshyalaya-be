package com.e_learning.Sikshyalaya.controllers;

import com.e_learning.Sikshyalaya.dtos.Transaction;
import com.e_learning.Sikshyalaya.entities.Course;
import com.e_learning.Sikshyalaya.entities.Enrollment;
import com.e_learning.Sikshyalaya.entities.User;
import com.e_learning.Sikshyalaya.repositories.EnrollmentRepository;
import com.e_learning.Sikshyalaya.service.CourseService;
import com.e_learning.Sikshyalaya.service.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;


@RestController
@RequestMapping
public class UserController {


    private final CourseService courseService;


    private final   UserService userService;


    private final EnrollmentRepository enrollmentRepository;

    public UserController (CourseService courseService, UserService userService, EnrollmentRepository enrollmentRepository){
    this.userService = userService;
    this.enrollmentRepository = enrollmentRepository;
    this.courseService = courseService;
    }

    @PostMapping("/enrollment/{courseId}")
    public String enrollCourse(@PathVariable Integer courseId){
        SecurityContext context = SecurityContextHolder.getContext();
        Authentication authentication = context.getAuthentication();
        String userName = authentication.getName();
        Optional<User> byUserName = userService.getByUserName(userName);
        User user = byUserName.get();

        Course course = courseService.findById(courseId);
        Enrollment enrollment = new Enrollment();
        enrollment.setUser(user);
        enrollment.setCourse(course);
        enrollmentRepository.save(enrollment);
        return "OK";
    }
}
