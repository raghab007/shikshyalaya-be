package com.e_learning.Sikshyalaya.dtos;

import com.e_learning.Sikshyalaya.entities.Course;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CourseResponseDto {
    private  Integer courseId;
    private String name;
    private String description;
    private String image;

    public  CourseResponseDto(Course course){
        name = course.getCourseName();
        description = course.getCourseDescription();
        image = course.getImageUrl();
        courseId = course.getCourseID();

    }
    private  Integer totalLectures;

    private  Integer totalFinished;

    private  double percentageFinished;
}
