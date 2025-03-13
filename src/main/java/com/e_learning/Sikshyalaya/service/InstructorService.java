package com.e_learning.Sikshyalaya.service;

import com.e_learning.Sikshyalaya.entities.Course;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

@Service
public class InstructorService {

    @Autowired
    CourseService courseService;

    public String saveVideo(MultipartFile video) throws IOException {
        File directory = new File("src/main/resources/static/videos/course").getAbsoluteFile();
        // Create directory if it doesn't exist
        if (!directory.exists()) {
            System.out.println(directory.mkdirs());
        }
        FileOutputStream fileOutputStream = getFileOutputStream(video, directory);
        fileOutputStream.close();
        return video.getOriginalFilename();
    }

    private  FileOutputStream getFileOutputStream(MultipartFile video, File directory) throws IOException {
        if (video == null || video.getOriginalFilename() == null || video.getOriginalFilename().isBlank()) {
            throw new IllegalArgumentException("Invalid file or filename");
        }
        // Getting file name
        String originalFilename = video.getOriginalFilename();
        File uploadFile = new File(directory, originalFilename);
        byte[] bytes = video.getBytes();
        FileOutputStream fileOutputStream = new FileOutputStream(uploadFile);
        fileOutputStream.write(bytes);
        return fileOutputStream;
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


    public String updateCourseImage(MultipartFile image, Integer courseId) throws IOException {
        File file = new File("src/main/resources/static/images/course").getAbsoluteFile();
        Course course =  courseService.findById(courseId);
        if (course!=null){
            File oldCourseImage = new File(file, course.getImageUrl());
            if (oldCourseImage.exists()) {
                boolean delete = oldCourseImage.delete();
                if (delete){
                    String filePath = "src/main/resources/static/images/course";
                    File uploadFile = new File(filePath,image.getOriginalFilename()).getAbsoluteFile();
                    image.transferTo(uploadFile);
                    return "Success";

                }
            }
        }
        return "failed";
    }


}
