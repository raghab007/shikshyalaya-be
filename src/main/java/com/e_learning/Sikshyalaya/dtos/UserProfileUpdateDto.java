package com.e_learning.Sikshyalaya.dtos;

import lombok.Data;

@Data
public class UserProfileUpdateDto {
    private String firstName;
    private String lastName;
    private String email;
    private String contactNumber;
}