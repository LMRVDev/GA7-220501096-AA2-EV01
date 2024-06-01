package com.example.backend.crm.services;

import java.util.List;

import com.example.backend.crm.models.entities.OrderDetail;


/*
 * La interfaz define métodos relacionados con la gestión
 * de la entidad, son comunes en las capas de servicio para
 * definir contratos que deben implementarse en clases
 * concretas.
 */
public interface OrderDetailService {

    List<OrderDetail> listAll();

    OrderDetail saveOrderDetail(OrderDetail orderDetail);

    OrderDetail findById(Long id);

    void deleteOrderDetail(OrderDetail orderDetail);

    boolean existById(Long id);



}
