package com.e_learning.Sikshyalaya.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

@Entity
public class VideoFeedback {
    @Id
    private Integer id;

    private String videoUrl;

    @ManyToOne
    private Lecture lecture;

    @ManyToOne
    private User user;
}
