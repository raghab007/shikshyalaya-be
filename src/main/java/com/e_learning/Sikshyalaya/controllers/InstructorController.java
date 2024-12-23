package com.e_learning.Sikshyalaya.controllers;
import com.e_learning.Sikshyalaya.entities.Course;
import com.e_learning.Sikshyalaya.entities.Section;
import com.e_learning.Sikshyalaya.entities.User;
import com.e_learning.Sikshyalaya.service.CourseService;
import com.e_learning.Sikshyalaya.service.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/instructor")
public class InstructorController {

    private final UserService userService;
    private final CourseService courseService;
    public InstructorController(CourseService courseService, UserService userService) {
        this.courseService = courseService;
        this.userService = userService;
    }

    @PostMapping("/course")
    public  void addCourse(@RequestBody Course course){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        Optional<User> user1 = userService.getByUserName(username);
        User user = user1.get();
        if (user !=null && user.getRole().equals("INSTRUCTOR")) {
            course.setInstructor(user);
            courseService.saveCourse(course);
        }else {
            throw new UsernameNotFoundException("Invalid username or password");
        }
    }

    @PostMapping("/course/addsection/{courseId}")
    public void addSection(@RequestBody Section section, @PathVariable Integer courseId){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        Course  course = courseService.findById(courseId);
        course.getSections().add(section);
        courseService.saveCourse(course);
    }

    @DeleteMapping("/course")
    public void deleteCourse(Integer courseId){
        courseService.deleteById(courseId);
    }

    @PutMapping("/course/updatedetails")
    public void updateCourseDetails(Course course){
        Course oldCourse = courseService.findById(course.getCourseID());
        if (oldCourse != null) {
            oldCourse.setCourseName(course.getCourseName());
            oldCourse.setCourseDescription(course.getCourseDescription());
        }

    }

    @PutMapping("/course/update")
    public  void updateCourse(Integer courseId,Section section){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        Optional<User> user1 = userService.getByUserName(username);
        User user = user1.get();
        if (user != null) {

        }else {
            throw new UsernameNotFoundException("Username not found");
        }
        Course oldCourse = courseService.findById(courseId);
        if (oldCourse != null) {
            oldCourse.getSections().add(section);
        }else{
            throw new IllegalArgumentException("Course not found");
        }
        courseService.saveCourse(oldCourse);
    }
}
