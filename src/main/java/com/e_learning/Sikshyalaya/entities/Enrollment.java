package com.e_learning.Sikshyalaya.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
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
    @JsonBackReference
    private Course course;

    @ManyToOne
    @JsonBackReference
    private User user;
    private LocalDateTime enrollmentDate = LocalDateTime.now();

}
