package com.example.backend.crm.models.entities;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/*
 * Las entidades son clases Java que se utilizan para
 * representar datos en una base de datos relacional.
 * 
 * La anotación JsonIdentityInfo permite gestionar relaciones 
 * bidireccionales durante la serializacion/desserialización JSON.
 * En este caso, la propiedad List<Order> orders será gestionada segun su id. 
 */
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
@Entity
@Table(name = "customer")
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty(message = "El nombre del cliente es obligatorio")
    @Size(min = 3, max = 50, message = "El nombre del cliente debe tener entre 3 y 50 caracteres")
    private String name;

    @NotEmpty(message = "El número de teléfono del cliente es obligatorio")
    @Size(min = 10, message = "El número de teléfono del cliente debe tener 10 dígitos")
    private String phoneNumber;

    @NotEmpty(message = "La dirección del cliente es obligatoria")
    @Size(min = 5, message = "La dirección del cliente debe tener al menos 5 caracteres")
    private String address;

    @NotEmpty(message = "El barrio del cliente es obligatorio")
    @Size(min = 3, message = "El barrio del cliente debe tener al menos 3 caracteres")
    private String neighborhood;

    //@NotEmpty(message = "La ciudad del cliente es obligatoria")
    @Size(min = 3, message = "La ciudad del cliente debe tener al menos 3 caracteres")
    private String city;

    //@JsonManagedReference
    @OneToMany(mappedBy = "customer")
    private List<Order> orders;

    @Size(min = 3)
    private String email;

    
}
