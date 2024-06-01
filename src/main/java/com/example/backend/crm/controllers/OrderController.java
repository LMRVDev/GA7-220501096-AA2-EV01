package com.example.backend.crm.controllers;

import org.springframework.web.bind.annotation.RestController;

import com.example.backend.crm.models.entities.Customer;
import com.example.backend.crm.models.entities.Order;
import com.example.backend.crm.models.entities.OrderDetail;
import com.example.backend.crm.models.payload.MessageResponse;
import com.example.backend.crm.services.CustomerService;
import com.example.backend.crm.services.OrderDetailService;
import com.example.backend.crm.services.OrderService;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

/*
 * La clase controlador se encarga de definir los endpoints
 * HTTP para acceder a la lógica de negocio de una entidad,
 * los puntos finales definen las URIs a las que se
 * realizan las peticiones y las operaciones disponibles
 * en un servidor web o API RESTful.
 *
 * @RestController se utiliza para marcar una clase como
 * un controlador de Spring que maneja solicitudes HTTP y
 * produce respuestas HTTP.
 *
 * @RequestMapping se utiliza para definir la estructura
 * de las rutas URL, en este caso todas las rutas
 * definidas en los métodos de este controlador se
 * ejecutan en la ruta base "api/v1/student"
 */
@RestController
@RequestMapping("/order")
@CrossOrigin(originPatterns = "*")
public class OrderController {


    /*
     * @Autowired se utiliza para inyectar instancias de
     * clases de servicio u otros componentes en
     * controladores u otras clases que los necesiten.
     */
    @Autowired
    private OrderService orderService;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private OrderDetailService orderDetailService;

    /*
     * @GetMapping se usa para mapear solicitudes HTTP GET
     * a un método controlador, en este caso el método
     * showAll() solo manejará solicitudes GET a la ruta
     * /products
     *
     * @ResponseStatus se utiliza para definir el código de
     * estado HTTP que debe devolver un controlador cuando
     * se invoca alguno de sus métodos
     */
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> showAll() {
        List<Order> getList = orderService.listAll();

        /*
         * MensajeResponse se utiliza para encapsular la
         * respuesta de los métodos del controlador en
         * una estructura estándar.
         */
        if (getList == null) {
            return new ResponseEntity<>(MessageResponse.builder()
                    .message("No existen registros")
                    .object(null)
                    .build(), HttpStatus.OK);
        }

        return new ResponseEntity<>(
                MessageResponse.builder()
                        .message("")
                        .object(getList)
                        .build(),
                HttpStatus.OK);
    }

    /*
     * @PostMapping es una anotación que se utiliza para
     * mapear una solicitud HTTP POST a un método.
     */
    // @PostMapping
    // @ResponseStatus(HttpStatus.CREATED)
    // public ResponseEntity<?> create(@RequestBody Order order) {

    // try {

    // Order orderSave = orderService.saveOrder(order);

    // return new ResponseEntity<>(MessageResponse.builder()
    // .message("Orden guardada")
    // .object(orderSave)
    // .build(), HttpStatus.CREATED);

    // } catch (Exception e) {
    // return new ResponseEntity<>(MessageResponse.builder()
    // .message(e.getMessage())
    // .object(null)
    // .build(), HttpStatus.METHOD_NOT_ALLOWED);
    // }
    // }

    /*
     * @GetMapping("/{id}") se utiliza para mapear solicitudes
     * HTTP GET a un método controlador que maneja la búsqueda
     * de un recurso por ID. El cual se define en el parametro
     * del método con la anotacion @PathVariable
     *
     * @PathVariable se utiliza para vincular un valor que
     * aparece en la URL a un parámetro del método del controlador,
     * permitiendo que el valor se utilice en la lógica del controlador.
     */
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> showById(@PathVariable Long id) {

        Order orderSave = orderService.findById(id);

        if (orderSave == null) {
            return new ResponseEntity<>(MessageResponse.builder()
                    .message("El registro no fue encontrado")
                    .object(null)
                    .build(), HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(
                MessageResponse.builder()
                        .message("")
                        .object(orderSave)
                        .build(),
                HttpStatus.OK);
    }

    /*
     * @DeleteMapping especifica que el método solo manejará
     * las solicitudes que se realicen utilizando el método
     * HTTP DELETE.
     */
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<?> delete(Long id) {

        try {

            Order orderDelete = orderService.findById(id);
            orderService.deleteOrder(orderDelete);

            return new ResponseEntity<>(MessageResponse.builder()
                    .message("Orden eliminada")
                    .object(null)
                    .build(), HttpStatus.NO_CONTENT);

        } catch (Exception e) {
            return new ResponseEntity<>(MessageResponse.builder()
                    .message(e.getMessage())
                    .object(null)
                    .build(), HttpStatus.METHOD_NOT_ALLOWED);
        }
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> createOrder(@RequestBody Map<String, Object> request) {

        try {
            
            //Obtener Order, Customer y OrderList del JSON
            Order order = new ObjectMapper().convertValue(request.get("order"), Order.class);
            Customer customer = new ObjectMapper().convertValue(request.get("customer"), Customer.class);
            List<OrderDetail> orderDetailsFromJson = (List<OrderDetail>) request.get("orderDetail");
            
            // Crear una lista vacía de OrderDetail para almacenar los objetos convertidos:
            List<OrderDetail> orderDetails = new ArrayList<>();
    
            // Iterar la lista y convertir cada objeto a OrderDetail
            for (Object obj : orderDetailsFromJson) {
                OrderDetail orderDetailIte = new ObjectMapper().convertValue(obj, OrderDetail.class);
                orderDetails.add(orderDetailIte);
            }
    
            // Obtener la fecha actual
            Date orderDate = Date.valueOf(LocalDate.now());
    
            // Verificar si el cliente existe
            if (customerService.existByPhone(customer.getPhoneNumber())) {
    
                // El cliente ya existe, obtenlo de la base de datos
                Customer existingCustomer = customerService.findByPhone(customer.getPhoneNumber());
    
                // El cliente ya existe, asignar su ID a la orden
                order.setCustomer(existingCustomer);
                System.out.println("Cliente encontrado: " + existingCustomer.getName());
    
            } else {
    
                // El cliente no existe, crearlo
                Customer customerSave = customerService.saveCustomer(customer);
                order.setCustomer(customerSave);
    
            }
    
            // Asignar la fecha a la orden
            order.setOrderDate(orderDate);
    
            //Asignar OrderDetails a la Order
            order.setOrderDetails(orderDetails);
    
            // Crear la orden
            Order orderSave = orderService.saveOrder(order);
    
            //Guardar OrderDetail
            for (OrderDetail ite: orderDetails){
                ite.setOrder(orderSave);
                orderDetailService.saveOrderDetail(ite);
            }
    
            // Devolver la respuesta
            return new ResponseEntity<>(MessageResponse.builder()
                    .message("Orden guardada")
                    .object(orderSave)
                    .build(), HttpStatus.CREATED);

        } catch (Exception e) {

            return new ResponseEntity<>(MessageResponse.builder()
            .message(e.getMessage())
            .object(null)
            .build(), HttpStatus.METHOD_NOT_ALLOWED);

        }


    }

}
