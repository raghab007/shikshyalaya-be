package com.e_learning.Sikshyalaya.dtos;

import com.e_learning.Sikshyalaya.enums.CourseDifficulty;
import lombok.Data;

@Data
public class CourseUpdateDto {
    private String courseName;
    private String courseDescription;
    private Integer coursePrice;
    private CourseDifficulty courseDifficulty;
    private Integer categoryId;
}