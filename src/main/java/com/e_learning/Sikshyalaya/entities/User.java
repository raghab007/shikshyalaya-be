package com.e_learning.Sikshyalaya.entities;
import com.e_learning.Sikshyalaya.dtos.RequestUserDto;
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

    @OneToMany(mappedBy = "user")
    @JsonManagedReference
    private List<Enrollment> enrollments = new ArrayList<>();

    @OneToMany
    private List<Comment> comments = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private List<VideoFeedback> videoFeedbacks = new ArrayList<>();

    @OneToMany(mappedBy = "instructor")
    @JsonManagedReference
    private List<Course> courses;

    @OneToMany(mappedBy = "user")
    @JsonManagedReference
    private  List<Message> messages = new ArrayList<>();
    public User (RequestUserDto requestUserDto){

        firstName = requestUserDto.getFirstName();
        lastName = requestUserDto.getLastName();
        password = requestUserDto.getPassword();
        role = requestUserDto.getRole();
        userName = requestUserDto.getUserName();
        contactNumber = requestUserDto.getContactNumber();
        email = requestUserDto.getEmail();

    }
}
