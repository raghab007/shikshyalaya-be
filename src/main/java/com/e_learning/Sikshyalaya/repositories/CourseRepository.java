package com.e_learning.Sikshyalaya.repositories;

import com.e_learning.Sikshyalaya.entities.Course;
import com.e_learning.Sikshyalaya.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CourseRepository extends JpaRepository<Course, Integer> {

        // @Query("SELECT * from _course where course_id = :categoryId")
        // List<Course> findByCategory(@Param("categoryId") Integer categoryId);

        // Fetch paginated courses using native query
        @Query(value = "SELECT * FROM course ORDER BY courseid LIMIT :limit OFFSET :offset", nativeQuery = true)
        List<Course> findPaginatedCourses(@Param("offset") int offset, @Param("limit") int limit);

        @Query(value = "SELECT * FROM course WHERE category_id = :categoryId ORDER BY courseid LIMIT :limit OFFSET :offset", nativeQuery = true)
        List<Course> findPaginatedCoursesByCategory(@Param("categoryId") Integer categoryId,
                        @Param("offset") int offset, @Param("limit") int limit);

        @Query(value = "SELECT COUNT(*) FROM course WHERE category_id = :categoryId", nativeQuery = true)
        int countByCategoryId(@Param("categoryId") Integer categoryId);

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
                        "LOWER(course_name) LIKE LOWER(CONCAT('%', :query, '%'))", nativeQuery = true)
        int countSearchResultsByName(@Param("query") String query);

        List<Course> findByInstructor(User instructor);

        @Query(value = "SELECT * FROM course WHERE course_price BETWEEN :min AND :max ORDER BY courseid LIMIT :limit OFFSET :offset", nativeQuery = true)
        List<Course> findByCoursePriceBetween(@Param("min") Double min, @Param("max") Double max,
                        @Param("offset") int offset, @Param("limit") int limit);

        @Query(value = "SELECT * FROM course WHERE course_price >= :min ORDER BY courseid LIMIT :limit OFFSET :offset", nativeQuery = true)
        List<Course> findByCoursePriceGreaterThanEqual(@Param("min") Double min, @Param("offset") int offset,
                        @Param("limit") int limit);

        @Query(value = "SELECT * FROM course WHERE course_price <= :max ORDER BY courseid LIMIT :limit OFFSET :offset", nativeQuery = true)
        List<Course> findByCoursePriceLessThanEqual(@Param("max") Double max, @Param("offset") int offset,
                        @Param("limit") int limit);

        @Query(value = "SELECT COUNT(*) FROM course WHERE course_price BETWEEN :min AND :max", nativeQuery = true)
        int countByCoursePriceBetween(@Param("min") Double min, @Param("max") Double max);

        @Query(value = "SELECT COUNT(*) FROM course WHERE course_price >= :min", nativeQuery = true)
        int countByCoursePriceGreaterThanEqual(@Param("min") Double min);

        @Query(value = "SELECT COUNT(*) FROM course WHERE course_price <= :max", nativeQuery = true)
        int countByCoursePriceLessThanEqual(@Param("max") Double max);

        @Query(value = "SELECT * FROM course WHERE course_difficulty = :difficulty ORDER BY courseid LIMIT :limit OFFSET :offset", nativeQuery = true)
        List<Course> findByCourseDifficulty(@Param("difficulty") String difficulty, @Param("offset") int offset,
                        @Param("limit") int limit);

        @Query(value = "SELECT COUNT(*) FROM course WHERE course_difficulty = :difficulty", nativeQuery = true)
        int countByCourseDifficulty(@Param("difficulty") String difficulty);

        @Query(value = "SELECT * FROM course WHERE LOWER(course_name) LIKE LOWER(CONCAT('%', :search, '%'))", nativeQuery = true)
        Page<Course> findByCourseNameContaining(@Param("search") String search, Pageable pageable);
}
