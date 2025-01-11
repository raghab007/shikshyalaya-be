package com.e_learning.Sikshyalaya.interfaces;

import com.e_learning.Sikshyalaya.dtos.RequestUser;
import com.e_learning.Sikshyalaya.entities.User;


import java.util.List;
import java.util.Optional;

public interface IUserService {

      void saveUser(User user);

     Optional<User> getByUserName(String userName) ;

     List<User> findAll() ;

     void deleteUserByUserName(String userName) ;

     String verify(RequestUser user) ;
}


