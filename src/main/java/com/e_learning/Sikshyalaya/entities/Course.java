package com.e_learning.Sikshyalaya.entities;

import com.e_learning.Sikshyalaya.dtos.RequestCourseDto;
import com.e_learning.Sikshyalaya.enums.CourseDifficulty;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
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
    private String imageUrl;
    private Integer courseDuration;
    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<Enrollment> enrollments = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    private CourseDifficulty courseDifficulty;
    @ManyToOne()
    @JoinColumn(name = "category_id")
    @JsonBackReference
    private CourseCategory category;

    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<Section> sections = new ArrayList<>();

    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<Message> messages;

    @ManyToOne
    @JsonIgnore
    private User instructor;

    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<Payment> payments = new ArrayList<>();

    @OneToMany(mappedBy = "course")
    @JsonManagedReference
    private List<VideoFeedback> videoFeedbacks = new ArrayList<>();
    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<RatingReview> ratingReviews = new ArrayList<>();


    public Course(RequestCourseDto courseDto) {
        courseName = courseDto.getCourseName();
        courseDescription = courseDto.getCourseDescription();
        coursePrice = courseDto.getCoursePrice();
        imageUrl = courseDto.getCourseImage().getOriginalFilename();
        courseDuration = courseDto.getCourseDuration();
    }
}
