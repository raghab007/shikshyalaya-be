package com.e_learning.Sikshyalaya.controllers;

import com.e_learning.Sikshyalaya.dtos.CourseDetailResponseDto;
import com.e_learning.Sikshyalaya.dtos.ViewCourseDto;
import com.e_learning.Sikshyalaya.entities.Course;
import com.e_learning.Sikshyalaya.entities.CourseCategory;
import com.e_learning.Sikshyalaya.entities.Section;
import com.e_learning.Sikshyalaya.repositories.CategoryRepository;
import com.e_learning.Sikshyalaya.service.CourseService;
import com.e_learning.Sikshyalaya.service.SectionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class CourseController {

    private final CourseService courseService;

    private final SectionService sectionService;

    private final CategoryRepository categoryRepository;

    public CourseController(CourseService courseService, SectionService sectionService, CategoryRepository categoryRepository) {
        this.courseService = courseService;
        this.sectionService = sectionService;
        this.categoryRepository = categoryRepository;
    }

    @GetMapping("/courses")
    public ResponseEntity<Map<String, Object>> getAllCourses(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "6") int limit) {

        System.out.println("Name:");
        System.out.println(SecurityContextHolder.getContext().getAuthentication().getName());
        // Calculate offset based on page and limit
        int offset = (page - 1) * limit;

        // Fetch paginated courses from the service
        List<Course> paginatedCourses = courseService.findPaginatedCourses(offset, limit);

        // Update image URLs for the paginated courses
        for (Course course : paginatedCourses) {
            String imageString = "/images/course/" + course.getImageUrl();
            course.setImageUrl(imageString);
            course.setInstructor(course.getInstructor());
        }
        List<ViewCourseDto> responseCourses = paginatedCourses.stream().map(course -> new ViewCourseDto(course)).toList();

        // Get the total number of courses for pagination
        int totalCourses = courseService.getTotalCourses();
        int totalPages = (int) Math.ceil((double) totalCourses / limit);

        // Prepare the response
        Map<String, Object> response = new HashMap<>();
        response.put("courses", responseCourses);
        response.put("totalPages", totalPages);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/course/{courseID}")
    public ResponseEntity<?> getCourseById(@PathVariable int courseID) {
        Course course = courseService.findById(courseID);
        List<Section> sections = sectionService.findSectionsByCourse(course.getCourseID());
        course.setSections(sections);
        CourseDetailResponseDto responseDto = new CourseDetailResponseDto(course);
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    @GetMapping("/course/course_category")
    public List<CourseCategory> getAllCourseCategories() {
        List<CourseCategory> all = categoryRepository.findAll();
        return all;
    }
}
