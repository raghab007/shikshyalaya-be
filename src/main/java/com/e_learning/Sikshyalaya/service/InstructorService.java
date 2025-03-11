package com.e_learning.Sikshyalaya.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

@Service
public class InstructorService {
    public String saveVideo(MultipartFile video) throws IOException {
        File directory = new File("src/main/resources/static/videos/course").getAbsoluteFile();
        // Create directory if it doesn't exist
        if (!directory.exists()) {
            System.out.println(directory.mkdirs());
        }
        if (video == null || video.getOriginalFilename() == null || video.getOriginalFilename().isBlank()) {
            throw new IllegalArgumentException("Invalid file or filename");
        }
        // Getting file name
        String originalFilename = video.getOriginalFilename();
        File uploadFile = new File(directory, originalFilename);
        byte[] bytes = video.getBytes();
        FileOutputStream fileOutputStream = new FileOutputStream(uploadFile);
        fileOutputStream.write(bytes);
        fileOutputStream.close();
        return video.getOriginalFilename();
    }

    public String saveThumbnail(MultipartFile image) throws IOException {
        File directory = new File("src/main/resources/static/images/thumbnails");
        if (!directory.exists()){
            directory.mkdirs();
        }

        if (image==null || image.getOriginalFilename()==null|| image.getOriginalFilename().isBlank()){
            throw  new IllegalArgumentException("Invalid file");
        }

        String originalFileName = image.getOriginalFilename();
        File uploadFile = new File(directory, originalFileName);
        byte [] bytes = image.getBytes();
        FileOutputStream fileOutputStream  = new FileOutputStream(uploadFile);
        fileOutputStream.write(bytes);
        fileOutputStream.close();
        return image.getOriginalFilename();
    }


}
