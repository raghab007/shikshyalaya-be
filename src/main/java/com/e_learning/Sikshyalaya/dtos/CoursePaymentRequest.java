package com.e_learning.Sikshyalaya.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CoursePaymentRequest {
    private  Integer courseId;

    private  Integer amount;
}
