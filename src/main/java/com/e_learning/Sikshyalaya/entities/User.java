package com.e_learning.Sikshyalaya.entities;

import com.e_learning.Sikshyalaya.dtos.RequestUserDto;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Table(name = "_User")
public class User {
    @Id
    private String userName;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String role;
    private String contactNumber;
    private boolean isBlocked;

    @OneToMany(mappedBy = "user")
    @JsonManagedReference
    private List<Enrollment> enrollments = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    @JsonManagedReference
    private List<VideoFeedback> videoFeedbacks = new ArrayList<>();

    @OneToMany(mappedBy = "instructor")
    private List<Course> courses;

    @OneToMany(mappedBy = "user")
    @JsonManagedReference
    private List<Message> messages = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    @JsonIgnore
    @JsonManagedReference
    private List<Comment> comments = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    @JsonIgnore
    private List<UserProgress> userProgresses = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    @JsonIgnore
    private List<Payment> payments = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    @JsonManagedReference
    private List<CommentReply> commentReplies = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<RatingReview> ratingReviews = new ArrayList<>();

    @Column(name = "profile_image")
    private String profileImage;

    public User(RequestUserDto requestUserDto) {

        firstName = requestUserDto.getFirstName();
        lastName = requestUserDto.getLastName();
        password = requestUserDto.getPassword();
        role = requestUserDto.getRole();
        userName = requestUserDto.getUserName();
        contactNumber = requestUserDto.getContactNumber();
        email = requestUserDto.getEmail();

    }
}
