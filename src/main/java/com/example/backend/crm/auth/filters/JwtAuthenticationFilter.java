package com.example.backend.crm.auth.filters;

import java.io.IOException;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static com.example.backend.crm.auth.TokenJwtConfig.HEADER_AUTHORIZATION;
import static com.example.backend.crm.auth.TokenJwtConfig.PREFIX_TOKEN;
import static com.example.backend.crm.auth.TokenJwtConfig.SECRECT_KEY;
import com.example.backend.crm.models.entities.User;
import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.jsonwebtoken.Jwts;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/*
 * La clase UsernamePasswordAuthenticationFilter gestiona la autenticación 
 * basada en usuario y contraseña.
 */
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    // Interface de Spring Security utilizada para manejar la autenticación de
    // usuarios
    private final AuthenticationManager authenticationManager;

    public JwtAuthenticationFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    /*
     * Método que intenta la autenticación al recibir una solicitud.
     * 
     * HttpServletRequest y HttpServletResponse representan la solicitud HTTP y
     * la respuesta HTTP, respectivamente
     */
    @Override
    public Authentication attemptAuthentication(
            HttpServletRequest request,
            HttpServletResponse response)
            throws AuthenticationException {

        User user = null;
        String username = null;
        String password = null;

        try {
            // Lee los datos del usuario desde el cuerpo de la solicitud
            user = new ObjectMapper().readValue(request.getInputStream(), User.class);
            username = user.getUsername(); // Obtiene el nombre de usuario
            password = user.getPassword(); // Obtiene la contraseña

            // Registra los datos del usuario para fines de depuración
            // logger.info("Username desde request InputStream (raw) " + username);
            // logger.info("Password desde request InputStream (raw) " + password);
        } catch (StreamReadException e) {
            e.printStackTrace(); // Maneja excepciones de lectura de stream
        } catch (DatabindException e) {
            e.printStackTrace(); // Maneja excepciones de enlace de datos
        } catch (IOException e) {
            e.printStackTrace(); // Maneja excepciones de E/S
        }

        /*
         * La clase UsernamePasswordAuthenticationToken permite crear un token
         * de autenticación con el nombre de usuario y la contraseña.
         */
        UsernamePasswordAuthenticationToken authToken
                = new UsernamePasswordAuthenticationToken(username, password);

        // Autentica el token utilizando el AuthenticationManager
        return authenticationManager.authenticate(authToken);
    }

    // Método llamado cuando la autenticación es exitosa
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
            Authentication authResult) throws IOException, ServletException {

        // Obtiene el nombre de usuario del objeto de autenticación resultante
        String username = ((org.springframework.security.core.userdetails.User) authResult.getPrincipal()).getUsername();

        /*
         * Extracción y asignación de roles, 
         * se determina si el usuario es ADMIN o no.
         */
        Collection<? extends GrantedAuthority> roles = authResult.getAuthorities();
        boolean isAdmin = roles.stream().
                anyMatch(r -> r.getAuthority().equals("ROLE_ADMIN"));
        //DefaultClaims claims = new DefaultClaims();
        Map<String, Object> claims = new HashMap<>();
        claims.put("authorities", new ObjectMapper().writeValueAsString(roles));
        claims.put("isAdmin", isAdmin);
        claims.put("username", username); //Parametro adicional (opcional)

        String token = Jwts.builder()
                .claims(claims)
                .subject(username)
                .signWith(SECRECT_KEY)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + 3600000))
                .compact();

        // Añade el token al encabezado de la respuesta
        response.addHeader(HEADER_AUTHORIZATION, PREFIX_TOKEN + token);

        // Crea un cuerpo de respuesta JSON con el token y un mensaje de éxito
        Map<String, Object> body = new HashMap<>();
        body.put("token", token);
        body.put("message", String.format("Hola %s, has iniciado sesión correctamente", username));
        body.put("username", username);

        // Escribe la respuesta JSON
        response.getWriter().write(new ObjectMapper().writeValueAsString(body));
        response.setStatus(200); // Establece el estado de la respuesta a 200 OK
        response.setContentType("application/json"); // Establece el tipo de contenido a JSON
    }

    // Método llamado cuando la autenticación falla
    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response,
            AuthenticationException failed) throws IOException, ServletException {

        // Crea un cuerpo de respuesta JSON con un mensaje de error
        Map<String, Object> body = new HashMap<>();
        body.put("message", "Error en la autenticación");
        body.put("error", failed.getMessage());

        // Escribe la respuesta JSON
        response.getWriter().write(new ObjectMapper().writeValueAsString(body));
        response.setStatus(401); // Establece el estado de la respuesta a 401 Unauthorized
        response.setContentType("application/json"); // Establece el tipo de contenido a JSON

    }

}
