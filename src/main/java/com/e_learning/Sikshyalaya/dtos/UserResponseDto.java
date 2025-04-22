package com.e_learning.Sikshyalaya.dtos;

import com.e_learning.Sikshyalaya.entities.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserResponseDto {
    private String userName;
    private String firstName;
    private String lastName;
    private String email;
    private String contactNumber;
    private String role;
    private boolean isBlocked;

    public UserResponseDto(User user) {
        userName = user.getUserName();
        firstName = user.getFirstName();
        lastName = user.getLastName();
        email = user.getEmail();
        contactNumber = user.getContactNumber();
        role = user.getRole();
        this.isBlocked  = user.isBlocked();
    }
}
