package com.e_learning.Sikshyalaya.repositories;

import com.e_learning.Sikshyalaya.entities.Payment;
import com.e_learning.Sikshyalaya.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Integer> {
    List<Payment> findByUserOrderByDateTimeDesc(User user);
}
