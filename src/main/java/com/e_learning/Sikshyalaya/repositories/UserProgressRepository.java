package com.e_learning.Sikshyalaya.repositories;

import com.e_learning.Sikshyalaya.entities.UserProgress;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserProgressRepository extends JpaRepository<UserProgress, Integer> {
}
