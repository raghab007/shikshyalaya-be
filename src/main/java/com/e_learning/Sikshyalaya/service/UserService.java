package com.e_learning.Sikshyalaya.service;
import com.e_learning.Sikshyalaya.dtos.LoginResponse;
import com.e_learning.Sikshyalaya.dtos.RequestUser;
import com.e_learning.Sikshyalaya.entities.User;
import com.e_learning.Sikshyalaya.interfaces.IUserService;
import com.e_learning.Sikshyalaya.repositories.UserRepository;
import lombok.extern.java.Log;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class UserService implements IUserService {
    @Autowired
    private   AuthenticationManager authenticationManager;
    @Autowired
    private JWTService jwtService;

    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

   private final UserRepository userRepository;
    public UserService(UserRepository userRepository) {

        this.userRepository = userRepository;
    }

    public  void saveUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
    }

    public Optional<User> getByUserName(String userName) {
       return userRepository.findByUserName(userName);
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public void deleteUserByUserName(String userName) {
        userRepository.deleteById(userName);
    }

    public LoginResponse verify(RequestUser user) {
        try {
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUserName(), user.getPassword()));
            if (authentication.isAuthenticated()) {
              String token  =  jwtService.generateToken(user.getUserName());
                Optional<User> byUserName = userRepository.findByUserName(user.getUserName());
                User user1 = byUserName.get();
                return new LoginResponse(token,user1.getRole(), "success");
            }
        }catch (Exception e){
            System.out.println("exception:"+e);
            return  new LoginResponse();
        }
      return  null;
    }

}
