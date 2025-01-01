package com.e_learning.Sikshyalaya.repositories;

import com.e_learning.Sikshyalaya.entities.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Integer> {
}
