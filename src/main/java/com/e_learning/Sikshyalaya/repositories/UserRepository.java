package com.e_learning.Sikshyalaya.repositories;

import com.e_learning.Sikshyalaya.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
    Optional<User> findByUserName(String username); // Use Optional to handle missing users
}
