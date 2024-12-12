package com.e_learning.Sikshyalaya.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;


@Entity
@Getter
@Setter
public class CourseCategory {
    @Id
    private  Integer courseCategoryId;

    private String courseCategoryName;

    @OneToMany(mappedBy = "category")
    private  List<Course> courses = new ArrayList<>();
}
