package com.example.backend.crm.auth;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @JsonProperty ("authority"): Define el nombre de la clave en el JSON que
 * corresponde al parámetro del constructor.
 * @JsonCreator Indica que este constructor debe usarse para crear instancias de
 * la clase durante la deserialización. Ejemplo JSON: "authority": "ROLE_ADMIN"
 * se deserializa como new SimpleGrantedAuthority("ROLE_ADMIN") utilizando el
 * constructor anotado en el MixIn.
 */
public abstract class SimpleGrantedAuthorityJsonCreator {

    @JsonCreator
    public SimpleGrantedAuthorityJsonCreator(@JsonProperty("authority") String role) {
    }

}
