package com.e_learning.Sikshyalaya.service;

import com.e_learning.Sikshyalaya.dtos.VideoFeedbackDto;
import com.e_learning.Sikshyalaya.dtos.VideoFeedbackResponseDto;
import com.e_learning.Sikshyalaya.entities.Course;
import com.e_learning.Sikshyalaya.entities.User;
import com.e_learning.Sikshyalaya.entities.VideoFeedback;
import com.e_learning.Sikshyalaya.repositories.CourseRepository;
import com.e_learning.Sikshyalaya.repositories.UserRepository;
import com.e_learning.Sikshyalaya.repositories.VideoFeedbackRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class VideoFeedbackService   {

    @Autowired
    private VideoFeedbackRepository videoFeedbackRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CourseRepository courseRepository;

    // Custom directory path on user's desktop
    private final String baseDirectory = System.getProperty("user.home") + File.separator + "Desktop" +
            File.separator + "shikshyalaya" + File.separator + "videofeedback";

    public VideoFeedback saveVideoFeedback(VideoFeedbackDto videoFeedbackDto) {
        try {
            // Create directory if it doesn't exist
            File directory = new File(baseDirectory);
            if (!directory.exists()) {
                directory.mkdirs(); // Creates all necessary parent directories
            }

            // Generate unique filename
            MultipartFile videoFile = videoFeedbackDto.getVideoFile();
            String originalFilename = videoFile.getOriginalFilename();
            String fileExtension = originalFilename.substring(originalFilename.lastIndexOf("."));
            String uniqueFileName = UUID.randomUUID().toString() + fileExtension;

            // Save file to the directory
            Path filePath = Paths.get(directory.getAbsolutePath(), uniqueFileName);
            Files.copy(videoFile.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

            // Get user and lecture
            User user = userRepository.findById(SecurityContextHolder.getContext().getAuthentication().getName())
                    .orElseThrow(() -> new RuntimeException("User not found"));

            Course course = courseRepository.findById(videoFeedbackDto.getCourseId())
                    .orElseThrow(() -> new RuntimeException("Video not found"));

            // Create and save video feedback
            VideoFeedback videoFeedback = new VideoFeedback();
            videoFeedback.setVideoUrl(uniqueFileName);
            videoFeedback.setUser(user);
            videoFeedback.setCourse(course);

            return videoFeedbackRepository.save(videoFeedback);
        } catch (IOException e) {
            throw new RuntimeException("Failed to store video feedback", e);
        }
    }

    public VideoFeedback getVideoFeedbackById(Integer id) {
        return videoFeedbackRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Video feedback not found"));
    }

    public void deleteVideoFeedback(Integer id) {
        VideoFeedback videoFeedback = getVideoFeedbackById(id);
        try {
            // Delete the video file
            Files.deleteIfExists(Paths.get(videoFeedback.getVideoUrl()));
            // Delete the record
            videoFeedbackRepository.delete(videoFeedback);
        } catch (IOException e) {
            throw new RuntimeException("Failed to delete video feedback", e);
        }
    }

    public List<VideoFeedbackResponseDto> getAllVideoFeedbacks() {

        List<VideoFeedbackResponseDto> list= new ArrayList<>();
         videoFeedbackRepository.findAll().forEach(videoFeedback -> {
             VideoFeedbackResponseDto videoFeedbackResponseDto = new VideoFeedbackResponseDto(videoFeedback);
             list.add(videoFeedbackResponseDto);
         });
         return list;
    }
}