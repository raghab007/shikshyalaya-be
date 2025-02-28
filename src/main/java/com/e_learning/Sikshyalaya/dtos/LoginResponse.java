package com.e_learning.Sikshyalaya.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class LoginResponse {
    private  String token;

    private  String ROLE;
}
