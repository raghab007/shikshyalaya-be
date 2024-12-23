package com.e_learning.Sikshyalaya.repositories;

import com.e_learning.Sikshyalaya.entities.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CourseRepository extends JpaRepository<Course,Integer> {

//    @Query("SELECT * from _course where course_id = :categoryId")
//    List<Course> findByCategory(@Param("categoryId") Integer categoryId);
}
