package com.e_learning.Sikshyalaya.dtos;

import com.e_learning.Sikshyalaya.entities.VideoFeedback;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VideoFeedbackResponseDto {

    private String videoUrl;
    private UserResponseDto user;
    private CourseResponseDto course;

    public VideoFeedbackResponseDto(VideoFeedback videoFeedback) {
       videoUrl = videoFeedback.getVideoUrl();
       user = videoFeedback.getUser() != null ? new UserResponseDto(videoFeedback.getUser()) : null;
       course = videoFeedback.getCourse()!=null?new CourseResponseDto(videoFeedback.getCourse()):null;
    }
}
