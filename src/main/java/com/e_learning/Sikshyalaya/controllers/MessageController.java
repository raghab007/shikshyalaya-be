package com.e_learning.Sikshyalaya.controllers;


import com.e_learning.Sikshyalaya.dtos.MessageRequestDto;
import com.e_learning.Sikshyalaya.dtos.MessageResponseDto;
import com.e_learning.Sikshyalaya.entities.Message;
import com.e_learning.Sikshyalaya.repositories.CourseRepository;
import com.e_learning.Sikshyalaya.repositories.MessageRepository;
import com.e_learning.Sikshyalaya.repositories.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@RestController
public class MessageController {

    CourseRepository courseRepository;

    MessageRepository messageRepository;

    UserRepository userRepository;

    public  MessageController(CourseRepository courseRepository, MessageRepository messageRepository, UserRepository userRepository){
       this.courseRepository = courseRepository;
       this.messageRepository = messageRepository;
       this.userRepository = userRepository;
    }

    @PostMapping("/messages")
    public ResponseEntity<?> saveMessage(@RequestBody MessageRequestDto messageRequestDto){
        Message message = new Message();
        message.setMessage(messageRequestDto.getMessage());
        message.setDate(messageRequestDto.getDate());
        // Set course and user relationships
        message.setCourse(courseRepository.findById(messageRequestDto.getCourseId()).orElseThrow(
                () -> new RuntimeException("Course not found")));

        message.setUser(userRepository.findByUserName(messageRequestDto.getUserName()).orElseThrow(
                () -> new RuntimeException("User not found")));

        Message save = messageRepository.save(message);

        return new ResponseEntity<>(HttpStatus.OK);

    }


    @GetMapping("/messages/{courseId}")
    public List<MessageResponseDto> getMessages(@PathVariable Integer courseId){
        return messageRepository.findAll().stream().filter(message -> Objects.equals(message.getCourse().getCourseID(), courseId)).map(message ->new MessageResponseDto(message)).toList();
    }


}
