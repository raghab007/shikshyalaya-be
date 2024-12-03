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
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer courseID;
    private String courseName;
    private String courseDescription;

   @OneToMany(mappedBy = "course")
   private List<Enrollment> enrollments;


    @OneToMany
    private  List<Section> sections = new ArrayList<>();

    @OneToOne
    private User instructor;

}
