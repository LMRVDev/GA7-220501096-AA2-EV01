package com.example.backend.crm.models.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "order_detail")
public class OrderDetail {

    @JsonIgnore
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /*
     * OrderDetail - Order:
     * Tipo de relación: Muchos a Uno (Many-to-One)
     * Cómo se establece: A través de la columna order_id
     * en la tabla order-detail.
     */
    @ManyToOne
    @JoinColumn(name = "order_id")
    @JsonBackReference
    //@JsonIgnore
    private Order order;

    /*
     * OrderDetail - Product:
     * Tipo de relación: Muchos a Uno (Many-to-One) para
     * ambos lados.
     * Cómo se establece: A través de las columnas order_id
     * y product_id en la tabla order-detail.
     */
    @ManyToOne
    @JoinColumn(name = "product_id")
    //@JsonBackReference
    private Product product;

    private int quantity;

}
