package com.e_learning.Sikshyalaya.repositories;

import com.e_learning.Sikshyalaya.entities.Resource;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ResourceRepository extends JpaRepository<Resource, Integer> {
}
