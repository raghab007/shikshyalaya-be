package com.e_learning.Sikshyalaya.dtos;

import com.e_learning.Sikshyalaya.entities.Message;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class MessageResponseDto{
    private Integer courseId;
    private String userName;
    private String message;
    private Date date;

    public MessageResponseDto(Message message){
        this.setMessage(message.getMessage());
        this.setCourseId(message.getCourse().getCourseID());
        this.setUserName(message.getUser().getUserName());
        this.setDate(message.getDate());
    }
}

