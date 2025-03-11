package com.e_learning.Sikshyalaya.repositories;

import com.e_learning.Sikshyalaya.entities.Lecture;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LectureRepository extends JpaRepository<Lecture, Integer> {
}
