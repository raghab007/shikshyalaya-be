package com.e_learning.Sikshyalaya.controllers;


import com.e_learning.Sikshyalaya.entities.RequestCourseDto;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DemoController {

    @GetMapping("/demo")
    public String getReq(@ModelAttribute RequestCourseDto courseDto) {
        Authentication authentication;

        try {
            authentication = SecurityContextHolder.getContext().getAuthentication();
            System.out.println(authentication.getName() + authentication.getAuthorities());
        } catch (Exception e) {
            System.out.println("Exception:" + e);
            return "Server error";
        }
        System.out.println(courseDto);
        System.out.println(courseDto.getCourseImage());
        System.out.println("Hello");
        return  authentication.getName();
    }
    @GetMapping("/check")
    public String check(){
        return "Ok";
    }
}
