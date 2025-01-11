package com.e_learning.Sikshyalaya.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Resource {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer resourceId;
    private String resourceName;
    private String resourceUrl;
    private  String resourceType;
    private String resourceDescription;

}
