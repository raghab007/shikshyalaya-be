package com.e_learning.Sikshyalaya.controllers;
import com.e_learning.Sikshyalaya.dtos.RequestCourseDto;
import com.e_learning.Sikshyalaya.entities.*;
import com.e_learning.Sikshyalaya.repositories.CategoryRepository;
import com.e_learning.Sikshyalaya.service.CourseService;
import com.e_learning.Sikshyalaya.service.SectionService;
import com.e_learning.Sikshyalaya.service.UserService;
import com.e_learning.Sikshyalaya.utils.StorageUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/instructor")
@Slf4j
public class InstructorController {
    private final UserService userService;
    private final CourseService courseService;
    private final SectionService sectionService;
    @Autowired
    private CategoryRepository categoryRepository;


    public InstructorController(CourseService courseService, UserService userService, StorageUtil storageUtil, SectionService sectionService) {
        this.courseService = courseService;
        this.userService = userService;
        this.sectionService = sectionService;
    }

    @PostMapping("/course")
    public ResponseEntity<?> addCourse(
           @ModelAttribute RequestCourseDto courseReq
    ) throws IOException {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (courseReq.getCourseImage().isEmpty()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Course course = new Course(courseReq);
       String username = auth.getName();
       Optional<User> user1 = userService.getByUserName(username);
        if (user1.isEmpty()) {
            throw new UsernameNotFoundException("Invalid username or password");
        }
        User user = user1.get();
        if (user.getRole().equals("INSTRUCTOR"))
        {
            course.setInstructor(user);
            courseService.saveCourse(course, courseReq.getCourseImage());
            return new ResponseEntity<>(HttpStatus.CREATED);
        }else
        {
            throw new UsernameNotFoundException("Invalid username or password");
        }

    }


    @PostMapping("/course/{courseId}/section")
    public void addSection(@RequestBody Section section, @PathVariable Integer courseId) throws IOException {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Course  course = courseService.findById(courseId);
        section.setCourse(course);
        sectionService.add(section);
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
            lecture.setSection(section);

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

    @PostMapping("/course/category")
    public  ResponseEntity<?> addCategory (@RequestBody CourseCategory category){
        category.setCourseCategoryId(1);
        categoryRepository.save(category);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/student/enrolled/{courseId}")
    public ResponseEntity<?> getAllEnrolledStudents(@PathVariable Integer courseId){
        Course course = courseService.findById(courseId);
        if (course==null){
            throw  new RuntimeException("Course not found");
        }
        List<Enrollment> enrollments  = course.getEnrollments();
        List<User> enrolledUsers = new ArrayList<>();
        for (Enrollment enrollment : enrollments) {
            enrolledUsers.add(enrollment.getUser());
        }
        return  new ResponseEntity<>(enrolledUsers,HttpStatus.OK);
    }
}
