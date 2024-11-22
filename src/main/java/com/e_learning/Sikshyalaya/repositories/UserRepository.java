package com.e_learning.Sikshyalaya.repositories;

import com.e_learning.Sikshyalaya.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository  extends JpaRepository<User, String> {
}
