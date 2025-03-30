package com.e_learning.Sikshyalaya.dtos;


import com.e_learning.Sikshyalaya.entities.Comment;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class CommentResponseDto {

    private String comment;

    private String userName;

    private Date date;

    public CommentResponseDto(Comment comment){
        this.comment = comment.getComment();
        this.userName = comment.getUser().getUserName();
        this.date = comment.getDate();
    }
}
