package com.e_learning.Sikshyalaya.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Objects;

@RestController
@RequestMapping
@Slf4j
public class FileController {
    @PostMapping("/upload")
    public String uploadFile(@RequestParam("file") MultipartFile file,@RequestParam("key") String key) throws IOException {
        System.out.println(key);
        System.out.println(file);
        if (file.isEmpty()) {
           return "File is empty";
       }
       try {
           String resourcePath = new File("C:/Users/A S U S/Desktop/FinalYearProject/shikshyalaya-be/src/main/resources/static/images").getPath();
           //String path = Paths.get("src/main/resources/static/images").toAbsolutePath().toString();
           log.info(resourcePath);
           File directory = new File(resourcePath);
           if (!directory.exists()) {
               directory.mkdirs();
           }
          File uploadedFile = new File(directory, Objects.requireNonNull(file.getOriginalFilename()));
           file.transferTo(uploadedFile);
           return "File uploaded successfully";
       }catch (Exception e){
           log.error(e.getMessage());
           return "File upload failed";
       }
    }

    @PostMapping("upload_video")
    public String uploadVideo (@RequestParam("file") MultipartFile file) throws IOException {
        if (file.isEmpty()) {
            return "file is empty";
        } else {
            String resourcePath = new File("src/main/resources/static/videos").getAbsolutePath();
            File directory = new File(resourcePath);
            if (!directory.exists()) {
                directory.mkdirs();
            }
            File uploadFile = new File(directory,file.getOriginalFilename());
            file.transferTo(uploadFile);
            return "Successfully uploaded";
        }
    }
}

