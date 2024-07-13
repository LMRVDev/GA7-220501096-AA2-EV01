package com.example.backend.crm.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.example.backend.crm.models.entities.Customer;
import com.example.backend.crm.models.payload.MessageResponse;
import com.example.backend.crm.services.CustomerService;
import org.springframework.web.bind.annotation.RequestParam;

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
@RequestMapping("/customer")
@CrossOrigin(originPatterns = "*")
public class CustomerController {

    /*
     * @Autowired se utiliza para inyectar instancias de
     * clases de servicio u otros componentes en
     * controladores u otras clases que los necesiten.
     */
    @Autowired
    private CustomerService customerService;

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
        List<Customer> getList = customerService.listAll();

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
                        .message("Lista de Clientes")
                        .object(getList)
                        .build(),
                HttpStatus.OK);
    }

    @GetMapping("/filter")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> filterCustomer(@RequestParam String query) {

        List<Customer> clientResult = customerService.findByName(query);

        if (clientResult.isEmpty()) {
            return new ResponseEntity<>(MessageResponse.builder()
                    .message("Ningun cliente coincide con el parámetro de búsqueda")
                    .object(null)
                    .build(), HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(MessageResponse.builder()
                .message("")
                .object(clientResult)
                .build(), HttpStatus.OK);

    }

    /*
     * @PostMapping es una anotación que se utiliza para
     * mapear una solicitud HTTP POST a un método.
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> create(@RequestBody Customer customer) {

        try {

            if (customerService.existByPhone(customer.getPhoneNumber())) {

                Customer customerFound = customerService.findByPhone(customer.getPhoneNumber());

                return new ResponseEntity<>(MessageResponse.builder()
                        .message("El cliente ya se encuentra registrado")
                        .object(customerFound.getId())
                        .build(), HttpStatus.CONFLICT);

            } else {

                Customer customerSave = customerService.saveCustomer(customer);

                return new ResponseEntity<>(MessageResponse.builder()
                        .message("Cliente guardado")
                        .object(customerSave)
                        .build(), HttpStatus.CREATED);
            }

        } catch (Exception e) {

            return new ResponseEntity<>(MessageResponse.builder()
                    .message(e.getMessage())
                    .object(null)
                    .build(), HttpStatus.METHOD_NOT_ALLOWED);

        }
    }

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

        Customer customerSave = customerService.findById(id);

        if (customerSave == null) {
            return new ResponseEntity<>(MessageResponse.builder()
                    .message("El registro no fue encontrado")
                    .object(null)
                    .build(), HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(
                MessageResponse.builder()
                        .message("")
                        .object(customerSave)
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
    public ResponseEntity<?> delete(@PathVariable Long id) {

        try {

            Customer customerDelete = customerService.findById(id);
            customerService.deleteCustomer(customerDelete);

            MessageResponse messageResponse = MessageResponse.builder()
                    .message("Cliente eliminado")
                    .object(null)
                    .build();

            return ResponseEntity.ok(messageResponse);

        } catch (Exception e) {
            return new ResponseEntity<>(MessageResponse.builder()
                    .message(e.getMessage())
                    .object(null)
                    .build(), HttpStatus.METHOD_NOT_ALLOWED);
        }
    }

    /*
     * @PutMapping especifica que el método solo manejará
     * las solicitudes que se realicen utilizando el método
     * HTTP PUT.
     */
    @PutMapping("/{id}")
    public ResponseEntity<?> update(@RequestBody Customer customer,
            @PathVariable Long id) {
        Optional<Customer> o = customerService.updateCustumer(customer, id);

        if (o.isPresent()) {
            return new ResponseEntity<>(MessageResponse.builder()
                    .message("Cliente actualizado")
                    .object(o)
                    .build(), HttpStatus.CREATED);

        } else {
            return new ResponseEntity<>(MessageResponse.builder()
                    .message("Cliente no encontrado")
                    .object(null)
                    .build(), HttpStatus.METHOD_NOT_ALLOWED);

        }
    }

}
