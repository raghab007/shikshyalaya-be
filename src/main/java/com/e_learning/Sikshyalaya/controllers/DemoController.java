package com.e_learning.Sikshyalaya.controllers;


import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DemoController {

    @GetMapping("/demo")
    public void getReq(HttpServletRequest request){
        System.out.println(request.getSession().getId());
    }
}