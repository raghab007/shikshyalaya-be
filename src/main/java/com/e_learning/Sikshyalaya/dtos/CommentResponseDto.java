package com.e_learning.Sikshyalaya.dtos;


import com.e_learning.Sikshyalaya.entities.Comment;
import com.e_learning.Sikshyalaya.entities.CommentReply;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
public class CommentResponseDto {

    private  Integer id;
    private String comment;

    private String userName;

    private Date date;

    private List<CommentReplyResponseDto> commentReplies;

    public CommentResponseDto(Comment comment){
        this.comment = comment.getComment();
        this.userName = comment.getUser().getUserName();
        this.date = comment.getDate();
        this.id = comment.getCommentId();
    }
}
