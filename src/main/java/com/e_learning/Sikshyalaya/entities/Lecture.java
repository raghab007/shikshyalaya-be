package com.e_learning.Sikshyalaya.entities;

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
    private List<Comment> comments = new ArrayList<>();

    @ManyToOne
    private Section section;

    @OneToMany
    private  List<VideoFeedback> videoFeedbacks = new ArrayList<>();

    @OneToOne
    private Resource resource;
}
