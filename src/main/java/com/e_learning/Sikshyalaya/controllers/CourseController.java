package com.e_learning.Sikshyalaya.controllers;

import com.e_learning.Sikshyalaya.entities.Course;
import com.e_learning.Sikshyalaya.entities.Section;
import com.e_learning.Sikshyalaya.service.CourseService;
import com.e_learning.Sikshyalaya.service.SectionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class CourseController {

   private final CourseService courseService;

   private  final SectionService sectionService;

    public  CourseController(CourseService courseService, SectionService sectionService){
        this.courseService = courseService;
        this.sectionService = sectionService;
    }
    @GetMapping("/courses")
    public List<Course> getAllCourses(){
        List<Course> all = courseService.findAll();
        for (Course course:all){
            System.out.println(course.getImageUrl());
            String imageString = "/images/course/"+course.getImageUrl();
                course.setImageUrl(imageString);
            }
        return all;
    }
    @GetMapping("/course/{courseID}")
    public ResponseEntity<?> getCourseById(@PathVariable int courseID){
        Course course = courseService.findById(courseID);
        List<Section> sections = sectionService.findSectionsByCourse(course.getCourseID());
        course.setSections(sections);
        return new ResponseEntity<>(course, HttpStatus.OK);
    }
}
