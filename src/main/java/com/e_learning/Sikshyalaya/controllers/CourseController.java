package com.e_learning.Sikshyalaya.controllers;

import com.e_learning.Sikshyalaya.entities.Course;
import com.e_learning.Sikshyalaya.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class CourseController {
    @Autowired
    CourseService courseService;


    @GetMapping("/courses")
    public List<Course> getAllCourses(){
        return courseService.findAll();
    }
    @GetMapping("/course/{courseID}")
    public Course getCourseById(@PathVariable int courseID){
        return courseService.findById(courseID);
    }

}
