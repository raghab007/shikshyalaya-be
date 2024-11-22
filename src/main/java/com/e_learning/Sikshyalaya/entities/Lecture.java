package com.e_learning.Sikshyalaya.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Lecture {
    @Id
    private Integer id;
    private String title;
    private String description;
    private Integer year;
    private String videoUrl;
    @OneToMany(mappedBy = "lecture")
    private List<Comment> comments = new ArrayList<>();

    @OneToMany
    private  List<VideoFeedback> videoFeedbacks = new ArrayList<>();
}
