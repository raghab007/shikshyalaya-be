package com.e_learning.Sikshyalaya.controllers;

import com.e_learning.Sikshyalaya.dtos.*;
import com.e_learning.Sikshyalaya.entities.*;
import com.e_learning.Sikshyalaya.repositories.CategoryRepository;
import com.e_learning.Sikshyalaya.repositories.LectureRepository;
import com.e_learning.Sikshyalaya.service.CourseService;
import com.e_learning.Sikshyalaya.service.InstructorService;
import com.e_learning.Sikshyalaya.service.SectionService;
import com.e_learning.Sikshyalaya.service.UserService;
import com.e_learning.Sikshyalaya.utils.StorageUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;


@RestController
@RequestMapping("/instructor")
@Slf4j
public class InstructorController {
    private final UserService userService;
    private final CourseService courseService;
    private final SectionService sectionService;
    private final CategoryRepository categoryRepository;
    private final InstructorService instructorService;
    private final LectureRepository lectureRepository;

    public InstructorController(LectureRepository lectureRepository, CategoryRepository categoryRepository, CourseService courseService, UserService userService, StorageUtil storageUtil, SectionService sectionService, InstructorService instructorService) {
        this.courseService = courseService;
        this.userService = userService;
        this.sectionService = sectionService;
        this.categoryRepository = categoryRepository;
        this.instructorService = instructorService;
        this.lectureRepository = lectureRepository;
    }

    @PostMapping("/course")
    public ResponseEntity<?> addCourse(@ModelAttribute RequestCourseDto courseReq) throws IOException {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (courseReq.getCourseImage().isEmpty()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        CourseCategory courseCategory = categoryRepository.findById(courseReq.getCategoryId()).orElseThrow(() -> new RuntimeException("Course cateogry not found"));
        Course course = new Course(courseReq);
        course.setCategory(courseCategory);
        course.setCourseDifficulty(courseReq.getCourseDifficulty());
        String username = auth.getName();
        Optional<User> user1 = userService.getByUserName(username);
        User user = user1.orElseThrow(() -> new RuntimeException("User not found"));
        if (user.getRole().equals("INSTRUCTOR")) {
            course.setInstructor(user);
            courseService.saveCourse(course, courseReq.getCourseImage());
            return new ResponseEntity<>(HttpStatus.CREATED);
        } else {
            throw new UsernameNotFoundException("Invalid Role");
        }

    }

    @GetMapping("/course/{courseId}/sections")
    public List<Section> getAllSectionsByCourse(@PathVariable Integer courseId) {
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        Optional<User> optionalUser = userService.getByUserName(name);
        User user = optionalUser.orElseThrow(() -> new RuntimeException("User not found"));
        List<Course> courses = user.getCourses();
        Optional<Course> first = courses.stream().filter(course -> course.getCourseID() == courseId).findFirst();
        Course course = first.orElseThrow(() -> new RuntimeException("Course not found"));
        return course.getSections();
    }

    @PostMapping("/course/{courseId}/section")
    public ResponseEntity<?> addSection(@RequestBody SectionRequestDto sectionRequestDto, @PathVariable Integer courseId) throws IOException {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Course course = courseService.findById(courseId);
        Section section = new Section();
        section.setName(sectionRequestDto.getName());
        section.setDescription(sectionRequestDto.getDescription());
        section.setCourse(course);
        sectionService.add(section);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/course/{courseId}")
    public ResponseEntity<?> deleteCourseById (@PathVariable Integer courseId){
        String userName = getUserName();
        User byUserName = userService.getByUserName(userName).orElseThrow(()-> new RuntimeException("User not found"));
        Course courseNotFound = byUserName.getCourses().stream().filter(course -> course.getCourseID().equals(courseId)).findFirst().orElseThrow(() -> new RuntimeException("Course not found"));
        courseService.deleteById(courseId);
        return  new ResponseEntity<>(HttpStatus.OK);

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
        return new ResponseEntity<>(HttpStatus.CREATED);
    }


    @GetMapping("/course/section/{sectionId}/lecture")
    public List<Lecture> getLecturesBySection(@PathVariable Integer sectionId) {
        Section byId = sectionService.findById(sectionId).orElseThrow(() -> new RuntimeException("Section not found"));
        List<Lecture> lectures = byId.getLectures();
        System.out.println(lectures);
        return lectures;
    }



    @PutMapping("/course/updatedetails")
    public void updateCourseDetails(Course course) {
        Course oldCourse = courseService.findById(course.getCourseID());
        if (oldCourse != null) {
            oldCourse.setCourseName(course.getCourseName());
            oldCourse.setCourseDescription(course.getCourseDescription());
        }
    }

    @PutMapping("/course/update")
    public void updateCourse(Integer courseId, Section section) {
        String username = getUserName();
        Optional<User> user1 = userService.getByUserName(username);
        User user = user1.get();
        Course oldCourse = courseService.findById(courseId);
        if (oldCourse != null) {
            oldCourse.getSections().add(section);
        } else {
            throw new IllegalArgumentException("Course not found");
        }
        courseService.saveCourse(oldCourse);
    }

    @PostMapping("/course/category")
    public ResponseEntity<?> addCategory(@RequestBody CourseCategory category) {
        category.setCourseCategoryId(1);
        categoryRepository.save(category);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/students/enrolled/{courseId}")
    public ResponseEntity<?> getAllEnrolledStudents(@PathVariable Integer courseId) {
        Course course = courseService.findById(courseId);
        if (course == null) {
            throw new RuntimeException("Course not found");
        }
        List<Enrollment> enrollments = course.getEnrollments();
        List<UserResponseDto> enrolledUsers = new ArrayList<>();
        for (Enrollment enrollment : enrollments) {
            UserResponseDto userResponseDto = new UserResponseDto(enrollment.getUser());
            enrolledUsers.add(userResponseDto);
        }
        return new ResponseEntity<>(enrolledUsers, HttpStatus.OK);
    }

    //    @GetMapping("/students/enrolled")
//    public  ResponseEntity<?> getEnrolledStudentsByInstructor(){
//    }
    @GetMapping("/course")
    public List<ViewCourseDto> getCoursesByInstructor() {
        String name = getUserName();
        List<ViewCourseDto> courseLists = courseService.getCourseByInstructor(name).stream().map(course -> new ViewCourseDto(course)).toList();
        return courseLists;
    }

    @PatchMapping("/course/{courseId}")
    public String changeCourseImage(@RequestBody MultipartFile image, @PathVariable Integer courseId) throws IOException {
        return instructorService.updateCourseImage(image, courseId);
    }


    @GetMapping("/courses/stats")
    public ResponseEntity<Map<String, Object>> getCoursesDetails() {
        String userName = getUserName();
        Map<String, Object> response = new HashMap<>();
        long totalNumberOfCoursesByInstructor = courseService.getTotalNumberOfCoursesByInstructor(userName);
        long totalEnrolledStudentsByInstructor = courseService.getTotalEnrolledStudentsByInstructor(userName);
        int totalRevenue = instructorService.totalRevenue(userName);
        response.put("TotalCourses", totalNumberOfCoursesByInstructor);
        response.put("TotalStudents", totalEnrolledStudentsByInstructor);
        response.put("TotalRevenue", totalRevenue);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    public String getUserName() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }


    @PutMapping("/course/{courseId}")
    public ResponseEntity<?> updateCourse(@PathVariable Integer courseId, @RequestBody CourseUpdateDto courseUpdateDto) {

        try {
            // Get authenticated instructor
            String username = getUserName();
            User instructor = userService.getByUserName(username).orElseThrow(() -> new UsernameNotFoundException("User not found"));

            // Verify the user is an instructor
            if (!instructor.getRole().equals("INSTRUCTOR")) {
                throw new UsernameNotFoundException("Invalid Role");
            }

            // Get the course to update
            Course course = courseService.findById(courseId);
            if (course == null) {
                return new ResponseEntity<>("Course not found", HttpStatus.NOT_FOUND);
            }

            // Verify the instructor owns this course
            if (!course.getInstructor().getUserName().equals(instructor.getUserName())) {
                return new ResponseEntity<>("You can only update your own courses", HttpStatus.FORBIDDEN);
            }

            // Update course fields if they are provided in the DTO
            if (courseUpdateDto.getCourseName() != null) {
                course.setCourseName(courseUpdateDto.getCourseName());
            }
            if (courseUpdateDto.getCourseDescription() != null) {
                course.setCourseDescription(courseUpdateDto.getCourseDescription());
            }
            if (courseUpdateDto.getCoursePrice() != null) {
                course.setCoursePrice(courseUpdateDto.getCoursePrice());
            }
            if (courseUpdateDto.getCourseDifficulty() != null) {
                course.setCourseDifficulty(courseUpdateDto.getCourseDifficulty());
            }
            if (courseUpdateDto.getCategoryId() != null) {
                CourseCategory category = categoryRepository.findById(courseUpdateDto.getCategoryId()).orElseThrow(() -> new RuntimeException("Category not found"));
                course.setCategory(category);
            }

            // Save the updated course
            courseService.saveCourse(course);

            return new ResponseEntity<>(course, HttpStatus.OK);

        } catch (Exception e) {
            log.error("Error updating course", e);
            return new ResponseEntity<>("Error updating course: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

//    @PatchMapping("/course/{courseId}/image")
//    public ResponseEntity<?> updateCourseImage(
//            @PathVariable Integer courseId,
//            @RequestParam("image") MultipartFile image) {
//
//        try {
//            // Get authenticated instructor
//            String username = getUserName();
//            User instructor = userService.getByUserName(username)
//                    .orElseThrow(() -> new UsernameNotFoundException("User not found"));
//
//            // Verify the user is an instructor
//            if (!instructor.getRole().equals("INSTRUCTOR")) {
//                throw new UsernameNotFoundException("Invalid Role");
//            }
//
//            // Get the course to update
//            Course course = courseService.findById(courseId);
//            if (course == null) {
//                return new ResponseEntity<>("Course not found", HttpStatus.NOT_FOUND);
//            }
//
//            // Verify the instructor owns this course
//            if (!course.getInstructor().getId().equals(instructor.getId())) {
//                return new ResponseEntity<>("You can only update your own courses", HttpStatus.FORBIDDEN);
//            }
//
//            // Update course image
//            if (image.isEmpty()) {
//                return new ResponseEntity<>("Image file is required", HttpStatus.BAD_REQUEST);
//            }
//
//            String imageUrl = courseService.saveCourseImage(course, image);
//
//            return new ResponseEntity<>(imageUrl, HttpStatus.OK);
//
//        } catch (IOException e) {
//            log.error("Error updating course image", e);
//            return new ResponseEntity<>("Error updating course image: " + e.getMessage(),
//                    HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }
//
//

}
