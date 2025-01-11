package com.e_learning.Sikshyalaya.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Section {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int sectionId;
    private String name;
    private String description;
    @OneToMany
    private List<Lecture> lectures = new ArrayList<>();
    @ManyToOne(cascade = CascadeType.ALL)
    @JsonBackReference
    private  Course course;
}
