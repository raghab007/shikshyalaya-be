package com.e_learning.Sikshyalaya.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.engine.internal.Cascade;

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
    private List<Enrollment> enrollments;
    @ManyToOne()
    @JoinColumn(name = "category_id")
    private  CourseCategory category;
    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL)
    @JsonBackReference
    private  List<Section> sections = new ArrayList<>();
    @ManyToOne
    @JsonBackReference
    private User instructor;

}
