package com.example.backend.crm.services;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import com.example.backend.crm.models.entities.Order;


/*
 * La interfaz define métodos relacionados con la gestión
 * de la entidad, son comunes en las capas de servicio para
 * definir contratos que deben implementarse en clases
 * concretas.
 */
public interface OrderService {

    List<Order> listAll();

    Order saveOrder(Order order);

    Order findById(Long id);

    void deleteOrder(Order order);

    boolean existById(Long id);

    List<Order> getOrdersBetweenDates(LocalDate startDate, LocalDate endDate);

}
