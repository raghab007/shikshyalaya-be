package com.e_learning.Sikshyalaya.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.UUID;

@Slf4j
@Component
public class StorageUtil {

    public boolean saveImage(MultipartFile file, String path) {
        if (file.isEmpty()) {
            return false;
        }
        String resourcePath = new File(path).getAbsolutePath();
        log.info(resourcePath);
        System.out.println(resourcePath);
        File filePath = new File(resourcePath);
        if (!filePath.exists()) {
            filePath.mkdirs();
        }
        try {
            file.transferTo(new File(path + file.getOriginalFilename()));
            return true;
        } catch (Exception e) {
            log.error(e.getMessage());
            return false;
        }
    }


    public String getFileExtenstion(String fileName) {
        int i = fileName.lastIndexOf('.');
        return fileName.substring(i);
    }

    public String getRandomImageUrl() {
        return UUID.randomUUID().toString();
    }


}
