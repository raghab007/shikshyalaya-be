package com.e_learning.Sikshyalaya.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Enrollment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private   Integer enrollmentId;
    @ManyToOne
    private Course course;
    @ManyToOne
    private User user;
    private LocalDateTime enrollmentDate = LocalDateTime.now();

}
