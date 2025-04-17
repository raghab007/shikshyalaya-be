package com.e_learning.Sikshyalaya.service;

import com.e_learning.Sikshyalaya.entities.Course;
import com.e_learning.Sikshyalaya.entities.Enrollment;
import com.e_learning.Sikshyalaya.entities.Section;
import com.e_learning.Sikshyalaya.entities.User;
import com.e_learning.Sikshyalaya.interfaces.ICourseService;
import com.e_learning.Sikshyalaya.repositories.*;
import com.e_learning.Sikshyalaya.utils.Constants;
import com.e_learning.Sikshyalaya.utils.StorageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;

@Service
public class CourseService implements ICourseService {
    @Autowired
    private final CourseRepository courseRepository;

    @Autowired
    EnrollmentRepository enrollmentRepository;

    @Autowired
    private SectionRepository sectionRepository;

    private ResourceRepository resourceRepository;

    private UserRepository userRepository;

    StorageUtil storageUtil;

    public CourseService(CourseRepository courseRepository, StorageUtil storageUtil) {
        this.courseRepository = courseRepository;
        this.storageUtil = storageUtil;
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
        String originalFilename = storageUtil.getRandomImageUrl() + storageUtil.getFileExtenstion(imageFile.getOriginalFilename());
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
        return enrollmentRepository.findAll().stream().map(Enrollment::getCourse).filter(course -> course.getInstructor().getUserName().equals(instructorName)).count();
    }


    public HashMap<String, Integer> getNumberOfStudentsByCourse(String instructorName) {
        User user = userRepository.findByUserName(instructorName).orElseThrow(() -> new RuntimeException("User not fount"));
        List<Course> courseList = user.getCourses();
        Map<String, Integer> map = new HashMap<>();
        //courseList.forEach(course -> );
        return null;
    }


    // New search methods
    public List<Course> searchCourses(String query, int offset, int limit) {
        return courseRepository.searchCoursesByNamePaginated(query, offset, limit);
    }

    public int countSearchResults(String query) {
        return courseRepository.countSearchResultsByName(query);
    }
}














