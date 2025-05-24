package com.e_learning.Sikshyalaya.controllers;

import com.e_learning.Sikshyalaya.dtos.VideoFeedbackDto;
import com.e_learning.Sikshyalaya.dtos.VideoFeedbackResponseDto;
import com.e_learning.Sikshyalaya.entities.VideoFeedback;
import com.e_learning.Sikshyalaya.service.VideoFeedbackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/video-feedback")
public class VideoFeedbackController {

    @Autowired
    private VideoFeedbackService videoFeedbackService;

    @PostMapping
    public ResponseEntity<VideoFeedback> submitVideoFeedback(@ModelAttribute VideoFeedbackDto videoFeedbackDto) {
        VideoFeedback savedFeedback = videoFeedbackService.saveVideoFeedback(videoFeedbackDto);
        return ResponseEntity.ok(savedFeedback);
    }

    @GetMapping("/{id}")
    public ResponseEntity<VideoFeedback> getVideoFeedback(@PathVariable Integer id) {
        VideoFeedback videoFeedback = videoFeedbackService.getVideoFeedbackById(id);
        return ResponseEntity.ok(videoFeedback);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteVideoFeedback(@PathVariable Integer id) {
        videoFeedbackService.deleteVideoFeedback(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<VideoFeedbackResponseDto>> getAllVideoFeedbacks() {
        videoFeedbackService.getAllVideoFeedbacks();
        return ResponseEntity.ok(videoFeedbackService.getAllVideoFeedbacks());
    }
}