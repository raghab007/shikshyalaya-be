package com.e_learning.Sikshyalaya.dtos;

import com.e_learning.Sikshyalaya.enums.CourseDifficulty;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
public class RequestCourseDto {
    private String courseName;
    private String courseDescription;
    private Integer coursePrice;
    private Integer courseDuration;
    private MultipartFile courseImage;
    private Integer categoryId;
    private CourseDifficulty courseDifficulty;
}
