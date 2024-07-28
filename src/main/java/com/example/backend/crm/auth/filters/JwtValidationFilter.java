package com.example.backend.crm.auth.filters;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import com.example.backend.crm.auth.SimpleGrantedAuthorityJsonCreator;
import static com.example.backend.crm.auth.TokenJwtConfig.PREFIX_TOKEN;
import static com.example.backend.crm.auth.TokenJwtConfig.SECRECT_KEY;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/*
 * La clase BasicAuthenticationFilter proporciona la 
 * funcionalidad básica para el filtrado de 
 * autenticación HTTP.
 */
public class JwtValidationFilter extends
        BasicAuthenticationFilter {

    /*
     * AuthenticationManager: Interface de Spring Security 
     * utilizada para gestionar la autenticación.
     */
    public JwtValidationFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }

    /* Método que realiza el filtrado interno de la 
     * solicitud HTTP.
     * 
     * HttpServletRequest y HttpServletResponse representan 
     * la solicitud HTTP y la respuesta HTTP, respectivamente.
     * 
     * FilterChain: Interface que representa la cadena 
     * de filtros a través de la cual debe pasar la 
     * solicitud HTTP.
     */
    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain chain)
            throws IOException, ServletException {

        // Obtiene el encabezado "Authorization" de la solicitud
        String header = request.getHeader("Authorization");

        // Si el encabezado es nulo o no comienza con el prefijo esperado, 
        //  continúa con el siguiente filtro
        if (header == null || !header.startsWith(PREFIX_TOKEN)) {
            chain.doFilter(request, response);
            return;
        }

        // Remueve el prefijo del token para obtener solo el token
        String token = header.replace(PREFIX_TOKEN, "");

        try {
            /*
             * La clase Claims representa una pieza de información a incluir 
             * en un token JWT (usuario, rol, fecha de expiración, etc)
             * 
             * Se decodifica y verifica un token JWT usando una clave secreta, 
             * para extraer y almacenar el sujeto del token.
             */
            Claims claims = Jwts.parser()
                    .verifyWith(SECRECT_KEY)
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();

            Object authoritiesClaims = claims.get("authorities");

            //Parametro adicional (opcional)
            Object username2 = claims.get("username");
            logger.info("Username" + username2.toString());

            /*
            * Se obtiene el sujeto (subject) del token JWT, 
            * que generalmente es el nombre de usuario.
             */
            String username = claims.getSubject();

            // Crea una Colección de autoridades (roles) del usuario
            Collection<? extends GrantedAuthority> authorities = Arrays
                    .asList(new ObjectMapper()
                            /*
                             * La interface addMixIn permite que Jackson pueda deserializar archivos JSON 
                             * ("authority": "ROLE_ADMIN") como SimpleGrantedAuthority("ROLE_ADMIN") 
                             * utilizando las anotaciones de la clase SimpleGrantedAuthorityJsonCreator 
                             */
                            .addMixIn(SimpleGrantedAuthority.class, SimpleGrantedAuthorityJsonCreator.class)
                            /*
                             * Conversión de la cadena JSON que contiene los "authorities" en un array de 
                             * SimpleGrantedAuthority. Luego, Arrays.asList() convierte ese array en una lista
                             */
                            .readValue(authoritiesClaims.toString().getBytes(), SimpleGrantedAuthority[].class));

            /*
             * La clase UsernamePasswordAuthenticationToken implementa 
             * la interface Authentication y permite crear un token
             * de autenticación con el nombre de usuario y las 
             * autoridades (roles)
             */
            UsernamePasswordAuthenticationToken authentication
                    = new UsernamePasswordAuthenticationToken(username, null, authorities);

            /*
             * Clase que proporciona acceso al contexto de 
             * seguridad, que almacena los detalles de 
             * autenticación del usuario actual.
             */
            SecurityContextHolder.getContext().setAuthentication(authentication);

            // Continúa con el siguiente filtro en la cadena
            chain.doFilter(request, response);

        } catch (JwtException e) {

            // Si la clave secreta no coincide, se envía una respuesta de error
            Map<String, String> body = new HashMap<>();
            body.put("error", e.getMessage());
            body.put("message", "El token jwt no es válido");

            // Escribe la respuesta de error en formato JSON
            response.getWriter().write(new ObjectMapper().writeValueAsString(body));
            response.setStatus(403);
            response.setContentType("application/json");
        }

    }

}
