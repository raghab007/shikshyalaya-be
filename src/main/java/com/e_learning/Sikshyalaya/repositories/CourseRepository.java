package com.e_learning.Sikshyalaya.repositories;

import com.e_learning.Sikshyalaya.entities.Course;
import com.e_learning.Sikshyalaya.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CourseRepository extends JpaRepository<Course,Integer> {

//    @Query("SELECT * from _course where course_id = :categoryId")
//    List<Course> findByCategory(@Param("categoryId") Integer categoryId);

    // Fetch paginated courses using native query
    @Query(value = "SELECT * FROM course ORDER BY courseid LIMIT :limit OFFSET :offset", nativeQuery = true)
    List<Course> findPaginatedCourses(@Param("offset") int offset, @Param("limit") int limit);

    // Count total courses
    @Query(value = "SELECT COUNT(*) FROM course", nativeQuery = true)
    int countTotalCourses();

    @Query(value = "SELECT * FROM course WHERE " +
            "LOWER(course_name) LIKE LOWER(CONCAT('%', :query, '%')) " +
            "ORDER BY courseid LIMIT :limit OFFSET :offset", nativeQuery = true)
    List<Course> searchCoursesByNamePaginated(
            @Param("query") String query,
            @Param("offset") int offset,
            @Param("limit") int limit);

    // Count search results by name only
    @Query(value = "SELECT COUNT(*) FROM course WHERE " +
            "LOWER(course_name) LIKE LOWER(CONCAT('%', :query, '%'))",
            nativeQuery = true)
    int countSearchResultsByName(@Param("query") String query);

    List<Course> findByInstructor(User instructor);

}
