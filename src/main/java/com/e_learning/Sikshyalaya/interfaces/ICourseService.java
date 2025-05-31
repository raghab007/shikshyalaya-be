package com.e_learning.Sikshyalaya.interfaces;

import com.e_learning.Sikshyalaya.dtos.CourseChartDataDto;
import com.e_learning.Sikshyalaya.dtos.ViewCourseDto;
import com.e_learning.Sikshyalaya.entities.Course;
import com.e_learning.Sikshyalaya.entities.Section;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface ICourseService {

     void saveCourse(Course course, MultipartFile imageFile) throws IOException;

     void saveCourse(Course course);

     void deleteById(Integer id);

     void updateCourseSection(Section section);

     Course findById(Integer id);

     List<Course> findAll();

     List<Course> getCourseByInstructor(String instructorName);

     long getTotalNumberOfCoursesByInstructor(String instructorName);

     long getTotalEnrolledStudentsByInstructor(String instructorName);

     List<CourseChartDataDto> getCourseChartData(String instructorUsername);

     Integer getTotalRevenue(String instructorUsername);

     List<ViewCourseDto> getCoursesByCategory(int page, int limit, int categoryId);

     int getTotalNumberOfCoursesByCategory(int categoryId);

     List<ViewCourseDto> getCoursesByPriceRange(Double min, Double max, int page, int limit);

     int getTotalNumberOfCoursesByPriceRange(Double min, Double max);

     List<ViewCourseDto> getCoursesByDifficulty(String difficulty, int page, int limit);

     int getTotalNumberOfCoursesByDifficulty(String difficulty);

     // New methods for pagination and search
     Page<Course> findAll(Pageable pageable);

     Page<Course> findByCourseNameContaining(String search, Pageable pageable);

     void deleteCourse(Long courseId);
}
