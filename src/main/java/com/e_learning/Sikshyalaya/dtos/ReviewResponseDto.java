package com.e_learning.Sikshyalaya.dtos;


import com.e_learning.Sikshyalaya.entities.RatingReview;

public class ReviewResponseDto {
    private Long id;
    private String userName;
    private String userEmail;
    private int rating;
    private String courseName;

    public ReviewResponseDto(RatingReview review) {
        this.id = review.getId();
        this.userName = review.getUser().getUserName();
        this.userEmail = review.getUser().getEmail();
        this.rating = review.getRating();
        this.courseName = review.getCourse().getCourseName();
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }


    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }
}