package com.example.backend.crm.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.backend.crm.models.entities.Product;
import com.example.backend.crm.repositories.ProductRepository;



/*
 * Los servicios son clases Java que proporcionan una
 * capa de abstracción entre la lógica de la aplicación
 * y las fuentes de datos o servicios externos.
 */
@Service
public class ProductServiceImpl implements ProductService {

    /*
     * Autowired se utiliza para implementar la inyección
     * de dependencias, lo cual permite que un objeto
     * reciba sus dependencias de otro objeto, en lugar de
     * crearlas o buscarlas por sí mismo.
     */
    @Autowired
    private ProductRepository productRepository;

    

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
    public Product saveProduct(Product product) {
        return productRepository.save(product);
    }

    /*
     * @Transactional(readOnly = true) se debe usar en
     * métodos de servicio que solo realizan operaciones
     * de lectura de datos, pero no modifican los datos.
     */
    @Transactional(readOnly = true)
    @Override
    public Product findById(Long id) {
        return productRepository.findById(id).orElse(null);
    }

    @Transactional
    @Override
    public void deleteProduct(Product product) {
        productRepository.delete(product);
    }

    @Transactional(readOnly = true)
    @Override
    public boolean existById(Long id) {
        return productRepository.existsById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Product> listAll() {
        return (List<Product>) productRepository.findAll();
    }

    @Transactional
    @Override
    public Optional<Product> updateProduct(Product product, Long id) {
        Optional<Product> o = productRepository.findById(id);
        Product productOptional = null;

        if(o.isPresent()){

            /*
             * orElseThrow() se utiliza para obtener el valor del
             * Optional si está presente y, de lo contrario,
             * lanzar una excepción proporcionada como argumento.
             */
            Product productDb = o.orElseThrow();
            productDb.setName(product.getName());
            productDb.setDescription(product.getDescription());
            productDb.setPrice(product.getPrice());
            productDb.setBarcode(product.getBarcode());
            productDb.setSupplier(product.getSupplier());
            productOptional = productRepository.save(productDb);
        }

        /*
         * ofNullable() se utiliza para crear un Optional que
         * puede contener un valor nulo, o el valor actualizado
         * del usuario.
         */
        return Optional.ofNullable(productOptional);
    }

    @Override
    public List<Product> findByName(String name) {
        return productRepository.findByNameContaining(name);
    }

    @Override
    public boolean existByBarcode(String barcode) {
        return productRepository.existsByBarcode(barcode);
    }

    @Override
    public Product findByBarcode(String barcode) {
        return productRepository.findByBarcode(barcode);
        
    }

    @Override
    public List<Product> findAllProducts() {
        return (List<Product>) productRepository.findAll();
    }

}
