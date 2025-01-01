package com.e_learning.Sikshyalaya.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

@Slf4j
@Component
public class StorageUtil {

    public boolean saveImage(MultipartFile file, String path){
        if(file.isEmpty()){
            return false;
        }
        String resourcePath = new File(path).getAbsolutePath();
        File filePath = new File(resourcePath);
        if(!filePath.exists()){
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
}
