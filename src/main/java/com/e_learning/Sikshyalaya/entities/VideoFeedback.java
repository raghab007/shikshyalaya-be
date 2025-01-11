package com.e_learning.Sikshyalaya.entities;

import jakarta.persistence.*;

@Entity
public class VideoFeedback {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String videoUrl;

    @ManyToOne
    private Lecture lecture;

    @ManyToOne
    private User user;
}
