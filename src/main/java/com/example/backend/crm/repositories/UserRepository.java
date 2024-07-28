package com.example.backend.crm.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.backend.crm.models.entities.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long>{
    
    boolean existsByUsername(String username);

    boolean existsByEmail(String email);

    User findByEmail(String email);

    Optional<User> findByUsername(String username);
}
