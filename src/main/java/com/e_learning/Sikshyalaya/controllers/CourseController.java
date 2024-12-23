package com.e_learning.Sikshyalaya.controllers;

import com.e_learning.Sikshyalaya.dtos.CourseResponseDto;
import com.e_learning.Sikshyalaya.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class CourseController {
    @Autowired
    CourseService courseService;


    @GetMapping("/courses")
    public List<CourseResponseDto> getAllCourses(){
        return courseService.findAll().stream().map(course->new CourseResponseDto(course.getCourseName(), course.getCourseDescription(),course.getCoursePrice()))
                .toList();
    }
}
