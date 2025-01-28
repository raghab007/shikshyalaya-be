package com.e_learning.Sikshyalaya.repositories;

import com.e_learning.Sikshyalaya.entities.CourseCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<CourseCategory, Integer> {
}
