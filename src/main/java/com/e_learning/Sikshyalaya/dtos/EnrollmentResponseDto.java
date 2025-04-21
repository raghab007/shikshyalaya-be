package com.e_learning.Sikshyalaya.dtos;


import com.e_learning.Sikshyalaya.entities.Course;
import com.e_learning.Sikshyalaya.entities.Enrollment;
import com.e_learning.Sikshyalaya.entities.User;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class EnrollmentResponseDto {

    private Integer enrollmentId;
    private Course course;

    private User user;
    private LocalDateTime enrollmentDate;

    public EnrollmentResponseDto(Enrollment enrollment){
        this.enrollmentId = enrollment.getEnrollmentId();
        this.course = enrollment.getCourse();
        this.user = enrollment.getUser();
        enrollmentDate = enrollment.getEnrollmentDate();
    }
}
