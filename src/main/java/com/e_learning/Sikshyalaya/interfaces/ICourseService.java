package com.e_learning.Sikshyalaya.interfaces;

import com.e_learning.Sikshyalaya.entities.Course;
import com.e_learning.Sikshyalaya.entities.Section;
import java.util.List;

public interface ICourseService {

     void saveCourse(Course course);

     void deleteById(Integer id);

     void updateCourseSection(Section section);

     Course findById(Integer id);

     List<Course> findAll();
}

