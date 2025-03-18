package com.e_learning.Sikshyalaya.entities;

import com.e_learning.Sikshyalaya.dtos.RequestCourseDto;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer courseID;
    private String courseName;
    private String courseDescription;
    private Integer coursePrice;
    private  String imageUrl;
    private Integer courseDuration;
    @OneToMany(mappedBy = "course")
    @JsonManagedReference
    private List<Enrollment> enrollments = new ArrayList<>();

    @ManyToOne()
    @JoinColumn(name = "category_id")
    @JsonBackReference
    private  CourseCategory category;

    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL)
    @JsonManagedReference
    private  List<Section> sections = new ArrayList<>();



    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<Message> messages;

    @ManyToOne
    @JsonBackReference
    private User instructor;
    public  Course(RequestCourseDto courseDto){
        courseName = courseDto.getCourseName();
        courseDescription = courseDto.getCourseDescription();
        coursePrice = courseDto.getCoursePrice();
        imageUrl = courseDto.getCourseImage().getOriginalFilename();
        courseDuration = courseDto.getCourseDuration();
    }
}
