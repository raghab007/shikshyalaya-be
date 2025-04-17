package com.e_learning.Sikshyalaya.service;


import com.e_learning.Sikshyalaya.dtos.CommentDTO;
import com.e_learning.Sikshyalaya.dtos.CommentReplyDTO;
import com.e_learning.Sikshyalaya.entities.Comment;
import com.e_learning.Sikshyalaya.entities.CommentReply;
import com.e_learning.Sikshyalaya.entities.User;
import com.e_learning.Sikshyalaya.repositories.CommentReplyRepository;
import com.e_learning.Sikshyalaya.repositories.CommentRepository;
import com.e_learning.Sikshyalaya.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentService {

    private final CommentRepository commentRepository;
    private final CommentReplyRepository commentReplyRepository;
    private final UserRepository userRepository;

    @Autowired
    public CommentService(CommentRepository commentRepository,
                          CommentReplyRepository commentReplyRepository,
                          UserRepository userRepository) {
        this.commentRepository = commentRepository;
        this.commentReplyRepository = commentReplyRepository;
        this.userRepository = userRepository;
    }

    public List<CommentDTO> getCommentsForInstructorCourses(String courseName) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        List<Comment> comments;
        if (courseName != null && !courseName.isEmpty()) {
            comments = commentRepository.findByInstructorAndCourseName(username, courseName);
        } else {
            comments = commentRepository.findAllByInstructor(username);
        }

        return comments.stream()
                .map(this::convertToCommentDTO)
                .collect(Collectors.toList());
    }

    public CommentReplyDTO addReplyToComment(Integer commentId, CommentReplyDTO replyDTO) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findById(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new RuntimeException("Comment not found"));

        CommentReply reply = new CommentReply();
        reply.setReply(replyDTO.getReply());
        reply.setUser(user);
        reply.setComment(comment);
        reply.setDate(new Date());

        CommentReply savedReply = commentReplyRepository.save(reply);
        return convertToCommentReplyDTO(savedReply);
    }

    private CommentDTO convertToCommentDTO(Comment comment) {
        CommentDTO dto = new CommentDTO();
        dto.setCommentId(comment.getCommentId());
        dto.setComment(comment.getComment());
        dto.setDate(comment.getDate());
        dto.setUserName(comment.getUser().getUserName());
        dto.setUserFirstName(comment.getUser().getFirstName());
        dto.setUserLastName(comment.getUser().getLastName());

        if (comment.getLecture() != null) {
            dto.setLectureTitle(comment.getLecture().getTitle());
            if (comment.getLecture().getSection() != null &&
                    comment.getLecture().getSection().getCourse() != null) {
                dto.setCourseName(comment.getLecture().getSection().getCourse().getCourseName());
            }
        }

        if (comment.getCommentReplies() != null && !comment.getCommentReplies().isEmpty()) {
            dto.setReplies(comment.getCommentReplies().stream()
                    .map(this::convertToCommentReplyDTO)
                    .collect(Collectors.toList()));
        }

        return dto;
    }

    private CommentReplyDTO convertToCommentReplyDTO(CommentReply reply) {
        CommentReplyDTO dto = new CommentReplyDTO();
        dto.setId(reply.getId());
        dto.setReply(reply.getReply());
        dto.setDate(reply.getDate());
        if (reply.getUser() != null) {
            dto.setUserName(reply.getUser().getUserName());
            dto.setUserFirstName(reply.getUser().getFirstName());
            dto.setUserLastName(reply.getUser().getLastName());
        }
        return dto;
    }
}