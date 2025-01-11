package com.e_learning.Sikshyalaya.service;

import com.e_learning.Sikshyalaya.entities.Course;
import com.e_learning.Sikshyalaya.entities.Section;
import com.e_learning.Sikshyalaya.interfaces.ICourseService;
import com.e_learning.Sikshyalaya.repositories.CourseRepository;
import com.e_learning.Sikshyalaya.repositories.ResourceRepository;
import com.e_learning.Sikshyalaya.repositories.SectionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CourseService implements ICourseService {

    @Autowired
    private final   CourseRepository courseRepository;

    @Autowired
    private SectionRepository sectionRepository;

    private ResourceRepository resourceRepository;
    public CourseService(CourseRepository courseRepository){
        this.courseRepository = courseRepository;
    }

    public void saveCourse(Course course){
        courseRepository.save(course);
    }

    public void deleteById(Integer id){
        courseRepository.deleteById(id);
    }

    public void updateCourseSection(Section section){
      return;
    }

    public Course findById(Integer id){
        Optional<Course> course = courseRepository.findById(id);
        return course.orElse(null);
    }

    public List<Course> findAll(){
        return courseRepository.findAll();
    }

}
