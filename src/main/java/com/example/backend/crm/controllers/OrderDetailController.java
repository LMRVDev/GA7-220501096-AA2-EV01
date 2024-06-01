package com.example.backend.crm.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.example.backend.crm.models.entities.OrderDetail;
import com.example.backend.crm.models.payload.MessageResponse;
import com.example.backend.crm.services.OrderDetailService;



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
@RequestMapping("/orderdetail")
public class OrderDetailController {

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
        List<OrderDetail> getList = orderDetailService.listAll();

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


}
