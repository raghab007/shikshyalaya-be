package com.e_learning.Sikshyalaya.service;

import com.e_learning.Sikshyalaya.entities.Course;
import com.e_learning.Sikshyalaya.entities.Enrollment;
import com.e_learning.Sikshyalaya.repositories.EnrollmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Service
public class InstructorService {

    @Autowired
    private CourseService courseService;

    @Autowired
    private EnrollmentRepository enrollmentRepository;

    public String saveVideo(MultipartFile video) throws IOException {
        String userHome = System.getProperty("user.home") + File.separator + "Desktop";
        File directory = new File(userHome, "shikshyalaya/course/videos");
        // Create directory if it doesn't exist
        if (!directory.exists()) {
            System.out.println(directory.mkdirs());
        }
        FileOutputStream fileOutputStream = getFileOutputStream(video, directory);
        fileOutputStream.close();
        return video.getOriginalFilename();
    }

    private FileOutputStream getFileOutputStream(MultipartFile video, File directory) throws IOException {
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
        String userHome = System.getProperty("user.home") + File.separator + "Desktop";
        File directory = new File(userHome, "shikshyalaya/course/thumbnails");
        if (!directory.exists()) {
            directory.mkdirs();
        }

        if (image == null || image.getOriginalFilename() == null || image.getOriginalFilename().isBlank()) {
            throw new IllegalArgumentException("Invalid file");
        }

        String originalFileName = image.getOriginalFilename();
        File uploadFile = new File(directory, originalFileName);
        byte[] bytes = image.getBytes();
        FileOutputStream fileOutputStream = new FileOutputStream(uploadFile);
        fileOutputStream.write(bytes);
        fileOutputStream.close();
        return image.getOriginalFilename();
    }


    public String updateCourseImage(MultipartFile image, Integer courseId) throws IOException {
        String userHome = System.getProperty("user.home") + File.separator + "Desktop";
        File directory = new File(userHome, "shikshyalaya/course/images");
        Course course = courseService.findById(courseId);
        if (course != null) {
            File oldCourseImage = new File(directory, course.getImageUrl());
            if (oldCourseImage.exists()) {
                boolean delete = oldCourseImage.delete();
                if (delete) {
                    String filePath = "shikshyalaya/course/images";
                    String imageName = getRandomImageUrl() + getFileExtenstion(image.getOriginalFilename());
                    File uploadFile = new File(filePath, imageName).getAbsoluteFile();
                    image.transferTo(uploadFile);
                    course.setImageUrl(image.getOriginalFilename());
                    courseService.saveCourse(course);
                    return "Success";

                }
            }
        }
        return "failed";
    }


    public Integer totalRevenue(String userName) {
        List<Enrollment> enrollments = enrollmentRepository.findAll();
        enrollments = enrollments.stream().filter(enrollment -> enrollment.getCourse().getInstructor().getUserName().equals(userName)).toList();
        Integer amount = 0;
        for (Enrollment enrollment : enrollments) {
            Course course = enrollment.getCourse();
            amount = amount + course.getCoursePrice();
        }
        return amount;
    }

    public String getFileExtenstion(String fileName) {
        int i = fileName.lastIndexOf('.');
        return fileName.substring(i);
    }

    public String getRandomImageUrl() {
        return UUID.randomUUID().toString();
    }

    public List<Enrollment> getAllStudentEnrollmentsByInstructor(String instructorUserName) {
        return enrollmentRepository.findAllByInstructor(instructorUserName);
    }


}
