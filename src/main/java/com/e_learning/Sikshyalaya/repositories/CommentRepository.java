package com.e_learning.Sikshyalaya.repositories;

import com.e_learning.Sikshyalaya.entities.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Integer> {
    // Find all comments for courses taught by a specific instructor
    @Query("SELECT c FROM Comment c JOIN c.lecture l JOIN l.section s JOIN s.course co WHERE co.instructor.userName = :instructorUsername")
    List<Comment> findAllByInstructor(@Param("instructorUsername") String instructorUsername);

    // Find comments filtered by course name for a specific instructor
    @Query("SELECT c FROM Comment c JOIN c.lecture l JOIN l.section s JOIN s.course co " +
            "WHERE co.instructor.userName = :instructorUsername AND co.courseName = :courseName")
    List<Comment> findByInstructorAndCourseName(@Param("instructorUsername") String instructorUsername,
                                                @Param("courseName") String courseName);
}
