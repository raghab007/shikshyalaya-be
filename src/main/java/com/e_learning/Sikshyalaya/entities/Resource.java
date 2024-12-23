package com.e_learning.Sikshyalaya.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Resource {
    @Id
    private Integer resourceId;
    private String resourceName;
    private String resourceUrl;
}
