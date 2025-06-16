package com.springboot.auth.services;

import com.springboot.auth.dto.UserDto;
import com.springboot.auth.entity.User;

public interface UserService {

  User findByUsername(String username);

  User save(UserDto userDto);
}