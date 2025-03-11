package com.e_learning.Sikshyalaya.controllers;
import com.e_learning.Sikshyalaya.dtos.LectureRequestDto;
import com.e_learning.Sikshyalaya.dtos.RequestCourseDto;
import com.e_learning.Sikshyalaya.dtos.SectionRequestDto;
import com.e_learning.Sikshyalaya.dtos.UserResponseDto;
import com.e_learning.Sikshyalaya.entities.*;
import com.e_learning.Sikshyalaya.repositories.CategoryRepository;
import com.e_learning.Sikshyalaya.repositories.LectureRepository;
import com.e_learning.Sikshyalaya.service.CourseService;
import com.e_learning.Sikshyalaya.service.InstructorService;
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
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/instructor")
@Slf4j
public class InstructorController {
    private final UserService userService;
    private final CourseService courseService;
    private final SectionService sectionService;
    private final CategoryRepository categoryRepository;
   private final InstructorService instructorService;
   private  final LectureRepository lectureRepository;


    public InstructorController(LectureRepository lectureRepository,CategoryRepository categoryRepository,CourseService courseService, UserService userService, StorageUtil storageUtil, SectionService sectionService, InstructorService instructorService) {
        this.courseService = courseService;
        this.userService = userService;
        this.sectionService = sectionService;
        this.categoryRepository = categoryRepository;
        this.instructorService =instructorService;
        this.lectureRepository = lectureRepository;
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
        CourseCategory courseCategory = new CourseCategory();
        courseCategory.setCourseCategoryId(courseReq.getCategoryId());
        course.setCategory(courseCategory);
       String username = auth.getName();
       Optional<User> user1 = userService.getByUserName(username);
        User user = user1.orElseThrow(() -> new RuntimeException("User not found"));

        if (user.getRole().equals("INSTRUCTOR"))
        {
            course.setInstructor(user);
            courseService.saveCourse(course, courseReq.getCourseImage());
            return new ResponseEntity<>(HttpStatus.CREATED);
        }else
        {
            throw new UsernameNotFoundException("Invalid Role");
        }

    }
    @GetMapping("/course/{courseId}/sections")
    public List<Section> getAllSectionsByCourse(@PathVariable Integer courseId){
       String name = SecurityContextHolder.getContext().getAuthentication().getName();
        Optional<User> optionalUser = userService.getByUserName(name);
       User user = optionalUser.orElseThrow(()->new RuntimeException("User not found"));
       List<Course> courses = user.getCourses();
        Optional<Course> first = courses.stream().filter(course -> course.getCourseID() == courseId).findFirst();
        Course course = first.orElseThrow(() -> new RuntimeException("Course not found"));
        return course.getSections();
    }

    @PostMapping("/course/{courseId}/section")
    public void addSection(@RequestBody SectionRequestDto sectionRequestDto, @PathVariable Integer courseId) throws IOException {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Course  course = courseService.findById(courseId);
        Section section = new Section();
        section.setName(sectionRequestDto.getName());
        section.setDescription(sectionRequestDto.getDescription());
        section.setCourse(course);
        sectionService.add(section);
    }

    @PostMapping("/course/section/{sectionId}/lecture")
    public ResponseEntity<?> addLecture(@ModelAttribute LectureRequestDto lectureRequestDto, @PathVariable Integer sectionId) throws IOException {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Optional<User> optionalUser = userService.getByUserName(auth.getName());
        if (optionalUser.isEmpty()) {
            throw new UsernameNotFoundException("Invalid username or password");
        }
        String videoFileName = instructorService.saveVideo(lectureRequestDto.getVideo());
        String imageFileName = instructorService.saveThumbnail(lectureRequestDto.getImage());
        Section section = sectionService.findById(sectionId).orElseThrow(() -> new RuntimeException("Section not found"));
        Lecture lecture = new Lecture();
        lecture.setSection(section);
        lecture.setTitle(lectureRequestDto.getTitle());
        lecture.setDescription(lectureRequestDto.getDescription());
        lecture.setImageUrl(imageFileName);
        lecture.setVideoUrl(videoFileName);
        lecture.setUploadedDate(new Date());
        lecture.setSection(section);
        lectureRepository.save(lecture);
       return  new ResponseEntity<>(HttpStatus.CREATED);
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

    @GetMapping("/students/enrolled/{courseId}")
    public ResponseEntity<?> getAllEnrolledStudents(@PathVariable Integer courseId){
        Course course = courseService.findById(courseId);
        if (course==null){
            throw  new RuntimeException("Course not found");
        }
        List<Enrollment> enrollments  = course.getEnrollments();
        List<UserResponseDto> enrolledUsers = new ArrayList<>();
        for (Enrollment enrollment : enrollments) {
            UserResponseDto userResponseDto = new UserResponseDto(enrollment.getUser());
            enrolledUsers.add(userResponseDto);
        }
        return  new ResponseEntity<>(enrolledUsers,HttpStatus.OK);
    }
}
