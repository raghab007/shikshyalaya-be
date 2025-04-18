package com.e_learning.Sikshyalaya.dtos;


import com.e_learning.Sikshyalaya.entities.CommentReply;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter

public class CommentReplyResponseDto {

    private String userName;

    private Date replyDate;

    private String commentReply;

    private String role;

    public CommentReplyResponseDto(CommentReply commentReply) {
        this.replyDate = commentReply.getDate();
        this.userName = commentReply.getUser().getUserName();
        this.commentReply = commentReply.getReply();
        this.role = commentReply.getUser().getRole();
    }
}
