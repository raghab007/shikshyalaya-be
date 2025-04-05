package com.e_learning.Sikshyalaya.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer paymentId;


    private LocalDateTime dateTime = LocalDateTime.now();


    @ManyToOne
    private  User user;

    @ManyToOne
    @JsonBackReference
    private  Course course;

    private  Integer amount;

}
