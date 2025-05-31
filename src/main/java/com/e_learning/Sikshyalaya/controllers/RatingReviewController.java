package com.e_learning.Sikshyalaya.controllers;

import com.e_learning.Sikshyalaya.dtos.RatingReviewRequest;
import com.e_learning.Sikshyalaya.dtos.RatingReviewResponse;
import com.e_learning.Sikshyalaya.service.RatingReviewService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/rating-reviews")
public class RatingReviewController {
    private final RatingReviewService ratingReviewService;

    public RatingReviewController(RatingReviewService ratingReviewService) {
        this.ratingReviewService = ratingReviewService;
    }

    @PostMapping
    public ResponseEntity<RatingReviewResponse> addOrUpdateRatingReview(
            @RequestBody RatingReviewRequest request) throws Exception {
        return ResponseEntity.ok(ratingReviewService.addOrUpdateRatingReview(request));
    }

    @GetMapping("/course/{courseId}")
    public ResponseEntity<List<RatingReviewResponse>> getReviewsForCourse(
            @PathVariable Integer courseId) {
        return ResponseEntity.ok(ratingReviewService.getReviewsForCourse(courseId));
    }

    @GetMapping("/has-rated/{userName}/{courseId}")
    public ResponseEntity<Boolean> hasUserRatedCourse(
            @PathVariable String userName,
            @PathVariable Integer courseId) {
        return ResponseEntity.ok(ratingReviewService.hasUserRatedCourse(userName, courseId));
    }


//    @GetMapping
//    public  ResponseEntity<List<RatingReviewResponse>> getAllRatingForEachCourse(){
//
//    }
}