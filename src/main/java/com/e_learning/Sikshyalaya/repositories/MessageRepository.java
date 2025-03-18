package com.e_learning.Sikshyalaya.repositories;

import com.e_learning.Sikshyalaya.entities.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MessageRepository  extends JpaRepository<Message, Integer> {
}
