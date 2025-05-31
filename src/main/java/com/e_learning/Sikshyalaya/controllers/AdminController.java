package com.e_learning.Sikshyalaya.controllers;

import com.e_learning.Sikshyalaya.dtos.CourseResponseDto;
import com.e_learning.Sikshyalaya.dtos.RatingReviewResponse;
import com.e_learning.Sikshyalaya.dtos.UserResponseDto;
import com.e_learning.Sikshyalaya.dtos.ViewCourseDto;
import com.e_learning.Sikshyalaya.entities.Course;
import com.e_learning.Sikshyalaya.entities.User;
import com.e_learning.Sikshyalaya.service.CourseService;
import com.e_learning.Sikshyalaya.service.RatingReviewService;
import com.e_learning.Sikshyalaya.service.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/admin")
public class AdminController {

    private final CourseService courseService;
    private final UserService userService;
    private final RatingReviewService ratingReviewService;

    public AdminController(CourseService courseService, UserService userService,
            RatingReviewService ratingReviewService) {
        this.courseService = courseService;
        this.userService = userService;
        this.ratingReviewService = ratingReviewService;
    }

    @GetMapping("/courses")
    public ResponseEntity<Map<String, Object>> getAllCourses(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int limit,
            @RequestParam(required = false) String search) {

        Pageable pageable = PageRequest.of(page - 1, limit);
        Page<Course> coursePage;

        if (search != null && !search.trim().isEmpty()) {
            coursePage = courseService.findByCourseNameContaining(search, pageable);
        } else {
            coursePage = courseService.findAll(pageable);
        }

        List<ViewCourseDto> courses = coursePage.getContent().stream()
                .map(ViewCourseDto::new)
                .collect(Collectors.toList());

        Map<String, Object> response = new HashMap<>();
        response.put("courses", courses);
        response.put("currentPage", coursePage.getNumber() + 1);
        response.put("totalItems", coursePage.getTotalElements());
        response.put("totalPages", coursePage.getTotalPages());

        return ResponseEntity.ok(response);
    }

    @GetMapping("/users")
    public List<UserResponseDto> getAllUsers() {
        return userService.findAll().stream().filter(user -> user.getRole().equals("USER"))
                .map(UserResponseDto::new).toList();
    }

    @GetMapping("/instructors")
    public List<UserResponseDto> getAllInstructors() {
        return userService.findAll().stream().filter(user -> user.getRole().equals("INSTRUCTOR"))
                .map(UserResponseDto::new).toList();
    }

    @DeleteMapping("/users/{userName}")
    public void deleteUserByUserName(@PathVariable String userName) {
        userService.deleteUserByUserName(userName);
    }

    @PutMapping("/users/{userName}")
    public ResponseEntity<?> updateLoginStatus(@RequestBody BlockStatusRequest blockStatusRequest,
            @PathVariable String userName) {
        Optional<User> byUserName = userService.getByUserName(userName);
        User userNotFound = byUserName.orElseThrow(() -> new RuntimeException("User not found"));
        userNotFound.setBlocked(blockStatusRequest.isBlocked());
        userService.updateUser(userNotFound);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/courses/{courseId}")
    public ResponseEntity<?> deleteCourse(@PathVariable Long courseId) {
        try {
            courseService.deleteCourse(courseId);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to delete course: " + e.getMessage());
        }
    }

    @GetMapping("/courses/{courseId}/reviews")
    public ResponseEntity<Map<String, Object>> getCourseReviews(
            @PathVariable Long courseId,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int limit) {

        Pageable pageable = PageRequest.of(page - 1, limit);
        Page<RatingReviewResponse> reviewPage = ratingReviewService.getReviewsForCourseAdmin(courseId, pageable);

        Map<String, Object> response = new HashMap<>();
        response.put("reviews", reviewPage.getContent());
        response.put("currentPage", reviewPage.getNumber() + 1);
        response.put("totalItems", reviewPage.getTotalElements());
        response.put("totalPages", reviewPage.getTotalPages());

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/courses/{courseId}/reviews/{reviewId}")
    public ResponseEntity<?> deleteReview(
            @PathVariable Long courseId,
            @PathVariable Long reviewId) {
        try {
            ratingReviewService.deleteReview(reviewId);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to delete review: " + e.getMessage());
        }
    }

    public static class BlockStatusRequest {
        private boolean blocked;

        public boolean isBlocked() {
            return blocked;
        }

        public void setBlocked(boolean blocked) {
            this.blocked = blocked;
        }
    }

    public static class InstructorResponseDto {
        private String id;
        private String name;
        private String email;
        private int courses;
        private double rating;

        public InstructorResponseDto(String id, String name, String email, int courses, double rating) {
            this.id = id;
            this.name = name;
            this.email = email;
            this.courses = courses;
            this.rating = rating;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public int getCourses() {
            return courses;
        }

        public void setCourses(int courses) {
            this.courses = courses;
        }

        public double getRating() {
            return rating;
        }

        public void setRating(double rating) {
            this.rating = rating;
        }
    }

}
