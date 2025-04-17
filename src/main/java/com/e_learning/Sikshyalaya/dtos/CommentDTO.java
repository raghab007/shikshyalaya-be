package com.e_learning.Sikshyalaya.dtos;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class CommentDTO {
    private Integer commentId;
    private String comment;
    private String userName;
    private String userFirstName;
    private String userLastName;
    private Date date;
    private String lectureTitle;
    private String courseName;
    private List<CommentReplyDTO> replies;
    private String role;
}