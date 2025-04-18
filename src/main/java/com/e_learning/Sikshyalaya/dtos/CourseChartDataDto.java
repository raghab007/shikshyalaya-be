
package com.e_learning.Sikshyalaya.dtos;

public class CourseChartDataDto {
    private String courseName;
    private Integer students;
    private Integer coursePrice;
    private Integer totalRevenue;

    // Constructors, getters, and setters
    public CourseChartDataDto() {
    }

    public CourseChartDataDto(String courseName, Integer students, Integer coursePrice) {
        this.courseName = courseName;
        this.students = students;
        this.coursePrice = coursePrice;
        this.totalRevenue = coursePrice * students;
    }

    // Getters and Setters
    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public Integer getStudents() {
        return students;
    }

    public void setStudents(Integer students) {
        this.students = students;
    }

    public Integer getCoursePrice() {
        return coursePrice;
    }

    public void setCoursePrice(Integer coursePrice) {
        this.coursePrice = coursePrice;
    }

    public Integer getTotalRevenue() {
        return totalRevenue;
    }

    public void setTotalRevenue(Integer totalRevenue) {
        this.totalRevenue = totalRevenue;
    }
}