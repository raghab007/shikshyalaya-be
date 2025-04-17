package com.e_learning.Sikshyalaya.dtos;

import lombok.Data;

import java.util.Date;

@Data
public class CommentReplyDTO {
    private Integer id;
    private String reply;
    private String userName;
    private String userFirstName;
    private String userLastName;
    private Date date;
    private String role;
}