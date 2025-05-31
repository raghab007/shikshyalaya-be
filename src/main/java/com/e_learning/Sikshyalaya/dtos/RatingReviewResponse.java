package com.e_learning.Sikshyalaya.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RatingReviewResponse {
    private Long id;
    private Integer rating;
    private String review;
    private String userName;
    private String userEmail;
    private Integer courseId;
    private String userFullName;
    private LocalDateTime createdAt;
}
