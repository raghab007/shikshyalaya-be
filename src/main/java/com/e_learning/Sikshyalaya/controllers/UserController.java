package com.e_learning.Sikshyalaya.controllers;

import com.e_learning.Sikshyalaya.dtos.CourseResponseDto;
import com.e_learning.Sikshyalaya.dtos.RequestMessage;
import com.e_learning.Sikshyalaya.entities.Course;
import com.e_learning.Sikshyalaya.entities.Enrollment;
import com.e_learning.Sikshyalaya.entities.Message;
import com.e_learning.Sikshyalaya.entities.User;
import com.e_learning.Sikshyalaya.repositories.EnrollmentRepository;
import com.e_learning.Sikshyalaya.repositories.MessageRepository;
import com.e_learning.Sikshyalaya.service.CourseService;
import com.e_learning.Sikshyalaya.service.EnrollmentService;
import com.e_learning.Sikshyalaya.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.*;


@RestController
public class UserController {

    private final CourseService courseService;


    private final   UserService userService;

    private final EnrollmentRepository enrollmentRepository;

    private  final EnrollmentService enrollmentService;


    @Autowired
    private MessageRepository messageRepository;

    public UserController (CourseService courseService, UserService userService, EnrollmentRepository enrollmentRepository, EnrollmentService enrollmentService){
    this.userService = userService;
    this.enrollmentRepository = enrollmentRepository;
    this.courseService = courseService;
    this.enrollmentService = enrollmentService;
    }
    @PostMapping("/enrollment/{courseId}")
    public ResponseEntity<?> enrollCourse(@PathVariable Integer courseId){
        SecurityContext context = SecurityContextHolder.getContext();
        Authentication authentication = context.getAuthentication();
        String userName = authentication.getName();
        Optional<User> byUserName = userService.getByUserName(userName);
        User user = byUserName.orElseThrow(()-> new RuntimeException("User not found"));
        List<Enrollment> enrollments = enrollmentRepository.findAll();
        Enrollment enrollment1 = enrollments.stream().filter(enrollment ->
                Objects.equals(enrollment.getCourse().getCourseID(), courseId) && enrollment.getUser().getUserName().equals(userName)).findFirst().orElse(null);
        if (enrollment1!=(null)){
            return new ResponseEntity<>("Course already enrolled", HttpStatus.BAD_REQUEST);
        }
        Course course = courseService.findById(courseId);
        Enrollment enrollment = new Enrollment();
        enrollment.setUser(user);
        enrollment.setCourse(course);
        enrollmentRepository.save(enrollment);
        return  new ResponseEntity<>("Course Enrolled", HttpStatus.OK);
    }

    @GetMapping("/enrollment")
    public List<CourseResponseDto> getEnrolledCourseByUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        Optional<User> byUserName = userService.getByUserName(userName);
        User user = byUserName.orElseThrow(()->  new RuntimeException("User not found"));
        List<Enrollment> enrollments = user.getEnrollments();
        for (Enrollment enrollment : enrollments) {
            System.out.println("Username:"+enrollment.getUser().getUserName()+ "Course id:"+ enrollment.getCourse().getCourseID());
        }
        List<CourseResponseDto> courseResponse = new ArrayList<>();
        for (Enrollment enrollment:enrollments){
            courseResponse.add(new CourseResponseDto(enrollment.getCourse()));
        }
        return  courseResponse;
    }

    @PreAuthorize("@enrollmentService.isUserEnrolled(#courseId,authentication.name)")
    @GetMapping("/enrollment/course/{courseId}")
    public Course getCourseById(@PathVariable Integer courseId){
        return courseService.findById(courseId);
  }

  @GetMapping("/enrollment/courses")
  public List<Course> getEnrolledCourses(){
      String userName = SecurityContextHolder.getContext().getAuthentication().getName();
      return enrollmentRepository.findAll().
              stream().
              filter(enrollment -> enrollment.getUser().getUserName().equals(userName))
              .map(Enrollment::getCourse)
              .toList();
  }


  @PostMapping("/message/course/{courseId}")
  public String saveMessage(@PathVariable Integer courseId, @RequestBody RequestMessage requestMessage){
      String name = SecurityContextHolder.getContext().getAuthentication().getName();
      Course course  = courseService.findById(courseId);
      User user = userService.getByUserName(name).orElseThrow(()-> new RuntimeException("User not found"));
      Message message = new Message();
      message.setMessage(requestMessage.getMessage());
      message.setUser(user);
      message.setDate(new Date());
      message.setCourse(course);
      messageRepository.save(message);
      return "Success";
    }

}
