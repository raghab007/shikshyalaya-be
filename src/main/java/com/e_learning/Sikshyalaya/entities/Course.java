package com.e_learning.Sikshyalaya.entities;
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

   @OneToMany(mappedBy = "course")
   private List<Enrollment> enrollments;

   @ManyToOne()
   @JoinColumn(name = "category_id")
   private  CourseCategory category;

    @OneToMany
    private  List<Section> sections = new ArrayList<>();

    @ManyToOne
    private User instructor;

}
