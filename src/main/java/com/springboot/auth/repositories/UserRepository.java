package com.springboot.auth.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.springboot.auth.dto.UserDto;
import com.springboot.auth.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {

  User findByUsername(String username);

  User save(UserDto userDto);
}