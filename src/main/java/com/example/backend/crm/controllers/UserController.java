package com.example.backend.crm.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.backend.crm.models.dto.UserDto;
import com.example.backend.crm.models.entities.Role;
import com.example.backend.crm.models.entities.User;
import com.example.backend.crm.models.payload.MessageResponse;
import com.example.backend.crm.services.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/users")
@CrossOrigin(originPatterns = "*")
public class UserController {

    @Autowired
    private UserService service;

    @GetMapping
    public List<UserDto> list() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> show(@PathVariable Long id) {
        Optional<UserDto> userOptionl = service.findById(id);

        if (userOptionl.isPresent()) {
            return ResponseEntity.ok(userOptionl.orElseThrow());
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody User user, BindingResult result) {

        if (result.hasErrors()) {
            return validation(result);
        }

        if (service.existsByUsername(user.getUsername())) {
            return new ResponseEntity<>(MessageResponse.builder()
                    .message("El usuario " + user.getUsername() + " ya se encuentra registrado")
                    .object(null)
                    .build(), HttpStatus.CONFLICT);

        } else if (service.existsByEmail(user.getEmail())) {
            return new ResponseEntity<>(MessageResponse.builder()
                    .message("El email " + user.getEmail() + " ya se encuentra registrado")
                    .object(null)
                    .build(), HttpStatus.CONFLICT);
        } else {

            service.save(user);

            return new ResponseEntity<>(MessageResponse.builder()
                    .message("Usuario creado correctamente")
                    .object(user)
                    .build(), HttpStatus.ACCEPTED);
        }

    }

    @PostMapping("/admin")
    public ResponseEntity<?> createAdmin(@Valid @RequestBody User user, BindingResult result) {

        if (result.hasErrors()) {
            return validation(result);
        }

        if (service.existsByUsername(user.getUsername())) {
            return new ResponseEntity<>(MessageResponse.builder()
                    .message("El usuario " + user.getUsername() + " ya se encuentra registrado")
                    .object(null)
                    .build(), HttpStatus.CONFLICT);

        } else if (service.existsByEmail(user.getEmail())) {
            return new ResponseEntity<>(MessageResponse.builder()
                    .message("El email " + user.getEmail() + " ya se encuentra registrado")
                    .object(null)
                    .build(), HttpStatus.CONFLICT);
        } else {

            service.saveAdmin(user);

            return new ResponseEntity<>(MessageResponse.builder()
                    .message("Usuario creado correctamente")
                    .object(user)
                    .build(), HttpStatus.ACCEPTED);
        }

    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@Valid @RequestBody User user, BindingResult result, @PathVariable Long id) {
        Optional<UserDto> o = service.update(user, id);

        if (result.hasErrors()) {
            return validation(result);
        }

        if (o.isPresent()) {
            return new ResponseEntity<>(MessageResponse.builder()
                    .message("Cliente actualizado")
                    .object(o)
                    .build(), HttpStatus.CREATED);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> remove(@PathVariable Long id) {
        Optional<UserDto> o = service.findById(id);

        try {
            if (o.isPresent()) {
                service.remove(id);
            }
            return new ResponseEntity<>(MessageResponse.builder()
                    .message("Usuario eliminado")
                    .object(null)
                    .build(), HttpStatus.NO_CONTENT);

        } catch (Exception e) {

            return new ResponseEntity<>(MessageResponse.builder()
                    .message(e.getMessage())
                    .object(null)
                    .build(), HttpStatus.METHOD_NOT_ALLOWED);
        }

    }

    @GetMapping("/verifyEmail")
    public ResponseEntity<?> verifyEmail(@RequestParam String email) {

        Role adminRole = new Role(1L, "ROLE_ADMIN");

        if (service.existsByEmail(email)) {

            User usuarioEntontrado = service.findByEmail(email);

            if (!usuarioEntontrado.getRoles().contains(adminRole)){

                return new ResponseEntity<>(MessageResponse.builder()
                        .message("Correo encontrado")
                        .object(usuarioEntontrado)
                        .build(), HttpStatus.ACCEPTED);
            } else {

                return new ResponseEntity<>(MessageResponse.builder()
                    .message("El Correo ingresado corresponde a un Administrador")
                    .object(null)
                    .build(), HttpStatus.ACCEPTED);

            }

        } else {
            return new ResponseEntity<>(MessageResponse.builder()
                    .message("Correo no encontrado")
                    .object(null)
                    .build(), HttpStatus.ACCEPTED);
        }
    }

    @PatchMapping("/updatePassword/{id}")
    public ResponseEntity<?> updatePassword(@PathVariable Long id, @RequestBody User user) {
        Optional<User> o = service.getById(id);

        if (o.isPresent()) {
            User userFound = o.get();

            userFound.setPassword(user.getPassword());

            service.save(userFound);

            return new ResponseEntity<>(MessageResponse.builder()
                    .message("Contraseña actualizada")
                    .object(null)
                    .build(), HttpStatus.OK);

        } else {
            return ResponseEntity.notFound().build();
        }
    }

    private ResponseEntity<?> validation(BindingResult result) {

        Map<String, String> errors = new HashMap<>();

        result.getFieldErrors().forEach(err -> {
            errors.put(err.getField(), "El campo " + err.getField() + " " + err.getDefaultMessage());
        });

        return ResponseEntity.badRequest().body(errors);

    }
}
