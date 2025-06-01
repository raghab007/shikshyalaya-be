package com.e_learning.Sikshyalaya.dtos;

import com.e_learning.Sikshyalaya.entities.Course;

import com.e_learning.Sikshyalaya.enums.CourseDifficulty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ViewCourseDto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer courseID;
    private String courseName;
    private String courseDescription;
    private Integer coursePrice;
    private String imageUrl;
    private Integer courseDuration;
    private Integer totalEnrollments;
    private String courseCategory;
    private String instructorName;
    private CourseDifficulty courseDifficulty;
    private Double rating;
    private Integer totalRatings;
    private Boolean isEnrolled;

    public ViewCourseDto(Course course) {
        this.courseID = course.getCourseID();
        this.courseName = course.getCourseName();
        this.courseDescription = course.getCourseDescription();
        this.instructorName = course.getInstructor().getFirstName() + course.getInstructor().getLastName();
        this.courseCategory = course.getCategory() != null ? course.getCategory().getCourseCategoryName()
                : "Uncategorized";
        this.imageUrl = course.getImageUrl();
        this.coursePrice = course.getCoursePrice();
        this.totalEnrollments = course.getEnrollments().size();
        this.courseDifficulty = course.getCourseDifficulty();
        this.totalRatings = course.getRatingReviews().size();
        this.rating = course.getRatingReviews().isEmpty() ? 0.0
                : course.getRatingReviews().stream()
                        .mapToInt(review -> review.getRating())
                        .average()
                        .orElse(0.0);
        this.isEnrolled = false;
    }

    public void setIsEnrolled(Boolean isEnrolled) {
        this.isEnrolled = isEnrolled;
    }

    public Boolean getIsEnrolled() {
        return isEnrolled;
    }

}
