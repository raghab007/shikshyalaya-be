package com.e_learning.Sikshyalaya.dtos;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class MessageRequestDto {
    private Integer courseId;
    private String userName;
    private String message;
    private Date date;
}
