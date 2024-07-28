package com.example.backend.crm.services;

import java.util.List;
import java.util.Optional;

import com.example.backend.crm.models.dto.UserDto;
import com.example.backend.crm.models.entities.User;

public interface UserService {
    List<UserDto> findAll();

    Optional<UserDto> findById(Long id);
    
    Optional<User> getById(Long id);

    UserDto save(User user);
    
    UserDto saveAdmin(User user);
    
    Optional<UserDto> update(User user, Long id);

    void remove(Long id);

    boolean existsByUsername(String username);

    boolean existsByEmail(String email);

    User findByEmail(String email);

}
