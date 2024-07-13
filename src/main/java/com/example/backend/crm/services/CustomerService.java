package com.example.backend.crm.services;

import java.util.List;
import java.util.Optional;

import com.example.backend.crm.models.entities.Customer;


/*
 * La interfaz define métodos relacionados con la gestión
 * de la entidad, son comunes en las capas de servicio para
 * definir contratos que deben implementarse en clases
 * concretas.
 */
public interface CustomerService {

    List<Customer> listAll();

    Customer saveCustomer(Customer customer);

    Customer findById(Long id);

    void deleteCustomer(Customer customer);

    boolean existById(Long id);

    Optional<Customer> updateCustumer(Customer customer, Long id);

    boolean existByPhone(String phone);

    Customer findByPhone (String phone);

    List<Customer> findByName(String name); 
}
