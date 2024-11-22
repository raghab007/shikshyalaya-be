package com.e_learning.Sikshyalaya.service;

import com.e_learning.Sikshyalaya.entities.User;
import com.e_learning.Sikshyalaya.enums.Role;
import com.e_learning.Sikshyalaya.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.beans.Encoder;

@Service
public class UserService {

    UserRepository userRepository;
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public  void saveUser(User user) {
        user.setRole("USER");
        userRepository.save(user);
    }
}
