package com.e_learning.Sikshyalaya.dtos;

import com.e_learning.Sikshyalaya.entities.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
@Getter
@Setter
public class LectureReponseDto {

    private Integer id;
    private String title;
    private String description;
    private Date uploadedDate;
    private String videoUrl;
    private String imageUrl;
    private List<Comment> comments = new ArrayList<>();
    private  boolean isCompleted = false;

    public LectureReponseDto(Lecture lecture){
        this.id = lecture.getId();
        this.title = lecture.getTitle();
        this.description = lecture.getDescription();
        this.uploadedDate = lecture.getUploadedDate();
        this.videoUrl = lecture.getVideoUrl();
        this.imageUrl = lecture.getImageUrl();
        this.comments = lecture.getComments();
    }



}
