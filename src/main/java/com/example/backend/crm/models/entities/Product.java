package com.example.backend.crm.models.entities;

import java.math.BigDecimal;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

/*
 * Las entidades son clases Java que se utilizan para
 * representar datos en una base de datos relacional.
 */

@Data
@Entity
@Table(name = "product")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty(message = "El nombre del producto es obligatorio")
    private String name;

    @NotNull(message = "El precio del producto es obligatorio")
    private BigDecimal price; 

    @NotNull(message = "La descripción del producto es obligatoria")
    private String description;

    /*
     * Product - OrderDetail:
     * Tipo de relación: Uno a Muchos (One-to-Many)
     * Cómo se establece: A través de la lista orderDetails
     * en la clase Product.
     */
    //@JsonManagedReference
    @OneToMany(mappedBy = "product")
    @JsonIgnore
    private List<OrderDetail> orderDetails;

    private String barcode;

    private String supplier;


}
