package com.e_learning.Sikshyalaya.interfaces;

import com.e_learning.Sikshyalaya.dtos.LoginResponse;
import com.e_learning.Sikshyalaya.dtos.RequestUser;
import com.e_learning.Sikshyalaya.entities.User;

import java.util.List;
import java.util.Optional;

public interface IUserService {

      void saveUser(User user);

      User updateUser(User user);

      Optional<User> getByUserName(String userName);

      List<User> findAll();

      void deleteUserByUserName(String userName);

      LoginResponse verify(RequestUser user);

      User updateProfileImage(String username, String imageFileName);

      boolean verifyPassword(User user, String currentPassword);
}
