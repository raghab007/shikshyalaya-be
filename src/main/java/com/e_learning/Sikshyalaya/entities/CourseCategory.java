package com.e_learning.Sikshyalaya.entities;

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
public class CourseCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Integer courseCategoryId;

    private String courseCategoryName;

    @OneToMany(mappedBy = "category")
    private  List<Course> courses = new ArrayList<>();
}
