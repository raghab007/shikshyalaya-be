package com.e_learning.Sikshyalaya.entities;


import com.e_learning.Sikshyalaya.enums.ProgressStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class UserProgress {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Integer progressId;

    @ManyToOne
    private  User user;

    @ManyToOne
    private Lecture lecture;
}
