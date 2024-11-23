package com.e_learning.Sikshyalaya.repositories;

import com.e_learning.Sikshyalaya.entities.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CourseRepository extends JpaRepository<Course,Integer> {
}
