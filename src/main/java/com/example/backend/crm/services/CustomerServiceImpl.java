package com.example.backend.crm.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.backend.crm.models.entities.Customer;
import com.example.backend.crm.repositories.CustomerRepository;




/*
 * Los servicios son clases Java que proporcionan una
 * capa de abstracción entre la lógica de la aplicación
 * y las fuentes de datos o servicios externos.
 */
@Service
public class CustomerServiceImpl implements CustomerService {

    /*
     * Autowired se utiliza para implementar la inyección
     * de dependencias, lo cual permite que un objeto
     * reciba sus dependencias de otro objeto, en lugar de
     * crearlas o buscarlas por sí mismo.
     */
    @Autowired
    private CustomerRepository customerRepository;

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
    public Customer saveCustomer(Customer customer) {
        return customerRepository.save(customer);
    }

    /*
     * @Transactional(readOnly = true) se debe usar en
     * métodos de servicio que solo realizan operaciones
     * de lectura de datos, pero no modifican los datos.
     */
    @Transactional(readOnly = true)
    @Override
    public Customer findById(Long id) {
        return customerRepository.findById(id).orElse(null);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Customer> listAll() {
        return (List<Customer>) customerRepository.findAll();
    }

    @Transactional
    @Override
    public void deleteCustomer(Customer customer) {
        customerRepository.delete(customer);
    }

    @Transactional
    @Override
    public boolean existById(Long id) {
        return customerRepository.existsById(id);
    }

    @Transactional
    @Override
    public Optional<Customer> updateCustumer(Customer customer, Long id) {
        Optional<Customer> o = customerRepository.findById(id);
        Customer customerOptional = null;

        if(o.isPresent()){

            /*
             * orElseThrow() se utiliza para obtener el valor del
             * Optional si está presente y, de lo contrario,
             * lanzar una excepción proporcionada como argumento.
             */
            Customer customerDb = o.orElseThrow();
            customerDb.setAddress(customer.getAddress());
            customerDb.setCity(customer.getCity());
            customerDb.setName(customer.getName());
            customerDb.setNeighborhood(customer.getNeighborhood());
            customerDb.setPhoneNumber(customer.getPhoneNumber());
            customerDb.setEmail(customer.getEmail());
            customerOptional = customerRepository.save(customerDb);
        }

        /*
         * ofNullable() se utiliza para crear un Optional que
         * puede contener un valor nulo, o el valor actualizado
         * del usuario.
         */
        return Optional.ofNullable(customerOptional);
    }

    @Override
    public boolean existByPhone(String phone) {
        return customerRepository.existsByPhoneNumber(phone);
    }

    @Override
    public Customer findByPhone(String phone) {
        return customerRepository.findByPhoneNumber(phone);
    }

    @Override
    public List<Customer> findByName(String name) {
        return customerRepository.findByNameContaining(name);
    }

    

}
