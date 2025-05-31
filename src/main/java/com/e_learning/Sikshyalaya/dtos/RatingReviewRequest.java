package com.e_learning.Sikshyalaya.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RatingReviewRequest {
    private Integer rating;
    private String review;
    private Integer courseId;
}



