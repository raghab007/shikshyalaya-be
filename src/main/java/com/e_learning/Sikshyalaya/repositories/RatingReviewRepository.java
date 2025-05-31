package com.e_learning.Sikshyalaya.repositories;

import com.e_learning.Sikshyalaya.entities.RatingReview;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RatingReviewRepository extends JpaRepository<RatingReview, Long> {
    Optional<RatingReview> findByUser_UserNameAndCourse_CourseID(String userName, Integer courseId);

    List<RatingReview> findByCourse_CourseID(Integer courseId);

    boolean existsByUser_UserNameAndCourse_CourseID(String userName, Integer courseId);

    // New methods for admin functionality
    Page<RatingReview> findByCourse_CourseID(Long courseId, Pageable pageable);

    void deleteById(Long reviewId);
}