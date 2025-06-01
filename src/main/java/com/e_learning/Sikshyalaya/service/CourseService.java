package com.e_learning.Sikshyalaya.service;

import com.e_learning.Sikshyalaya.dtos.CourseChartDataDto;
import com.e_learning.Sikshyalaya.dtos.ViewCourseDto;
import com.e_learning.Sikshyalaya.entities.Course;
import com.e_learning.Sikshyalaya.entities.Enrollment;
import com.e_learning.Sikshyalaya.entities.Section;
import com.e_learning.Sikshyalaya.entities.User;
import com.e_learning.Sikshyalaya.interfaces.ICourseService;
import com.e_learning.Sikshyalaya.repositories.*;
import com.e_learning.Sikshyalaya.utils.Constants;
import com.e_learning.Sikshyalaya.utils.StorageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class CourseService implements ICourseService {
    @Autowired
    private final CourseRepository courseRepository;

    @Autowired
    private final EnrollmentRepository enrollmentRepository;

    @Autowired
    private SectionRepository sectionRepository;

    private ResourceRepository resourceRepository;

    @Autowired
    private final UserRepository userRepository;

    private StorageUtil storageUtil;

    private UserService userService;

    public CourseService(CourseRepository courseRepository, EnrollmentRepository enrollmentRepository,
            UserRepository userRepository, StorageUtil storageUtil, UserService userService) {
        this.courseRepository = courseRepository;
        this.enrollmentRepository = enrollmentRepository;
        this.userRepository = userRepository;
        this.storageUtil = storageUtil;
        this.userService = userService;
    }

    public void saveCourse(Course course, MultipartFile imageFile) throws IOException {
        String userHome = System.getProperty("user.home") + File.separator + "Desktop";
        File directory = new File(userHome, "shikshyalaya/course/images");
        // Create directory if it doesn't exist
        if (!directory.exists()) {
            System.out.println(directory.mkdirs());
        }
        if (imageFile == null || imageFile.getOriginalFilename() == null || imageFile.getOriginalFilename().isBlank()) {
            throw new IllegalArgumentException("Invalid file or filename");
        }
        // Getting file name
        String originalFilename = storageUtil.getRandomImageUrl()
                + storageUtil.getFileExtenstion(imageFile.getOriginalFilename());
        File uploadFile = new File(directory, originalFilename);
        byte[] bytes = imageFile.getBytes();
        FileOutputStream fileOutputStream = new FileOutputStream(uploadFile);
        fileOutputStream.write(bytes);
        fileOutputStream.close();
        course.setImageUrl(originalFilename);
        courseRepository.save(course);

    }

    // Fetch paginated courses
    public List<Course> findPaginatedCourses(int offset, int limit) {
        return courseRepository.findPaginatedCourses(offset, limit);
    }

    // Get total number of courses
    public int getTotalCourses() {
        return courseRepository.countTotalCourses();
    }

    @Override
    public void saveCourse(Course course) {
        courseRepository.save(course);
    }

    public void deleteById(Integer id) {
        courseRepository.deleteById(id);
    }

    public void updateCourseSection(Section section) {
        return;
    }

    public Course findById(Integer id) {
        Optional<Course> course = courseRepository.findById(id);
        return course.orElse(null);
    }

    public List<Course> findAll() {

        return courseRepository.findAll();
    }

    public List<Course> getCourseByInstructor(String instructorName) {
        List<Course> all = courseRepository.findAll();
        return all.stream().filter(course -> course.getInstructor().getUserName().equals(instructorName))
                .toList();

    }

    public long getTotalNumberOfCoursesByInstructor(String instructorName) {
        List<Course> all = courseRepository.findAll();
        long count = all.stream().filter(course -> course.getInstructor().getUserName().equals(instructorName)).count();
        return count;
    }

    public long getTotalEnrolledStudentsByInstructor(String instructorName) {
        return enrollmentRepository.findAll().stream().map(Enrollment::getCourse)
                .filter(course -> course.getInstructor().getUserName().equals(instructorName)).count();
    }

    public HashMap<String, Integer> getNumberOfStudentsByCourse(String instructorName) {
        User user = userRepository.findByUserName(instructorName)
                .orElseThrow(() -> new RuntimeException("User not fount"));
        List<Course> courseList = user.getCourses();
        Map<String, Integer> map = new HashMap<>();
        // courseList.forEach(course -> );
        return null;
    }

    // New search methods
    public List<Course> searchCourses(String query, int offset, int limit) {
        return courseRepository.searchCoursesByNamePaginated(query, offset, limit);
    }

    public int countSearchResults(String query) {
        return courseRepository.countSearchResultsByName(query);
    }

    public List<CourseChartDataDto> getCourseChartData(String instructorUsername) {
        User instructor = userService.getByUserName(instructorUsername)
                .orElseThrow(() -> new RuntimeException("Instructor not found"));

        List<Course> courses = courseRepository.findByInstructor(instructor);

        return courses.stream()
                .map(course -> new CourseChartDataDto(
                        course.getCourseName(),
                        course.getEnrollments().size(),
                        course.getCoursePrice()))
                .collect(Collectors.toList());
    }

    public Integer getTotalRevenue(String instructorUsername) {
        User instructor = userService.getByUserName(instructorUsername)
                .orElseThrow(() -> new RuntimeException("Instructor not found"));

        return courseRepository.findByInstructor(instructor).stream()
                .mapToInt(course -> course.getCoursePrice() * course.getEnrollments().size())
                .sum();
    }

    public List<ViewCourseDto> getCoursesByCategory(int page, int limit, int categoryId) {
        int offset = (page - 1) * limit;
        List<Course> paginatedCoursesByCategory = courseRepository.findPaginatedCoursesByCategory(categoryId, offset,
                limit);
        List<ViewCourseDto> list = paginatedCoursesByCategory.stream().map(course -> new ViewCourseDto(course))
                .toList();
        return list;
    }

    public int getTotalNumberOfCoursesByCategory(int categoryId) {
        return courseRepository.countByCategoryId(categoryId);
    }

    public List<ViewCourseDto> getCoursesByPriceRange(Double min, Double max, int page, int limit) {
        int offset = (page - 1) * limit;
        List<Course> courses;

        if (min != null && max != null) {
            courses = courseRepository.findByCoursePriceBetween(min, max, offset, limit);
        } else if (min != null) {
            courses = courseRepository.findByCoursePriceGreaterThanEqual(min, offset, limit);
        } else if (max != null) {
            courses = courseRepository.findByCoursePriceLessThanEqual(max, offset, limit);
        } else {
            courses = courseRepository.findPaginatedCourses(offset, limit);
        }

        return courses.stream()
                .map(ViewCourseDto::new)
                .collect(Collectors.toList());
    }

    public int getTotalNumberOfCoursesByPriceRange(Double min, Double max) {
        if (min != null && max != null) {
            return courseRepository.countByCoursePriceBetween(min, max);
        } else if (min != null) {
            return courseRepository.countByCoursePriceGreaterThanEqual(min);
        } else if (max != null) {
            return courseRepository.countByCoursePriceLessThanEqual(max);
        } else {
            return courseRepository.countTotalCourses();
        }
    }

    public List<ViewCourseDto> getCoursesByDifficulty(String difficulty, int page, int limit) {
        int offset = (page - 1) * limit;
        List<Course> courses = courseRepository.findByCourseDifficulty(difficulty, offset, limit);
        return courses.stream()
                .map(ViewCourseDto::new)
                .collect(Collectors.toList());
    }

    public int getTotalNumberOfCoursesByDifficulty(String difficulty) {
        return courseRepository.countByCourseDifficulty(difficulty);
    }

    @Override
    public Page<Course> findAll(Pageable pageable) {
        return courseRepository.findAll(pageable);
    }

    @Override
    public Page<Course> findByCourseNameContaining(String search, Pageable pageable) {
        return courseRepository.findByCourseNameContaining(search, pageable);
    }

    @Override
    public void deleteCourse(Long courseId) {
        courseRepository.deleteById(Math.toIntExact(courseId));
    }

    public List<ViewCourseDto> getCoursesWithEnrollmentStatus(String username) {
        List<Course> courses = courseRepository.findAll();
        List<ViewCourseDto> courseDtos = new ArrayList<>();

        // Get user's enrolled courses if username is provided
        Set<Integer> enrolledCourseIds = new HashSet<>();
        if (username != null) {
            enrolledCourseIds = enrollmentRepository.findAll().stream()
                    .filter(e -> e.getUser().getUserName().equals(username))
                    .map(e -> e.getCourse().getCourseID())
                    .collect(Collectors.toSet());
        }

        // Convert courses to DTOs and set enrollment status
        for (Course course : courses) {
            ViewCourseDto dto = new ViewCourseDto(course);
            dto.setIsEnrolled(enrolledCourseIds.contains(course.getCourseID()));
            courseDtos.add(dto);
        }

        return courseDtos;
    }

    public long getTotalNumberOfCourses() {
        return courseRepository.count();
    }

    public int getTotalRevenue() {
        return courseRepository.findAll().stream()
                .mapToInt(course -> course.getCoursePrice() * course.getEnrollments().size())
                .sum();
    }
}
