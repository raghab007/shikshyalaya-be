package com.e_learning.Sikshyalaya.service;

import com.e_learning.Sikshyalaya.dtos.RatingReviewRequest;
import com.e_learning.Sikshyalaya.dtos.RatingReviewResponse;
import com.e_learning.Sikshyalaya.entities.Course;
import com.e_learning.Sikshyalaya.entities.RatingReview;
import com.e_learning.Sikshyalaya.entities.User;
import com.e_learning.Sikshyalaya.repositories.CourseRepository;
import com.e_learning.Sikshyalaya.repositories.RatingReviewRepository;
import com.e_learning.Sikshyalaya.repositories.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RatingReviewService {
    private final RatingReviewRepository ratingReviewRepository;
    private final UserRepository userRepository;
    private final CourseRepository courseRepository;

    public RatingReviewService(RatingReviewRepository ratingReviewRepository,
            UserRepository userRepository,
            CourseRepository courseRepository) {
        this.ratingReviewRepository = ratingReviewRepository;
        this.userRepository = userRepository;
        this.courseRepository = courseRepository;
    }

    public RatingReviewResponse addOrUpdateRatingReview(RatingReviewRequest request) throws Exception {
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findById(name)
                .orElseThrow(() -> new Exception("User not found"));

        Course course = courseRepository.findById(request.getCourseId())
                .orElseThrow(() -> new Exception("Course not found"));

        // Check if rating already exists
        RatingReview ratingReview = ratingReviewRepository.findByUser_UserNameAndCourse_CourseID(
                name, request.getCourseId())
                .orElse(new RatingReview());

        // Update or set values
        ratingReview.setUser(user);
        ratingReview.setCourse(course);
        ratingReview.setRating(request.getRating());
        ratingReview.setReview(request.getReview());

        RatingReview savedReview = ratingReviewRepository.save(ratingReview);

        return convertToResponse(savedReview);
    }

    public List<RatingReviewResponse> getReviewsForCourse(Integer courseId) {
        return ratingReviewRepository.findByCourse_CourseID(courseId)
                .stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }

    public boolean hasUserRatedCourse(String userName, Integer courseId) {
        return ratingReviewRepository.existsByUser_UserNameAndCourse_CourseID(userName, courseId);
    }

    // New methods for admin functionality
    public Page<RatingReviewResponse> getReviewsForCourseAdmin(Long courseId, Pageable pageable) {
        Page<RatingReview> reviews = ratingReviewRepository.findByCourse_CourseID(courseId, pageable);
        return reviews.map(this::convertToResponse);
    }

    public void deleteReview(Long reviewId) {
        ratingReviewRepository.deleteById(reviewId);
    }

    private RatingReviewResponse convertToResponse(RatingReview ratingReview) {
        return new RatingReviewResponse(
                ratingReview.getId(),
                ratingReview.getRating(),
                ratingReview.getReview(),
                ratingReview.getUser().getUserName(),
                ratingReview.getUser().getEmail(),
                ratingReview.getCourse().getCourseID(),
                ratingReview.getUser().getFirstName() + " " + ratingReview.getUser().getLastName(),
                ratingReview.getCreatedAt());
    }
}
