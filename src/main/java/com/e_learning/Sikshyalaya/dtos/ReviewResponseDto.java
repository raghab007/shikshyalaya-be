package com.e_learning.Sikshyalaya.dtos;


import com.e_learning.Sikshyalaya.entities.RatingReview;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ReviewResponseDto {
    private Long id;
    private String userName;
    private String userEmail;
    private int rating;
    private String courseName;
    private Date   createdDate;

    public ReviewResponseDto(RatingReview review) {
        this.id = review.getId();
        this.userName = review.getUser().getUserName();
        this.userEmail = review.getUser().getEmail();
        this.rating = review.getRating();
        this.courseName = review.getCourse().getCourseName();
        this.createdDate = review.getCreatedAt();
    }

    // Getters and Setters

}