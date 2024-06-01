package com.example.backend.crm.services;

import java.util.List;
import java.util.Optional;

import com.example.backend.crm.models.entities.Product;


/*
 * La interfaz define métodos relacionados con la gestión
 * de la entidad, son comunes en las capas de servicio para
 * definir contratos que deben implementarse en clases
 * concretas.
 */
public interface ProductService {

    List<Product> listAll();

    Product saveProduct(Product product);

    Product findById(Long id);

    void deleteProduct(Product product);

    boolean existById(Long id);

    Optional<Product> updateProduct(Product product, Long id);

}
