package com.example.backend.crm.repositories;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.example.backend.crm.models.entities.Customer;



/*
 * La etiqueta @Repository se usa para marcar una clase
 * como un componente de repositorio, es decir una parte
 * importante de la arquitectura de una aplicación que se
 * encarga de interactuar con la base de datos o el sistema
 * de almacenamiento de datos.
 *
 * La clase CrudRepository es parte de Spring Data JPA y
 * proporciona métodos predefinidos para realizar operaciones
 * de acceso a datos comunes, como crear, leer, actualizar y
 * eliminar (CRUD), sin necesidad de escribir consultas SQL ni
 * implementar estos métodos manualmente.
 */
@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long>{
    
    boolean existsByPhoneNumber(String phoneNumber);

    Customer findByPhoneNumber(String phoneNumber);

    List<Customer> findByNameContaining(String query);

}
