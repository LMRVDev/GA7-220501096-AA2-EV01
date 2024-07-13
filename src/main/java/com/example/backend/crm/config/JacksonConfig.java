package com.example.backend.crm.config;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Clase de configuración para Jackson, el cual es el motor de serialización y
 * deserialización de JSON
 * utilizado por defecto en Spring Boot. Esta configuración permite manejar
 * adecuadamente los tipos de fecha y hora
 * de Java 8 (como LocalDate y LocalDateTime).
 */
@Configuration
public class JacksonConfig {

    /**
     * Método que define un bean de ObjectMapper configurado para manejar
     * adecuadamente los tipos de fecha y hora
     * de Java 8. Este método registra el módulo JavaTimeModule para permitir la
     * serialización y deserialización
     * de estos tipos, y deshabilita la escritura de fechas como timestamps.
     *
     * @return un objeto ObjectMapper configurado.
     */
    @Bean
    public ObjectMapper objectMapper() {

        // Crear una nueva instancia de ObjectMapper
        ObjectMapper objectMapper = new ObjectMapper();

        // Registrar el módulo JavaTimeModule para manejar tipos de fecha y hora de Java
        // 8
        objectMapper.registerModule(new JavaTimeModule());

        objectMapper.registerModule(new Jdk8Module());

        /*
         * Deshabilitar la escritura de fechas como timestamps (es decir, en formato
         * numérico)
         * Esta línea de configuración asegura que las fechas se serialicen como cadenas
         * en formato
         * ISO-8601 (por ejemplo, "2024-01-15").
         */
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        // Aceptar un valor único como un array
        objectMapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);

        return objectMapper;
    }
}