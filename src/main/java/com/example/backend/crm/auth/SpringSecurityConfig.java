package com.example.backend.crm.auth;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import com.example.backend.crm.auth.filters.JwtAuthenticationFilter;
import com.example.backend.crm.auth.filters.JwtValidationFilter;

/*
 * La siguiente clase se encarga de definir las políticas de 
 * seguridad para las peticiones HTTP .
 */
@Configuration
public class SpringSecurityConfig {

    @Value("${FRONTEND_URL}")
    String frontendUrl;

    @Autowired
    private AuthenticationConfiguration authenticationConfiguration;

    /*
     * Define un bean para codificar contraseñas usando BCrypt.
     */
    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /*
     * Obtiene el administrador de autenticaciones a partir de la configuración de autenticación.
     */
    AuthenticationManager authenticationManager() throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    /*
     * La clase SecurityFilterChain define una cadena de filtros 
     * en función de evaluar la autenticación y autorización de 
     * solicitudes HTTP.
     * 
     * El objeto HttpSecurity proporciona métodos para especificar 
     * directivas de seguridad, permite construir una cadena de 
     * filtros al encadenar llamadas a métodos en el objeto.
     */
    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        //Autoriza peticiones GET a la ruta "/users" sin autenticación
        return http
                .authorizeHttpRequests(authorize -> authorize
                // Permite todas las solicitudes y rutas específicas sin autenticación.
                .requestMatchers(HttpMethod.GET, "/users").permitAll()
                .requestMatchers(HttpMethod.GET, "/users/verifyEmail/**").permitAll()
                .requestMatchers(HttpMethod.PATCH, "/users/updatePassword/**").permitAll()
                // Requiere roles específicos para otras rutas.
                .requestMatchers(HttpMethod.POST, "/users/admin").hasAnyRole("ADMIN")
                .requestMatchers(HttpMethod.GET, "/users/{id}").hasAnyRole("ADMIN")
                .requestMatchers(HttpMethod.POST, "/users").hasAnyRole("ADMIN")
                .requestMatchers(HttpMethod.PUT, "/users/{id}").hasAnyRole("ADMIN")
                .requestMatchers(HttpMethod.DELETE, "/users/{id}").hasAnyRole("ADMIN")
                .requestMatchers("/users/**").hasRole("ADMIN")
                .requestMatchers(HttpMethod.GET, "/products/downloadFile").hasAnyRole("ADMIN")
                .requestMatchers(HttpMethod.POST, "/products").hasAnyRole("ADMIN")
                .requestMatchers(HttpMethod.PUT, "/products/**").hasAnyRole("ADMIN")
                .requestMatchers(HttpMethod.GET, "/products/filter**").hasAnyRole("ADMIN", "USER")
                .requestMatchers(HttpMethod.GET, "/products/**").hasAnyRole("ADMIN", "USER")
                .requestMatchers(HttpMethod.POST, "/order/saveOrders").hasAnyRole("ADMIN")
                .requestMatchers(HttpMethod.GET, "/order/filterByDate?**").hasAnyRole("ADMIN", "USER")
                .requestMatchers(HttpMethod.DELETE, "/customer/**").hasAnyRole("ADMIN")
                .requestMatchers(HttpMethod.PUT, "/customer/**").hasAnyRole("ADMIN")
                .requestMatchers(HttpMethod.POST, "/customer").hasAnyRole("ADMIN")
                .requestMatchers(HttpMethod.GET, "/customer/**").hasAnyRole("ADMIN", "USER")
                .requestMatchers(HttpMethod.GET, "/customer/filter**").hasAnyRole("ADMIN", "USER")
                //Requiere autenticación para cualquier otra petición 
                .anyRequest().authenticated())
                //Desactivación de proteción CSRF (Cross-Site Request Forgery) 
                .csrf(csrf -> csrf.disable())
                /*
                 * Agrega los filtros de autenticación y validación JWT a la cadena de filtros.
                 */
                .addFilter(new JwtAuthenticationFilter(authenticationConfiguration.getAuthenticationManager()))
                .addFilter(new JwtValidationFilter(authenticationConfiguration.getAuthenticationManager()))
                /*
                 * Al gestionar la sesion con una politica STATELESS 
                 * el servidor no almacena ninguna información de 
                 * sesión en el cliente.
                 */
                .sessionManagement(management -> management.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                // Habilita y configura CORS.
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .build();
    }

    /*
     * Configura las reglas de CORS permitiendo solicitudes desde "http://localhost:5173".
     */
    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowedOrigins(Arrays.asList(frontendUrl));
        config.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "PATCH"));
        config.setAllowedHeaders(Arrays.asList("Authorization", "Content-Type"));
        config.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return source;
    }

    /*
     * Registra el filtro de CORS con la mayor precedencia en la cadena de filtros.
     */
    @Bean
    FilterRegistrationBean<CorsFilter> corsFilter() {
        FilterRegistrationBean<CorsFilter> bean
                = new FilterRegistrationBean<>(
                        new CorsFilter(corsConfigurationSource())
                );
        bean.setOrder(Ordered.HIGHEST_PRECEDENCE);
        return bean;
    }

}
