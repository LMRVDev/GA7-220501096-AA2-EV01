package com.example.backend.crm.services;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.backend.crm.models.entities.Order;
import com.example.backend.crm.repositories.OrderRepository;


/*
 * Los servicios son clases Java que proporcionan una
 * capa de abstracción entre la lógica de la aplicación
 * y las fuentes de datos o servicios externos.
 */
@Service
public class OrderServiceImpl implements OrderService {

    /*
     * Autowired se utiliza para implementar la inyección
     * de dependencias, lo cual permite que un objeto
     * reciba sus dependencias de otro objeto, en lugar de
     * crearlas o buscarlas por sí mismo.
     */
    @Autowired
    private OrderRepository orderRepository;

    /*
     * @Transactional indica que este método debe ejecutarse
     * dentro de una transacción, si ocurre una excepción
     * durante la ejecución del método, la transacción se
     * revertirá automáticamente. Si el método se ejecuta
     * correctamente, la transacción se confirmará al
     * finalizar el método.
     */
    @Transactional
    @Override
    public Order saveOrder(Order order) {
        return orderRepository.save(order);
    }

    /*
     * @Transactional(readOnly = true) se debe usar en
     * métodos de servicio que solo realizan operaciones
     * de lectura de datos, pero no modifican los datos.
     */
    @Transactional(readOnly = true)
    @Override
    public Order findById(Long id) {
        return orderRepository.findById(id).orElse(null);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Order> listAll() {
        return (List<Order>) orderRepository.findAll();
    }

    @Transactional
    @Override
    public void deleteOrder(Order order) {
        orderRepository.delete(order);
    }

    @Transactional
    @Override
    public boolean existById(Long id) {
        return orderRepository.existsById(id);
    }

    @Override
    public List<Order> getOrdersBetweenDates(LocalDate startDate, LocalDate endDate) {
        return orderRepository.findByOrderDateBetween(startDate, endDate);
    }

    

}
