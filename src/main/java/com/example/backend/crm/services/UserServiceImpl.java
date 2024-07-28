package com.example.backend.crm.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.backend.crm.models.dto.UserDto;
import com.example.backend.crm.models.dto.mapper.DtoMapperUser;
import com.example.backend.crm.models.entities.Role;
import com.example.backend.crm.models.entities.User;
import com.example.backend.crm.repositories.RoleRepository;
import com.example.backend.crm.repositories.UserRepository;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository repository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private RoleRepository roleRepository;

    @Override
    @Transactional(readOnly = true)
    public List<UserDto> findAll() {
        List<User> users = (List<User>) repository.findAll();

        Role adminRole = new Role(1L, "ROLE_ADMIN");

        return users
                .stream()
                .map(u -> DtoMapperUser
                .builder()
                .setUser(u)
                .setAdmin(u.getRoles().contains(adminRole))
                .build()
                ).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<UserDto> findById(Long id) {
        return repository.findById(id).
                map(u -> DtoMapperUser
                .builder()
                .setUser(u)
                .build());
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<User> getById(Long id) {
        Optional<User> o = repository.findById(id);
        if (o.isPresent()) {
            return o;
        }
        return Optional.empty();
    }

    @Override
    @Transactional
    public UserDto save(User user) {
        user.setPassword(
                passwordEncoder
                        .encode(user.getPassword())
        );
        List<Role> roles = new ArrayList<>();
        Optional<Role> o = roleRepository
                .findByName("ROLE_USER");
        if (o.isPresent()) {
            roles.add(o.orElseThrow());
        }
        user.setRoles(roles);
        return DtoMapperUser
                .builder()
                .setUser(repository.save(user))
                .build();
    }

    @Override
    @Transactional
    public UserDto saveAdmin(User user) {
        user.setPassword(
                passwordEncoder
                        .encode(user.getPassword())
        );
        List<Role> roles = new ArrayList<>();
        Optional<Role> o = roleRepository
                .findByName("ROLE_ADMIN");
        if (o.isPresent()) {
            roles.add(o.orElseThrow());
        }
        user.setRoles(roles);
        return DtoMapperUser
                .builder()
                .setUser(repository.save(user))
                .build();
    }

    @Override
    @Transactional
    public Optional<UserDto> update(User user, Long id) {
        Optional<User> o = repository.findById(id);
        User userOptional = null;
        if (o.isPresent()) {
            User userDb = o.orElseThrow();
            userDb.setUsername(user.getUsername());
            userDb.setEmail(user.getEmail());
            userDb.setPassword(
                    passwordEncoder
                            .encode(user.getPassword())
            );
            userOptional = repository.save(userDb);
        }
        return Optional
                .ofNullable(
                        DtoMapperUser
                                .builder()
                                .setUser(userOptional)
                                .build()
                );
    }

    @Override
    @Transactional
    public void remove(Long id) {
        repository.deleteById(id);
    }

    @Override
    public boolean existsByUsername(String username) {
        return repository.existsByUsername(username);
    }

    @Override
    public boolean existsByEmail(String email) {
        return repository.existsByEmail(email);
    }

    @Override
    public User findByEmail(String email) {
        return repository.findByEmail(email);
    }
}
