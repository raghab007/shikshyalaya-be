package com.e_learning.Sikshyalaya.controllers;

import com.e_learning.Sikshyalaya.entities.Course;
import com.e_learning.Sikshyalaya.entities.Section;
import com.e_learning.Sikshyalaya.entities.User;
import com.e_learning.Sikshyalaya.service.CourseService;
import com.e_learning.Sikshyalaya.service.UserService;
import com.e_learning.Sikshyalaya.utils.StorageUtil;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.util.Optional;

@RestController
@RequestMapping("/instructor")
public class InstructorController {
    private final UserService userService;
    private final CourseService courseService;
    private  final String UPLOAD_DIR = "src/main/resources/static/images";
    private  final StorageUtil storageUtil;
    public InstructorController(CourseService courseService, UserService userService,StorageUtil storageUtil) {
        this.courseService = courseService;
        this.userService = userService;
        this.storageUtil = storageUtil;
    }

    @PostMapping("/course")
    public  void addCourse(
            @RequestParam("courseName") String courseName,
            @RequestParam("courseDescription") String courseDescription,
            @RequestParam("coursePrice") Integer coursePrice ,
            @RequestParam("coursePrice") Integer courseDuration,
            @RequestParam("courseImage") MultipartFile courseImage){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (courseImage.isEmpty()){
            return;
        }
        storageUtil.saveImage(courseImage,UPLOAD_DIR);
       Course course = new Course();
       course.setCourseName(courseName);
       course.setCourseDescription(courseDescription);
       course.setCoursePrice(coursePrice);
       course.setCourseDuration(courseDuration);
       course.setImageUrl(courseImage.getOriginalFilename());

        String username = auth.getName();
        Optional<User> user1 = userService.getByUserName(username);

        if (user1.isEmpty()) {
            throw new UsernameNotFoundException("Invalid username or password");
        }
        User user = user1.get();
        if (user.getRole().equals("INSTRUCTOR")) {
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
