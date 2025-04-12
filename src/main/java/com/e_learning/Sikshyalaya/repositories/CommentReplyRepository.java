package com.e_learning.Sikshyalaya.repositories;

import com.e_learning.Sikshyalaya.entities.CommentReply;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentReplyRepository  extends JpaRepository<CommentReply, Integer> {

}
