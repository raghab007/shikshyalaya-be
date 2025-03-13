package com.e_learning.Sikshyalaya.dtos;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
public class LectureRequestDto {
    private String title;
    private String description;
    private MultipartFile video;
    private MultipartFile image;
}
