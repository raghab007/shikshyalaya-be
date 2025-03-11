package com.e_learning.Sikshyalaya.dtos;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;

@Getter
@Setter
public class LectureRequestDto {
    private String title;
    private String description;
    private MultipartFile video;
    private MultipartFile image;
}
