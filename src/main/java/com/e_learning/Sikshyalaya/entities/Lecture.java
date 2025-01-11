package com.e_learning.Sikshyalaya.entities;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Lecture {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String title;
    private String description;
    private Integer year;
    private String videoUrl;
    @OneToMany(mappedBy = "lecture")
    private List<Comment> comments = new ArrayList<>();

    @OneToMany
    private  List<VideoFeedback> videoFeedbacks = new ArrayList<>();

    @OneToOne
    private Resource resource;
}
