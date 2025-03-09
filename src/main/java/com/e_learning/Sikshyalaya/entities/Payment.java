package com.e_learning.Sikshyalaya.entities;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer paymentId;


    private LocalDateTime dateTime;

//    @ManyToOne
//    private  Enrollment enrollment;
}
