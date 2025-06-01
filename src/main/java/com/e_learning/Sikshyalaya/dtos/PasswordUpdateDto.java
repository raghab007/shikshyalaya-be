package com.e_learning.Sikshyalaya.dtos;

import lombok.Data;

@Data
public class PasswordUpdateDto {
    private String currentPassword;
    private String newPassword;
}