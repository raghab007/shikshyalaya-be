package com.e_learning.Sikshyalaya.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Comment {
    @Id
    private Long commentId;
    private String comment;
    @ManyToOne
    private Lecture lecture;
}
