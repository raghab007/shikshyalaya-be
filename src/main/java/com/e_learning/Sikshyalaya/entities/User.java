package com.e_learning.Sikshyalaya.entities;
import com.e_learning.Sikshyalaya.enums.Role;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
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

    @OneToMany
    private List<Comment> comments = new ArrayList<>();

    @OneToMany
    private List<VideoFeedback> videoFeedbacks = new ArrayList<>();
}
