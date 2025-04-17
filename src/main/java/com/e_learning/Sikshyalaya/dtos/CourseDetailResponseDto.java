package com.e_learning.Sikshyalaya.dtos;

import com.e_learning.Sikshyalaya.entities.Course;
import com.e_learning.Sikshyalaya.entities.Section;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CourseDetailResponseDto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer courseID;
    private String courseName;
    private String courseDescription;
    private Integer coursePrice;
    private String imageUrl;
    private Integer courseDuration;
    private Integer totalEnrollments;
    private String courseCategory;
    private String instructorName;
    private Integer totalSections;
    private List<Section> sections;


    public CourseDetailResponseDto(Course course) {
        this.courseID = course.getCourseID();
        this.courseName = course.getCourseName();
        this.courseDescription = course.getCourseDescription();
        this.instructorName = course.getInstructor().getFirstName() + course.getInstructor().getLastName();
        //this.courseCategory = course.getCategory().getCourseCategoryName();
        this.imageUrl = course.getImageUrl();
        this.coursePrice = course.getCoursePrice();
        this.totalEnrollments = course.getEnrollments().size();
        this.totalSections = course.getSections().size();
        sections = course.getSections();
    }

}
