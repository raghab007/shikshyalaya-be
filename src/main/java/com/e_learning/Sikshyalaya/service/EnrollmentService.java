package com.e_learning.Sikshyalaya.service;

import com.e_learning.Sikshyalaya.repositories.EnrollmentRepository;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class EnrollmentService {

    private final EnrollmentRepository enrollmentRepository;

    public EnrollmentService(EnrollmentRepository enrollmentRepository) {
        this.enrollmentRepository = enrollmentRepository;
    }

    public boolean isUserEnrolled(Integer courseId, String username) {
        return enrollmentRepository.findAll().stream()
                .anyMatch(e -> Objects.equals(e.getUser().getUserName(), username)
                        && Objects.equals(e.getCourse().getCourseID(), courseId));
    }
}
