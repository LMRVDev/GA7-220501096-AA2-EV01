package com.example.backend.crm.models.entities;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/*
 * Las entidades son clases Java que se utilizan para
 * representar datos en una base de datos relacional.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
@Entity
@Table(name = "orders")
public class Order {

    @Id
    @Column(name = "idOrder")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "La fecha del pedido es obligatoria")
    @Column(columnDefinition  = "DATETIME")
    private Date orderDate;

    @NotNull(message = "La forma de pago es obligatoria")
    private String paymentMode;

    @NotNull(message = "El total del pedido es obligatorio")
    private BigDecimal totalAmount;

    /*
     * Order - Customer:
     * Tipo de relación: Muchos a Uno (Many-to-One)
     * Cómo se establece: A través de la columna customer_id
     * en la tabla "orders".
     */
    @ManyToOne(optional = false)
    @JoinColumn(name = "customer_id")
    @JsonBackReference
    private Customer customer;

    /*
     * Order - OrderDetail:
     * Tipo de relación: Uno a Muchos (One-to-Many)
     * Cómo se establece: A través de la lista
     * orderDetails en la clase Order.
     */
    @OneToMany(mappedBy = "order")
    @JsonManagedReference
    private List<OrderDetail> orderDetails;



}
