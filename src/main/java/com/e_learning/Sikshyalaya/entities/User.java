package com.e_learning.Sikshyalaya.entities;
import com.e_learning.Sikshyalaya.enums.Role;
import com.fasterxml.jackson.annotation.JsonBackReference;
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
    private List<Enrollment> enrollments = new ArrayList<>();

    @OneToMany
    private List<Comment> comments = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private List<VideoFeedback> videoFeedbacks = new ArrayList<>();

    @OneToMany(mappedBy = "instructor")
    private List<Course> courses;
}
