package com.example.backend.crm.services;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.backend.crm.models.entities.OrderDetail;
import com.example.backend.crm.repositories.OrderDetailRepository;


/*
 * Los servicios son clases Java que proporcionan una
 * capa de abstracción entre la lógica de la aplicación
 * y las fuentes de datos o servicios externos.
 */
@Service
public class OrderDetailServiceImpl implements OrderDetailService{

    /*
     * Autowired se utiliza para implementar la inyección
     * de dependencias, lo cual permite que un objeto
     * reciba sus dependencias de otro objeto, en lugar de
     * crearlas o buscarlas por sí mismo.
     */
    @Autowired
    private OrderDetailRepository orderDetailRepository;

    /*
     * @Transactional indica que este método debe ejecutarse
     * dentro de una transacción, si ocurre una excepción
     * durante la ejecución del método, la transacción se
     * revertirá automáticamente. Si el método se ejecuta
     * correctamente, la transacción se confirmará al
     * finalizar el método.
     */
    @Override
    @Transactional(readOnly = true)
    public List<OrderDetail> listAll() {
        return (List<OrderDetail>) orderDetailRepository.findAll();
    }

    @Override
    @Transactional
    public OrderDetail saveOrderDetail(OrderDetail orderDetail) {
        return orderDetailRepository.save(orderDetail);
    }

    @Override
    @Transactional(readOnly = true)
    public OrderDetail findById(Long id) {
        return orderDetailRepository.findById(id).orElse(null);
    }

    @Override
    @Transactional
    public void deleteOrderDetail(OrderDetail orderDetail) {
        orderDetailRepository.delete(orderDetail);
    }

    @Override
    @Transactional
    public boolean existById(Long id) {
        return orderDetailRepository.existsById(id);
    }

}
