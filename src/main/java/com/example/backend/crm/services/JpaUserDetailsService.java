package com.example.backend.crm.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.backend.crm.repositories.UserRepository;

/*
 * La anotacion @Service indica que la clases se 
 * gestionará como un componente que puede ser 
 * inyectado en otras partes de la aplicación.
 * 
 * La interfaz UserDetailsService define un método para
 * cargar los detalles del usuario basado en el nombre 
 * de usuario. 
 * 
 * La clase crea un objeto org.springframework.security.core.userdetails.User
 * para ser analizado en el primer filtro JwtAuthenticationFilter
 */
@Service
public class JpaUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    // Método que carga los detalles del usuario a 
    //  partir del nombre de usuario proporcionado
    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Optional<com.example.backend.crm.models.entities.User> o
                = userRepository.findByUsername(username);

        // Verifica si el nombre de usuario existe en base de datos
        if (!o.isPresent()) {
            // Lanza una excepción si el nombre de usuario ha sido creado
            throw new UsernameNotFoundException(String.format("Username %s no existe en el sistema!", username));
        }

        com.example.backend.crm.models.entities.User user = o.orElseThrow();

        /*
         * GrantedAuthority representa una autoridad otorgada
         * al usuario (por ejemplo, un rol).
         */
        // Lista de autoridades (roles) otorgadas al usuario
        List<GrantedAuthority> authorities = user.getRoles()
                .stream()
                .map(r -> new SimpleGrantedAuthority(r.getName()))
                .collect(Collectors.toList());

        // Añade el rol "ROLE_USER" a la lista de autoridades
        authorities.add(new SimpleGrantedAuthority("ROLE_USER"));

        // Crea y devuelve un objeto User con los detalles del usuario
        return new User(
                user.getUsername(),
                user.getPassword(),
                true, // Cuenta habilitada
                true, // Cuenta no expirada
                true, // Credenciales no expiradas
                true, // Cuenta no bloqueada
                authorities); // Lista de roles del usuario
    }

}
