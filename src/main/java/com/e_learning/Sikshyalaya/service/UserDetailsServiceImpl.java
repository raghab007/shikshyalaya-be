package com.e_learning.Sikshyalaya.service;

import com.e_learning.Sikshyalaya.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;


// Circular dependency was removed by using user repo instead of user service! But I don't know why
@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    public UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        com.e_learning.Sikshyalaya.entities.User user = userRepository.findByUserName(username);
        return User.builder().username(user.getUserName()).password(user.getPassword()).build();
    }
}
