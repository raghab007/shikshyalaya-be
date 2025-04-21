package com.e_learning.Sikshyalaya.repositories;

import com.e_learning.Sikshyalaya.entities.Enrollment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EnrollmentRepository extends JpaRepository<Enrollment, Integer> {

        @Query("SELECT e FROM Enrollment e WHERE e.course.instructor.userName = :instructorUserName")
        List<Enrollment> findAllByInstructor(@Param("instructorUserName") String instructorUserName);
}

