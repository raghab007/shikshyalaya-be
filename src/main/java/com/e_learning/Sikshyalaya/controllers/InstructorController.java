package com.e_learning.Sikshyalaya.controllers;

import com.e_learning.Sikshyalaya.entities.Course;
import com.e_learning.Sikshyalaya.entities.Lecture;
import com.e_learning.Sikshyalaya.entities.Section;
import com.e_learning.Sikshyalaya.entities.User;
import com.e_learning.Sikshyalaya.service.CourseService;
import com.e_learning.Sikshyalaya.service.SectionService;
import com.e_learning.Sikshyalaya.service.UserService;
import com.e_learning.Sikshyalaya.utils.Constants;
import com.e_learning.Sikshyalaya.utils.StorageUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.File;
import java.io.IOException;
import java.util.Optional;


@RestController
@RequestMapping("/instructor")
@Slf4j
public class InstructorController {
    private final UserService userService;
    private final CourseService courseService;
   // private  final String UPLOAD_DIR = "src/main/resources/static/images/course";
    private  final StorageUtil storageUtil;
    private final SectionService sectionService;

    public InstructorController(CourseService courseService, UserService userService, StorageUtil storageUtil, SectionService sectionService) {
        this.courseService = courseService;
        this.userService = userService;
        this.storageUtil = storageUtil;
        this.sectionService = sectionService;
    }

    @PostMapping("/course")
    public ResponseEntity<?> addCourse(
            @RequestParam("courseName") String courseName,
            @RequestParam("courseDescription") String courseDescription,
            @RequestParam("coursePrice") Integer coursePrice ,
            @RequestParam("coursePrice") Integer courseDuration,
            @RequestParam("courseImage") MultipartFile courseImage) throws IOException {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (courseImage.isEmpty()){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        File file = new File(Constants.CourseImagePath);
        String path = file.getAbsolutePath();
        File director = new File(path);
        if (!director.exists()){
            director.mkdir();
        }
       File uploadedFile = new File(path,courseImage.getOriginalFilename());
        courseImage.transferTo(uploadedFile);
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
            return new ResponseEntity<>(HttpStatus.CREATED);
        }else {
            throw new UsernameNotFoundException("Invalid username or password");
        }
    }

    @PostMapping("/course/{courseId}/section")
    public void addSection(@RequestBody Section section, @PathVariable Integer courseId) throws IOException {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Course  course = courseService.findById(courseId);
        course.getSections().add(section);
        section.setCourse(course);
        courseService.saveCourse(course);
    }


    @PostMapping("/course/section/{sectionId}/lecture")
    public boolean addLecture(@RequestBody Lecture lecture, @PathVariable Integer sectionId){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Optional<User> optionalUser = userService.getByUserName(auth.getName());
        if (optionalUser.isEmpty()) {
            throw new UsernameNotFoundException("Invalid username or password");
        }
        Section section = sectionService.findById(sectionId);
        if (section!=null) {
            section.getLectures().add(lecture);
            return true;
        }
        return false;
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
        Course oldCourse = courseService.findById(courseId);
        if (oldCourse != null) {
            oldCourse.getSections().add(section);
        }else{
            throw new IllegalArgumentException("Course not found");
        }
        courseService.saveCourse(oldCourse);
    }
}
