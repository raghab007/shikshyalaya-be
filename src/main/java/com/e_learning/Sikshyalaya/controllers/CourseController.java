package com.e_learning.Sikshyalaya.controllers;

import com.e_learning.Sikshyalaya.entities.Course;
import com.e_learning.Sikshyalaya.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class CourseController {

   private final CourseService courseService;

    public  CourseController(CourseService courseService){
        this.courseService = courseService;
    }
    @GetMapping("/courses")
    public List<Course> getAllCourses(){
        return courseService.findAll();
    }
    @GetMapping("/course/{courseID}")
    public ResponseEntity<?> getCourseById(@PathVariable int courseID){
        Course course = courseService.findById(courseID);
        return new ResponseEntity<>(course, HttpStatus.OK);
    }
}
