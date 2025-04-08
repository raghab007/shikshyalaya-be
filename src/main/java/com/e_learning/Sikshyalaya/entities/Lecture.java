package com.e_learning.Sikshyalaya.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
public class Lecture {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String title;
    private String description;
    private Date uploadedDate;
    private String videoUrl;
    private String imageUrl;

    @OneToMany(mappedBy = "lecture")
    @JsonManagedReference
    private List<Comment> comments = new ArrayList<>();

    @ManyToOne
    @JsonBackReference
    private Section section;

    @OneToMany
    private  List<VideoFeedback> videoFeedbacks = new ArrayList<>();


    @OneToMany(mappedBy = "lecture")
    @JsonIgnore
    List<UserProgress> userProgresses = new ArrayList<>();

    @OneToOne
    private Resource resource;
}
