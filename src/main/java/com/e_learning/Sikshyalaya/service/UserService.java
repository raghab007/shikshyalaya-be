package com.e_learning.Sikshyalaya.service;
import com.e_learning.Sikshyalaya.entities.User;
import com.e_learning.Sikshyalaya.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
  private   AuthenticationManager authenticationManager;

  private   PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

   private UserRepository userRepository;
    public UserService(UserRepository userRepository) {

        this.userRepository = userRepository;
    }

    public  void saveUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole("USER");
        userRepository.save(user);
    }

    public  User getByUserName(String userName) {
       return userRepository.findByUserName(userName);
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public void deleteUserByUserName(String userName) {
        userRepository.deleteById(userName);
    }

    public String verify(User user) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUserName(),user.getUserName()));
        if (authentication.isAuthenticated()){
           return  "Login success";
       }
        return "Login failed";
    }
}
