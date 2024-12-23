package com.e_learning.Sikshyalaya.service;

import com.e_learning.Sikshyalaya.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

// Circular dependency was removed by using user repo instead of user service! But I don't know why
@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<com.e_learning.Sikshyalaya.entities.User> user = userRepository.findByUserName(username);
        if (user.isPresent()) {
            com.e_learning.Sikshyalaya.entities.User user1 = user.get();
            return User.builder().username(user1.getUserName()).password(user1.getPassword()).build();
        }else {
            throw new UsernameNotFoundException("Username not found");
        }
    }
}
