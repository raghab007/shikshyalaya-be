package com.e_learning.Sikshyalaya.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CourseResponseDto {
    private String name;
    private String description;
    private  Integer price;

}
