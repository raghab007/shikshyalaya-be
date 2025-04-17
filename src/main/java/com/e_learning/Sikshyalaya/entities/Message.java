package com.e_learning.Sikshyalaya.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Entity
@Getter
@Setter
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer messageId;

    @ManyToOne
    @JoinColumn(name = "courseId")
    @JsonBackReference
    private Course course;


    @ManyToOne
    @JoinColumn(name = "userName")
    @JsonBackReference
    private User user;

    private Date date;

    private String message;
}
